package com.jspxcms.core.web.fore;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.jspxcms.common.file.FileHandler;
import com.jspxcms.common.security.CredentialsDigest;
import com.jspxcms.common.upload.Uploader;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.GlobalMail;
import com.jspxcms.core.domain.GlobalUpload;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.PublishPoint;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.service.InfoBufferService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.UserShiroService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.service.MobileVerifyService;
import com.jspxcms.plug.service.PayService;
import com.sun.star.uno.RuntimeException;

@Controller
public class UMemberController implements ServletContextAware {
	
	private static final String MY_COURSE = "a_my_course.html";
	private static final String MY_PROFILE = "a_my_profile.html";
	private static final String MY_SECURE = "a_my_secure.html";
	private static final String MY_SECURE_PASS = "a_my_secure_pass.html";
	private static final String MY_SECURE_PHONE = "a_my_secure_phone.html";
	private static final String MY_SECURE_EMAIL = "a_my_secure_email.html";
	private static final String MY_SECURE_EMAIL_VERIFY = "a_my_secure_email_send.html";
	private static final String MY_LIBRARY = "a_my_library.html";
	private static final String MY_SCORE = "a_my_score.html";
	private static final String MY_ORDER = "a_my_order.html";
	public static final String TO_LOGIN = "sys_member_login.html";
	
	//Spring这里是通过实现ServletContextAware接口来注入ServletContext对象  
    private ServletContext servletContext;  
    
    private static final Logger logger = LoggerFactory.getLogger(UMemberController.class);

	@RequestMapping(value = "myCourse.jspx")
	public String myCourse(HttpServletRequest request, HttpServletResponse response, Model model) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		List<Order> orderList = pay.findOrderByUserIdAndStatus(user.getId(), "paid");
		dataMap.put("orderList", orderList);
		dataMap.put("userId", user.getId());
		dataMap.put("user", user);
		return site.getTemplate(MY_COURSE);
	}
	
	@RequestMapping(value = "myProfile.jspx")
	public String myProfile(HttpServletRequest request, HttpServletResponse response, Model model) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		dataMap.put("userId", user.getId());
		dataMap.put("user", user);
		return site.getTemplate(MY_PROFILE);
	}
	
	@RequestMapping(value = "updateProfile.jspx", method = RequestMethod.POST)
	public String profileSubmit(Integer top, Integer left, Integer width,
			Integer height, String username,String gender,String comeFrom,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		
		Site site = Context.getCurrentSite(request);
		Response resp = new Response(request, response, modelMap);
		User user = Context.getCurrentUser(request);
		User userExist = userService.findByUsername(username);
		if(userExist != null && userExist.getId() != user.getId()) {
			resp.post(502, "昵称"+username+"已被占用");
		}
		user.setUsername(username);
		user.setGender(gender);
		UserDetail detail = user.getDetail();
		detail.setComeFrom(comeFrom);
		try {
//			doAvatarUpload(file, request, response);
			resizeAvatar(user, site, top, left, width, height);
			detail.setWithAvatar(true);
		} catch (Exception e) {
			logger.error("upload avatar image error.", e);
		}
		userService.update(user, detail);
		return resp.post();
	}
	
	private void resizeAvatar(User user, Site site, Integer top, Integer left, Integer width, Integer height) {
		try {
			PublishPoint point = site.getGlobal().getUploadsPublishPoint();
			FileHandler fileHandler = point.getFileHandler(pathResolver);
			// 读取头像临时文件
			String pathnameTemp = "/users/" + user.getId() + "/avatar_temp.jpg";
			BufferedImage buff = fileHandler.readImage(pathnameTemp);
			// 保存头像原图
			String pathnameOrig = "/users/" + user.getId() + "/" + User.AVATAR;
			fileHandler.storeImage(buff, "jpg", pathnameOrig);
			// 裁剪头像
			if (left != null && top != null && width != null && height != null) {
				buff = Scalr.crop(buff, left, top, width, height);
			}
			// 保存大头像
			String pathnameLarge = "/users/" + user.getId() + "/"
					+ User.AVATAR_LARGE;
			Integer avatarLarge = site.getGlobal().getRegister().getAvatarLarge();
			BufferedImage buffLarge = Scalr.resize(buff, Scalr.Method.QUALITY,
					avatarLarge, avatarLarge);
			fileHandler.storeImage(buffLarge, "jpg", pathnameLarge);
			// 保存小头像
			String pathnameSmall = "/users/" + user.getId() + "/"
					+ User.AVATAR_SMALL;
			Integer avatarSmall = site.getGlobal().getRegister().getAvatarSmall();
			BufferedImage buffSmall = Scalr.resize(buff, Scalr.Method.QUALITY,
					avatarSmall, avatarSmall);
			fileHandler.storeImage(buffSmall, "jpg", pathnameSmall);
			// 删除临时头像
			fileHandler.delete(pathnameTemp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String doAvatarUpload(MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 文件是否存在
		validateFile(file);
		Site site = Context.getCurrentSite(request);
		User user = Context.getCurrentUser(request);

		String origFilename = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origFilename).toLowerCase();
		GlobalUpload gu = site.getGlobal().getUpload();
		// 后缀名是否合法
		validateExt(ext, Uploader.IMAGE, gu);
		BufferedImage buffImg = ImageIO.read(file.getInputStream());

		PublishPoint point = user.getGlobal().getUploadsPublishPoint();
		FileHandler fileHandler = point.getFileHandler(pathResolver);
		String pathname = "/users/" + user.getId() + "/avatar.jpg";
		String urlPrefix = point.getUrlPrefix();
		String fileUrl = urlPrefix + pathname;
		// 一律存储为jpg
		fileHandler.storeImage(buffImg, "jpg", pathname);
		return fileUrl;
	}

	private void validateFile(MultipartFile partFile) {
		if (partFile == null || partFile.isEmpty()) {
			throw new RuntimeException("file is empty");
		}
	}
	
	private void validateExt(String extension, String type, GlobalUpload gu) {
		if (!gu.isExtensionValid(extension, type)) {
			throw new RuntimeException("image extension not allowed: " + extension);
		}
	}
	
	@RequestMapping(value = "mySecure.jspx")
	public String mySecure(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);

		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		return site.getTemplate(MY_SECURE);
	}
	
	@RequestMapping(value = "updatePass.jspx")
	public String modifyPassword(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_PASS);
	}
	
	@RequestMapping(value = "updatePassword.jspx",method = RequestMethod.POST)
	public String modifyPassword(HttpServletRequest request, HttpServletResponse response,
			Model model,String passwordOld, String password, String passwordAgain) {
		Response resp = new Response(request, response, model);
		if(!password.equals(passwordAgain)) {
			return resp.post(501, "两次输入密码不一致");
		}
		User user = Context.getCurrentUser(request);
		if (!credentialsDigest.matches(user.getPassword(), passwordOld,
				user.getSaltBytes())) {
			return resp.post(501, "member.passwordError");
		}
		userService.updatePassword(user.getId(), password);
		return resp.post();
	}
	
	@RequestMapping(value = "updatePhone.jspx")
	public String modifyMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = Context.getCurrentUser(request);
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("mobile", user.getMobile());
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_PHONE);
	}
	
	/**
	 * 修改手机-发送短信
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "modify_mobile_send_sms_verify_code.jspx", method = RequestMethod.POST)
	public String sendSmsVerifyCode(String mobile, HttpServletRequest request,
			HttpServletResponse response) {
		if(StringUtils.isBlank(mobile)) {
			return "false";
		}
		// 不允许重复注册
		User user = userShiroService.findByMobile(mobile);
		if(user != null) {
			return "false";
		}
		String verifyCode = mobileVerifyService.generateNewVerifyCode(mobile);
		// send sms
		boolean sendSucc = mobileVerifyService.sendSmsVerifyCodeForAlterMobile(mobile, verifyCode);
		if(sendSucc) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@RequestMapping(value = "modify_mobile.jspx",method=RequestMethod.POST)
	public String modifyMobile(String mobile, String smsVerifyCode, String password,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Response resp = new Response(request, response, model);
		User user = Context.getCurrentUser(request);
		if(StringUtils.isBlank(smsVerifyCode) || StringUtils.isBlank(mobile)) {
			return resp.post(501, "手机号和验证码不能为空");
		}
		// 检查手机号是否使用过
		if(userShiroService.findByMobile(mobile) != null) {
			return resp.post(501, "手机号已被使用，请更换");
		}
		// 验证密码
		if (!credentialsDigest.matches(user.getPassword(), password,
				user.getSaltBytes())) {
			return resp.post(501, "member.passwordError");
		}
		// 验证码
		if(!mobileVerifyService.verify(mobile, smsVerifyCode)) {
			return resp.post(501, "验证码错误");
		}
		userService.updatePhone(user.getId(), mobile);
		return resp.post();
	}
	
	@RequestMapping(value = "updateEmail.jspx")
	public String mySecureEmail(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = Context.getCurrentUser(request);
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("email", user.getEmail());
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_EMAIL);
	}
	
	/**
	 * 修改邮箱-发送邮件
	 * @param email
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sendVerifyEmail.jspx",method = RequestMethod.POST)
	public String sendVerifyEmail(String email, String password,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Response resp = new Response(request, response, model);
		User user = Context.getCurrentUser(request);
		// 检查邮箱是否使用过
		if(userShiroService.findByEmail(email) != null) {
			return resp.post(502, "邮箱已被使用，请更换");
		}
		// 验证密码
		if (!credentialsDigest.matches(user.getPassword(), password, user.getSaltBytes())) {
			return resp.post(501, "member.passwordError");
		}
		
		Site site = Context.getCurrentSite(request);
		GlobalMail mail = site.getGlobal().getMail();
		userService.sendModifyEmail(site, user, email, mail);
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("email", user.getEmail());
		ForeContext.setData(dataMap, request);
		return site.getTemplate(MY_SECURE_EMAIL_VERIFY);
	}
	
	/**
	 * 修改邮箱-验证key
	 * @param key
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "verify_email_for_modify.jspx")
	public String modifyEmail(String key, String email, HttpServletRequest request,
			HttpServletResponse response, Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.badRequest();
		}
		User keyUser = userService.findByValidation(
				Constants.VERIFY_EMAIL_TYPE, key);
		if(keyUser == null) {
			return resp.post(501, "无效链接");
		}
		// 更新邮箱
		userService.updateEmail(keyUser.getId(), email);
		return resp.post();
	}
	
	@RequestMapping(value = "myLibrary.jspx")
	public String myLibrary(HttpServletRequest request, HttpServletResponse response,String type, Model model) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		dataMap.put("userId", user.getId());
		dataMap.put("type", type);
		return site.getTemplate(MY_LIBRARY);
	}
	
	@RequestMapping(value = "myScore.jspx")
	public String myScore(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		
		return site.getTemplate(MY_SCORE);
	}
	
	@RequestMapping(value = "myOrder.jspx")
	public String myOrder(HttpServletRequest request, HttpServletResponse response, Model model,String status) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		Integer userId = user.getId();
		List<Order> orderList =null;
		if(status==null){
			orderList = pay.findOrderByUserId(userId);
		}else{
			orderList = pay.findOrderByUserIdAndStatus(userId, status);
		}
		dataMap.put("userId", userId);
		dataMap.put("orderList", orderList);
		return site.getTemplate(MY_ORDER);
	}
	
	@RequestMapping(value = "myOrderDelete.jspx")
	public String myOrderDelete(HttpServletRequest request, HttpServletResponse response, Model model,Integer orderId) {
		pay.cancelOrder(orderId);
		Response resp = new Response(request, response, model);
		return resp.post();
	}
	
	/**
	 * 文件下载
	 * @param infoId
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "download.jspx")
	public String downloadFile(Integer infoId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		
		Info info = infoQueryService.get(infoId);
		if(!info.getModel().getNumber().equals("fileAndTrain")) {
			Response resp = new Response(request, response, model);
			return resp.post(502, "系统错误");
		}
		
		infoBufferService.updateDownloads(infoId);
		
		String filepath = info.getCustoms().get("files");
		String fileName = info.getCustoms().get("filesName");
		try {
			fileName = URLEncoder.encode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String basePath = servletContext.getRealPath("/");
		File file = new File(basePath + filepath);
		
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
		
		ServletOutputStream out = null;
		FileInputStream fis = null;
		try {
			out = response.getOutputStream();
			fis = new FileInputStream(file);
			int b = 0;
			byte[] buffer = new byte[1024];
			while(b != -1) {
				b = fis.read(buffer);
				out.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				IOUtils.closeQuietly(fis);
			}
			if(out != null) {
				IOUtils.closeQuietly(out);
			}
		}
		return null;
	}
	
	@Autowired
	private PayService pay;
	@Autowired
	private UserService userService;
	@Autowired
	private InfoQueryService infoQueryService;
	@Autowired
	private InfoBufferService infoBufferService;
	@Autowired
	protected PathResolver pathResolver;
	@Autowired
	private CredentialsDigest credentialsDigest;
	@Autowired
	private MobileVerifyService mobileVerifyService;
	@Autowired
	private UserShiroService userShiroService;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
}
