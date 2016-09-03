package com.jspxcms.core.web.fore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jspxcms.common.util.JsonMapper;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.domain.Comment;
import com.jspxcms.core.domain.ScoreBoard;
import com.jspxcms.core.domain.ScoreItem;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domain.SpecialComment;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.CommentService;
import com.jspxcms.core.service.ScoreBoardService;
import com.jspxcms.core.service.ScoreItemService;
import com.jspxcms.core.service.SensitiveWordService;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.service.VoteMarkService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.plug.service.BrowseService;

/**
 * SpecialController
 * 
 * @author liufang
 * 
 */
@Controller
public class SpecialController {

	@RequestMapping(value = "/special/{id:[0-9]+}.jspx")
	public String special(@PathVariable Integer id, Integer page,
			HttpServletRequest request, HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Special special = service.get(id);
		if(special == null) {
			Response resp = new Response(request, response, modelMap);
			return resp.badRequest("course not found: " + id);
		}
		modelMap.addAttribute("special", special);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		User user = Context.getCurrentUser(request);
		//浏览记录
		service.addView(id, 1);
		if(user != null) {
			bs.addBrowse(user.getId(), id);
		}
		return special.getTemplate();
	}
	
	/**
	 * 提交评分
	 * @param courseId
	 * @param itemId
	 * @param request
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping(value = "/special_scoring.jspx", method = { RequestMethod.POST })
	public String scoring(Integer courseId, Integer itemId, String text, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		if (courseId == null || itemId == null) {
			return resp.badRequest();
		}
		Special special = service.get(courseId);
		ScoreItem item = scoreItemService.get(itemId);
		if (special == null || item == null) {
			return resp.badRequest();
		}
		Integer userId = Context.getCurrentUserId(request);
		if(userId == null) {
			return resp.badRequest("请先登录");
		}
		String ip = Servlets.getRemoteAddr(request);
		String cookie = Site.getIdentityCookie(request, response);
		if (userId != null) {
			if (voteMarkService.isUserVoted(Special.SCORE_MARK, courseId, userId, null)) {
				return resp.badRequest();
			}
		} else if (voteMarkService.isCookieVoted(Special.SCORE_MARK, courseId, cookie,
				null)) {
			return resp.badRequest();
		}
		
		// 保存评论并更新课程的评论数量
		text = sensitiveWordService.replace(text);
		Comment comment = new SpecialComment();
		comment.setFid(courseId);
		comment.setText(text);
		comment.setScore(item.getScore());
		comment.setIp(Servlets.getRemoteAddr(request));
		comment.setStatus(Comment.AUDITED);
		Site site = Context.getCurrentSite(request);
		commentService.save(comment, userId, site.getId(), null);
		// 更新评分数据
		scoreBoardService.scoring(Special.SCORE_MARK, courseId, itemId);
		voteMarkService.mark(Special.SCORE_MARK, courseId, userId, ip, cookie);
		return resp.post();
	}

	/**
	 * 查询评分
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/special_score/{id:[0-9]+}.jspx")
	public void score(@PathVariable Integer id, HttpServletResponse response) {
		List<ScoreBoard> boardList = scoreBoardService.findList(
				Special.SCORE_MARK, id);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (ScoreBoard board : boardList) {
			map.put(board.getItem().getId().toString(), board.getVotes());
		}
		JsonMapper mapper = new JsonMapper();
		String result = mapper.toJson(map);
		Servlets.writeHtml(response, result);
	}

	@Autowired
	private SpecialService service;
	@Autowired
	private BrowseService bs;
	@Autowired
	private ScoreBoardService scoreBoardService;
	@Autowired
	private VoteMarkService voteMarkService;
	@Autowired
	private ScoreItemService scoreItemService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private SensitiveWordService sensitiveWordService;
}
