package com.jspxcms.core.web.fore;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspxcms.common.file.FileHandler;
import com.jspxcms.common.file.LocalFileHandler;
import com.jspxcms.common.util.JsonMapper;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Org;
import com.jspxcms.core.domain.PublishPoint;
import com.jspxcms.core.domain.ScoreBoard;
import com.jspxcms.core.domain.ScoreItem;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.InfoBufferService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.ScoreBoardService;
import com.jspxcms.core.service.ScoreItemService;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.service.VoteMarkService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.TitleText;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.service.PayService;

/**
 * InfoController
 * 
 * @author liufang
 * 
 */
@Controller
public class InfoController {
	@RequestMapping(value = "/info/{infoId:[0-9]+}_{page:[0-9]+}.jspx")
	public String infoByPagePath(@PathVariable Integer infoId,
			@PathVariable Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		return doInfo(infoId, page, site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}/info/{infoId:[0-9]+}_{page:[0-9]+}.jspx")
	public String infoByPagePathSite(@PathVariable String siteNumber,
			@PathVariable Integer infoId, @PathVariable Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(siteNumber, messages, "siteNumber")) {
			return resp.badRequest();
		}
		Site site = siteService.findByNumber(siteNumber);
		if (!Validations.exist(site, messages, "Site", siteNumber)) {
			return resp.badRequest();
		}
		Context.setCurrentSite(request, site);
		Context.setCurrentSite(site);
		return doInfo(infoId, page, site, resp, messages, request, modelMap);
	}

	@RequestMapping(value = "/info/{infoId:[0-9]+}.jspx")
	public String info(@PathVariable Integer infoId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return infoByPagePath(infoId, page, request, response, modelMap);
	}

	@RequestMapping(value = "/site_{siteNumber}/info/{infoId:[0-9]+}.jspx")
	public String infoSite(@PathVariable String siteNumber,
			@PathVariable Integer infoId, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		return infoByPagePathSite(siteNumber, infoId, page, request, response,
				modelMap);
	}
	
	private final String MODEL_TYPE_FILE_AND_TRAIN = "fileAndTrain"; // 文库题库模型
	private final String MODEL_TYPE_LESSON = "lesson"; // 课时模型
	private final String NODE_NUMBER_FILE = "file"; // 栏目-文库
	private final String NODE_NUMBER_TRAIN = "train"; // 栏目-题库

	private String doInfo(Integer infoId, Integer page, Site site,
			Response resp, List<String> messages, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Info info = query.get(infoId);
		if (info == null) {
			return resp.badRequest("Info not found: " + infoId);
		}
		if (!info.isNormal()) {
			return resp.forbidden();
		}
		Collection<MemberGroup> groups = Context.getCurrentGroups(request);
		Collection<Org> orgs = Context.getCurrentOrgs(request);
		if (!info.isViewPerm(groups, orgs)) {
			User user = Context.getCurrentUser(request);
			if (user != null) {
				return resp.forbidden();
			} else {
				return resp.unauthorized();
			}
		}
		if (!info.getSite().getId().equals(site.getId())) {
			site = info.getSite();
			Context.setCurrentSite(request, site);
			Context.setCurrentSite(site);
		}
		if (info.isLinked()) {
			return "redirect:" + info.getLinkUrl();
		}
		page = page == null ? 1 : page;
		Node node = info.getNode();
		List<TitleText> textList = info.getTextList();
		TitleText infoText = TitleText.getTitleText(textList, page);
		String title = infoText.getTitle();
		String text = infoText.getText();
		modelMap.addAttribute("info", info);
		modelMap.addAttribute("node", node);
		modelMap.addAttribute("title", title);
		modelMap.addAttribute("text", text);
		// 文库类型
		if(MODEL_TYPE_FILE_AND_TRAIN.equals(info.getModel().getNumber())) {
			String nodeNumber = info.getNode().getNumber();
			modelMap.addAttribute("nodeNumber", nodeNumber);// 页面上通过${nodeNumber}获取
		}
		// 课时类型
		else if(MODEL_TYPE_LESSON.equals(info.getModel().getNumber())) {
			User user = Context.getCurrentUser(request);
			if(user == null) {// 如果未登录，则跳转至登录页面
				Map<String, Object> data = modelMap.asMap();
				String fallbackUrl = String.format("info/%s.jspx", info.getId());
				data.put("fallback", fallbackUrl);
				ForeContext.setData(data, request);
				return site.getTemplate(UMemberController.TO_LOGIN);
			}
			Integer courseId = info.getSpecials().get(0).getId();
			// 查询课程
			Special course = specialService.get(courseId);
			if(course == null) {
				return resp.badRequest("course not found: " + courseId);
			}
			// 提示购买按钮
			if(promptBuy(course, user)) {
				modelMap.addAttribute("shouldBuy", Boolean.TRUE);
			}
			modelMap.addAttribute("course", course);
		}

		Page<String> pagedList = new PageImpl<String>(Arrays.asList(text),
				new PageRequest(page - 1, 1), textList.size());
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page, info, pagedList);

		String template = Servlets.getParameter(request, "template");
		if (StringUtils.isNotBlank(template)) {
			return template;
		} else {
			return info.getTemplate();
		}
	}

	/**
	 * 判断视频是否可以免费播放
	 * @param courseId
	 * @param userId
	 * @return
	 */
	private boolean isCanPlay(Special course, Integer userId) {
		// 免费课可直接观看
		String priceStr = course.getCustoms().get("price");
		if(StringUtils.isBlank(priceStr) || Double.valueOf(priceStr) <= 0) {
			return true;
		}
		// 判断该学员是否购买过课程
		return payService.isBuyed(userId, course.getId());
	}
	
	// 是否提示购买
	private boolean promptBuy(Special course, User user) {
		// 免费课无需购买
		String priceStr = course.getCustoms().get("price");
		if(StringUtils.isBlank(priceStr) || Double.valueOf(priceStr) <= 0) {
			return false;
		}
		// 未登录或者已登录未购买
		return user == null || !payService.isBuyed(user.getId(), course.getId());
	}

	@RequestMapping(value = "/info_download.jspx")
	public String download(Integer id, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap)
			throws IOException {
		Response resp = new Response(request, response, modelMap);
		Info info = query.get(id);
		infoBufferService.updateDownloads(id);
		String path = info.getFile();
		if (StringUtils.isBlank(path)) {
			return resp.notFound();
		}
		String normalPath = FilenameUtils.normalize(path, true);
		PublishPoint point = info.getSite().getUploadsPublishPoint();
		FileHandler fileHandler = point.getFileHandler(pathResolver);
		String urlPrefix = point.getUrlPrefix();
		if (StringUtils.startsWith(normalPath, urlPrefix)
				&& fileHandler instanceof LocalFileHandler) {
			LocalFileHandler lfHandler = (LocalFileHandler) fileHandler;
			normalPath = normalPath.substring(urlPrefix.length());
			File file = lfHandler.getFile(normalPath);
			if (!file.exists()) {
				return null;
			}
			Servlets.setDownloadHeader(response, file.getName());
			response.setContentLength((int) file.length());
			OutputStream output = response.getOutputStream();
			FileUtils.copyFile(file, output);
			output.flush();
			return null;
		} else {
			response.sendRedirect(path);
			return null;
		}
	}

	@RequestMapping(value = "/info_views.jspx")
	public void view(Integer id, HttpServletResponse response) {
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		if (query.get(id) == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateViews(id));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_views/{id:[0-9]+}.jspx")
	public void views(@PathVariable Integer id,
			@RequestParam(defaultValue = "true") boolean isUpdate,
			HttpServletResponse response) {
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer views;
		if (isUpdate) {
			views = bufferService.updateViews(id);
		} else {
			views = info.getBufferViews();
		}
		String result = Integer.toString(views);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_comments/{id:[0-9]+}.jspx")
	public void comments(@PathVariable Integer id, HttpServletResponse response) {
		Info info = query.get(id);
		int comments;
		if (info != null) {
			comments = info.getBufferComments();
		} else {
			comments = 0;
		}
		String result = Integer.toString(comments);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_downloads/{id:[0-9]+}.jspx")
	public void downloads(@PathVariable Integer id, HttpServletResponse response) {
		Info info = query.get(id);
		int downloads;
		if (info != null) {
			downloads = info.getBufferDownloads();
		} else {
			downloads = 0;
		}
		String result = Integer.toString(downloads);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_digg.jspx")
	public void digg(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId(request);
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.DIGG_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateDiggs(id, userId,
				ip, cookie));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_bury.jspx")
	public void bury(Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		if (id == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		if (info == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId(request);
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.DIGG_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.DIGG_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		String result = Integer.toString(bufferService.updateBurys(id, userId,
				ip, cookie));
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_diggs/{id:[0-9]+}.jspx")
	public void diggs(@PathVariable Integer id, HttpServletResponse response) {
		Info info = query.get(id);
		int diggs;
		int burys;
		if (info != null) {
			diggs = info.getBufferDiggs();
			burys = info.getBufferBurys();
		} else {
			diggs = 0;
			burys = 0;
		}
		String result = "[" + diggs + "," + burys + "]";
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_scoring.jspx", method = { RequestMethod.POST })
	public void scoring(Integer id, Integer itemId, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		if (id == null || itemId == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Info info = query.get(id);
		ScoreItem item = scoreItemService.get(itemId);
		if (info == null || item == null) {
			Servlets.writeHtml(response, "0");
			return;
		}
		Integer userId = Context.getCurrentUserId(request);
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Info.SCORE_MARK, id, userId, null)) {
				Servlets.writeHtml(response, "0");
				return;
			}
		} else if (voteMarkService.isCookieVoted(Info.SCORE_MARK, id, cookie,
				null)) {
			Servlets.writeHtml(response, "0");
			return;
		}
		int score = infoBufferService.updateScore(id, itemId, userId, ip,
				cookie);
		String result = String.valueOf(score);
		Servlets.writeHtml(response, result);
	}

	@RequestMapping(value = "/info_score/{id:[0-9]+}.jspx")
	public void score(@PathVariable Integer id, HttpServletResponse response) {
		List<ScoreBoard> boardList = scoreBoardService.findList(
				Info.SCORE_MARK, id);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (ScoreBoard board : boardList) {
			map.put(board.getItem().getId().toString(), board.getVotes());
		}
		JsonMapper mapper = new JsonMapper();
		String result = mapper.toJson(map);
		Servlets.writeHtml(response, result);
	}

	@Autowired
	private SiteService siteService;
	@Autowired
	private ScoreBoardService scoreBoardService;
	@Autowired
	private VoteMarkService voteMarkService;
	@Autowired
	private ScoreItemService scoreItemService;
	@Autowired
	private InfoBufferService bufferService;
	@Autowired
	private InfoQueryService query;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	private PathResolver pathResolver;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private PayService payService;
}
