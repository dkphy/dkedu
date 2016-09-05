package com.jspxcms.core.web.fore;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.common.captcha.Captchas;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.common.web.Validations;
import com.jspxcms.core.domain.GlobalMail;
import com.jspxcms.core.domain.GlobalRegister;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.OrgService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.UserShiroService;
import com.jspxcms.core.support.Constants;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.plug.service.MobileVerifyService;
import com.octo.captcha.service.CaptchaService;

/**
 * RegisterController
 * 
 * @author liufang
 * 
 */
@Controller
public class RegisterController {
	/**
	 * 注册模板
	 */
	public static final String REGISTER_TEMPLATE = "sys_member_register.html";
	/**
	 * 注册结果模板。提示会员注册成功，或提示会员接收验证邮件。
	 */
	public static final String REGISTER_MESSAGE_TEMPLATE = "sys_member_register_message.html";
	/**
	 * 验证会员模板
	 */
	public static final String VERIFY_MEMBER_TEMPLATE = "sys_member_verify_member.html";
	/**
	 * 忘记密码模板
	 */
	public static final String FORGOT_PASSWORD_TEMPLATE = "sys_member_forgot_password.html";
	/**
	 * 找回密码模板
	 */
	public static final String RETRIEVE_PASSWORD_TEMPLATE = "sys_member_retrieve_password.html";

	@RequestMapping(value = "/register.jspx")
	public String registerForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		GlobalRegister registerConf = site.getGlobal().getRegister();
		if (registerConf.getMode() == GlobalRegister.MODE_OFF) {
			return resp.warning("register.off");
		}
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(REGISTER_TEMPLATE);
	}

	@RequestMapping(value = "/register.jspx", method = RequestMethod.POST)
	public String registerSubmit(String regType, String captcha, String username,
			String password, String email, String mobile, String smsVerifyCode, String gender, Date birthDate,
			String bio, String comeFrom, String qq, String msn, String weixin,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		GlobalRegister reg = site.getGlobal().getRegister();
		// 参数检验
		String result = validateRegisterSubmit(request, resp, reg, regType, captcha,
				username, password, email, gender, mobile, smsVerifyCode);
		if (resp.hasErrors()) {
			return result;
		}

		int verifyMode = reg.getVerifyMode();
		String ip = Servlets.getRemoteAddr(request);
		int groupId = reg.getGroupId();
		int orgId = reg.getOrgId();
		// 邮箱注册设置账户未未激活，其他情况均设置为已激活
		int status = User.NORMAL;
		if(!"byMobile".equals(regType) && verifyMode != GlobalRegister.VERIFY_MODE_NONE) {
			status = User.UNACTIVATED;
		}
//		int status = verifyMode == GlobalRegister.VERIFY_MODE_NONE ? User.NORMAL
//				: User.UNACTIVATED;
		// 注册账户
		User user = userService.register(ip, groupId, orgId, status, username,
				password, email, mobile, null, null, gender, birthDate, bio, comeFrom,
				qq, msn, weixin);
		// 如果是邮箱注册，发验证邮件
		if (!"byMobile".equals(regType) && verifyMode == GlobalRegister.VERIFY_MODE_EMAIL) {
			GlobalMail mail = site.getGlobal().getMail();
			String subject = reg.getVerifyEmailSubject();
			String text = reg.getVerifyEmailText();
			userService.sendVerifyEmail(site, user, mail, subject, text);
		}
		resp.addData("verifyMode", verifyMode);
		resp.addData("id", user.getId());
		resp.addData("username", user.getUsername());
		resp.addData("regType", regType);
		if(StringUtils.isNotBlank(user.getEmail())) {
			resp.addData("email", user.getEmail());
		}
		return resp.post();
	}

	@RequestMapping(value = "/register_message.jspx")
	public String registerMessage(String email, Integer verifyMode,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		GlobalRegister reg = site.getGlobal().getRegister();
		String username = Servlets.getParameter(request, "username");
		String result = validateRegisterMessage(request, resp, reg, username,
				email, verifyMode);
		if (resp.hasErrors()) {
			return result;
		}

		User registerUser = userService.findByUsername(username);
		modelMap.addAttribute("registerUser", registerUser);
		modelMap.addAttribute("verifyMode", verifyMode);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(REGISTER_MESSAGE_TEMPLATE);
	}

	@RequestMapping(value = "/verify_member.jspx")
	public String verifyMember(String key, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		Site site = Context.getCurrentSite(request);
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.badRequest();
		}
		User keyUser = userService.findByValidation(
				Constants.VERIFY_MEMBER_TYPE, key);
		userService.verifyMember(keyUser);
		modelMap.addAttribute("keyUser", keyUser);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(REGISTER_MESSAGE_TEMPLATE);
	}

	@RequestMapping(value = "/forgot_password.jspx")
	public String forgotPasswordForm(HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(FORGOT_PASSWORD_TEMPLATE);
	}

	@RequestMapping(value = "/forgot_password.jspx", method = RequestMethod.POST)
	public String forgotPasswordSubmit(String username, String email,
			String captcha, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		String result = validateForgotPasswordSubmit(request, resp, username,
				email, captcha);
		if (resp.hasErrors()) {
			return result;
		}

		Site site = Context.getCurrentSite(request);
		User forgotUser = userService.findByUsername(username);
		GlobalRegister reg = site.getGlobal().getRegister();
		GlobalMail mail = site.getGlobal().getMail();
		String subject = reg.getPasswordEmailSubject();
		String text = reg.getPasswordEmailText();
		userService.sendPasswordEmail(site, forgotUser, mail, subject, text);
		resp.addData("username", username);
		resp.addData("email", email);
		return resp.post();
	}
	
	@ResponseBody
	@RequestMapping(value = "/forgot_password_send_email.jspx", method = RequestMethod.POST)
	public String forgotPasswordSendEmail(String email, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		Site site = Context.getCurrentSite(request);
		User forgotUser = userShiroService.findByEmail(email);
		if(forgotUser == null) {
			return "false";
		}
		GlobalRegister reg = site.getGlobal().getRegister();
		GlobalMail mail = site.getGlobal().getMail();
		String subject = reg.getPasswordEmailSubject();
		String text = reg.getPasswordEmailText();
		userService.sendPasswordEmail(site, forgotUser, mail, subject, text);
		resp.addData("username", forgotUser.getUsername());
		resp.addData("email", email);
		return "true";
	}
	
	/**
	 * 忘记密码-发送手机验证码
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forgot_send_sms_verify_code.jspx", method = RequestMethod.POST)
	public String forgotSendSmsVerifyCode(String mobile, HttpServletRequest request,
			HttpServletResponse response) {
		if(StringUtils.isBlank(mobile)) {
			return "false";
		}
		// 无此用户，不允许发短信
		User user = userShiroService.findByMobile(mobile);
		if(user == null) {
			return "false";
		}
		String verifyCode = mobileVerifyService.generateNewVerifyCode(mobile);
		// send sms
		boolean sendSucc = mobileVerifyService.sendSmsVerifyCodeForForgotPsw(mobile, verifyCode);
		if(sendSucc) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 注册账号-发送短信
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register_send_sms_verify_code.jspx", method = RequestMethod.POST)
	public String registerSendSmsVerifyCode(String mobile, HttpServletRequest request,
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
		boolean sendSucc = mobileVerifyService.sendSmsVerifyCodeForReg(mobile, verifyCode);
		if(sendSucc) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@RequestMapping(value = "/retrieve_password.jspx")
	public String retrievePasswordForm(String key, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.badRequest();
		}

		Site site = Context.getCurrentSite(request);
		User retrieveUser = userService.findByValidation(
				Constants.RETRIEVE_PASSWORD_TYPE, key);
		modelMap.addAttribute("retrieveUser", retrieveUser);
		modelMap.addAttribute("key", key);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		return site.getTemplate(RETRIEVE_PASSWORD_TEMPLATE);
	}

	/**
	 * 邮箱重置密码
	 * @param key
	 * @param password
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/retrieve_password.jspx", method = RequestMethod.POST)
	public String retrievePasswordSubmit(String key, String password,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(key, messages, "key")) {
			return resp.post(401);
		}
		if (!Validations.notNull(password, messages, "password")) {
			return resp.post(402);
		}

		User retrieveUser = userService.findByValidation(
				Constants.RETRIEVE_PASSWORD_TYPE, key);
		if (retrieveUser == null) {
			return resp.post(501, "retrievePassword.invalidKey");
		}
		userService.passwordChange(retrieveUser, password);
		return resp.post();
	}
	
	/**
	 * 通过手机重置密码
	 * @param key
	 * @param password
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/retrieve_password_by_mobile.jspx", method = RequestMethod.POST)
	public String retrievePasswordSubmitByMobile(String mobile, String verifyCode, String password,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(mobile, messages, "mobile")) {
			return resp.post(401);
		}
		if (!Validations.notNull(password, messages, "password")) {
			return resp.post(402);
		}
		if (!Validations.notNull(verifyCode, messages, "verifyCode")) {
			return resp.post(403);
		}
		// 验证码
		boolean vSucc = mobileVerifyService.verify(mobile, verifyCode);
		if(!vSucc) {
			return resp.post(402, "sms.verifyCodeError");
		}
		// 修改密码
		User user = userShiroService.findByMobile(mobile);
		if (user == null) {
			return resp.post(501, "forgotPassword.usernameNotExist", new String[] {mobile});
		}
		userService.passwordChange(user, password);
		return resp.post();
	}

	@ResponseBody
	@RequestMapping(value = "/check_username.jspx")
	public String checkUsername(String username, String original,
			HttpServletRequest request, HttpServletResponse response) {
		Servlets.setNoCacheHeader(response);
		if (StringUtils.isBlank(username)) {
			return "true";
		}
		if (StringUtils.equals(username, original)) {
			return "true";
		}
		// 检查数据库是否重名
		boolean exist = userService.usernameExist(username);
		if (!exist) {
			return "true";
		} else {
			return "false";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/check_mobile.jspx")
	public String checkMobile(String mobile,
			HttpServletRequest request, HttpServletResponse response) {
		Servlets.setNoCacheHeader(response);
		if (StringUtils.isBlank(mobile)) {
			return "true";
		}
		// 检查数据库是否重名
		User user = userShiroService.findByMobile(mobile);
		return user == null ? "true" : "false";
	}
	
	@ResponseBody
	@RequestMapping(value = "/check_email.jspx")
	public String checkEmail(String email,
			HttpServletRequest request, HttpServletResponse response) {
		Servlets.setNoCacheHeader(response);
		if (StringUtils.isBlank(email)) {
			return "true";
		}
		// 检查数据库是否重名
		User user = userShiroService.findByEmail(email);
		return user == null ? "true" : "false";
	}

	private String validateRegisterSubmit(HttpServletRequest request,
			Response resp, GlobalRegister reg, String regType, String captcha, String username,
			String password, String email, String gender, String mobile, String verifyCode) {
		List<String> messages = resp.getMessages();
		// 不使用验证码
		// if (!Captchas.isValid(captchaService, request, captcha)) {
		// return resp.post(100, "error.captcha");
		// }
		if (reg.getMode() == GlobalRegister.MODE_OFF) {
			return resp.post(501, "register.off");
		}
		Integer groupId = reg.getGroupId();
		if (groupService.get(groupId) == null) {
			return resp.post(502, "register.groupNotSet");
		}
		Integer orgId = reg.getOrgId();
		if (orgService.get(orgId) == null) {
			return resp.post(503, "register.orgNotSet");
		}

		if (!Validations.notEmpty(username, messages, "username")) {
			return resp.post(401);
		}
		if (!Validations.length(username, reg.getMinLength(),
				reg.getMaxLength(), messages, "username")) {
			return resp.post(402);
		}
		// 防止同一昵称重复注册
		User user = userShiroService.findByUsername(username);
		if(user != null) {
			return resp.post(408, "该昵称已注册用户，不允许重复注册");
		}
		if (!Validations.pattern(username, reg.getValidCharacter(), messages,
				"username")) {
			return resp.post(403);
		}
		if (!Validations.notEmpty(password, messages, "password")) {
			return resp.post(404);
		}
		
		if("byMobile".equals(regType)) {
			// 手机注册，手机号和验证码不能为空，且验证码必须正确
			if (!Validations.notEmpty(mobile, messages, "mobile") 
					|| !Validations.notEmpty(verifyCode, messages, "verifyCode")) {
				return resp.post(408, "手机号和验证码不允许为空");
			}
			if(!mobileVerifyService.verify(mobile, verifyCode)) {
				return resp.post(409, "验证码错误");
			}
			// 防止同一手机号重复注册
			User mobileUser = userShiroService.findByMobile(mobile);
			if(mobileUser != null) {
				return resp.post(408, "该手机号已注册用户，不允许重复注册");
			}
		} else {
			// 邮箱注册，邮箱不能为空
			if (reg.getVerifyMode() == GlobalRegister.VERIFY_MODE_EMAIL
					&& !Validations.notEmpty(email, messages, "email")) {
				return resp.post(405, "邮箱不能为空");
			}
			if (!Validations.email(email, messages, "email")) {
				return resp.post(406, "邮箱格式错误");
			}
			// 防止同一邮箱重复注册
			User mailUser = userShiroService.findByEmail(email);
			if(mailUser != null) {
				return resp.post(408, "该邮箱已注册用户，不允许重复注册");
			}
		}
		if (!Validations.pattern(gender, "[F,M]", messages, "gender")) {
			return resp.post(407);
		}
		return null;
	}

	private String validateRegisterMessage(HttpServletRequest request,
			Response resp, GlobalRegister reg, String username, String email,
			Integer verifyMode) {
		List<String> messages = resp.getMessages();
		if (!Validations.notEmpty(username, messages, "username")) {
			return resp.badRequest();
		}
		if (!Validations.notEmpty(email, messages, "email")) {
			return resp.badRequest();
		}
		if (!Validations.notNull(verifyMode, messages, "verifyMode")) {
			return resp.badRequest();
		}
		User registerUser = userService.findByUsername(username);
		if (!Validations.exist(registerUser)) {
			return resp.notFound();
		}
//		if (!registerUser.getEmail().equals(email)) {
//			return resp.notFound("email not found: " + email);
//		}
		if (reg.getMode() == GlobalRegister.MODE_OFF) {
			return resp.warning("register.off");
		}
		return null;
	}

	private String validateForgotPasswordSubmit(HttpServletRequest request,
			Response resp, String username, String email, String captcha) {
		List<String> messages = resp.getMessages();
		if (!Captchas.isValid(captchaService, request, captcha)) {
			return resp.post(100, "error.captcha");
		}
		if (!Validations.notEmpty(username, messages, "username")) {
			return resp.post(401);
		}
		if (!Validations.notEmpty(email, messages, "email")) {
			return resp.post(402);
		}

		User forgotUser = userService.findByUsername(username);
		if (!Validations.exist(forgotUser)) {
			return resp.post(501, "forgotPassword.usernameNotExist",
					new String[] { username });
		}
		if (!StringUtils.equals(forgotUser.getEmail(), email)) {
			return resp.post(502, "forgotPassword.emailNotMatch");
		}
		return null;
	}

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private MemberGroupService groupService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserShiroService userShiroService;
	@Autowired
	private MobileVerifyService mobileVerifyService;
}
