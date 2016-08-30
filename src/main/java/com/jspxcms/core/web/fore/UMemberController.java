package com.jspxcms.core.web.fore;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.repository.UserDao;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.service.PayService;

@Controller
public class UMemberController {
	
	private static final String MY_COURSE = "a_my_course.html";
	private static final String MY_PROFILE = "a_my_profile.html";
	private static final String MY_SECURE = "a_my_secure.html";
	private static final String MY_SECURE_PASS = "a_my_secure_pass.html";
	private static final String MY_SECURE_PHONE = "a_my_secure_phone.html";
	private static final String MY_SECURE_EMAIL = "a_my_secure_email.html";
	private static final String MY_LIBRARY = "a_my_library.html";
	private static final String MY_SCORE = "a_my_score.html";
	private static final String MY_ORDER = "a_my_order.html";

	@RequestMapping(value = "myCourse.jspx")
	public String myCourse(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			//TODO
		}
		List<Order> orderList = pay.findOrderByUserIdAndStatus(user.getId(), "paid");
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("orderList", orderList);
		dataMap.put("userId", user.getId());
		dataMap.put("user", user);
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_COURSE);
	}
	
	@RequestMapping(value = "myProfile.jspx")
	public String myProfile(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			//TODO
		}
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", user.getId());
		dataMap.put("user", user);
		ForeContext.setData(model.asMap(), request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_PROFILE);
	}
	@RequestMapping(value = "updateProfile.jspx", method = RequestMethod.POST)
	public String profileSubmit(String username,String gender,String comeFrom,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		Response resp = new Response(request, response, modelMap);
		User user = Context.getCurrentUser(request);
		user.setGender(gender);
		UserDetail detail = user.getDetail();
		detail.setComeFrom(comeFrom);
		userService.update(user, detail);
		return resp.post();
	}
	
	
	@RequestMapping(value = "mySecure.jspx")
	public String mySecure(HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			//TODO
		}
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", user.getId());
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE);
	}
	
	@RequestMapping(value = "updatePass.jspx")
	public String mySecurePass(HttpServletRequest request, HttpServletResponse response, Model model,Integer userId) {
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_PASS);
	}
	@RequestMapping(value = "updatePassSuccess.jspx",method = RequestMethod.POST)
	public String mySecurePassSuccess(HttpServletRequest request, HttpServletResponse response,
			Model model,String pass, String rawPassword) {
		User u = Context.getCurrentUser(request);
		//验证 TODO
		System.out.println("密码=="+rawPassword);
		userService.updatePassword(u.getId(), rawPassword);
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		Response resp = new Response(request, response, model);
		return resp.post();
	}
	@RequestMapping(value = "updatePhone.jspx")
	public String mySecurePhone(HttpServletRequest request, HttpServletResponse response, Model model,Integer userId) {
		User u = userService.get(userId);
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", userId);
		dataMap.put("mobile", u.getMobile());
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_PHONE);
	}
	
	@RequestMapping(value = "updatePhoneSuccess.jspx",method=RequestMethod.POST)
	public String mySecurePhoneSuccess(HttpServletRequest request, HttpServletResponse response, Model model,Integer userId,String phone) {
		User u = userService.get(userId);
		userService.updatePhone(userId, phone);
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", userId);
		dataMap.put("mobile", u.getMobile());
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_PHONE);
	}
	
	
	@RequestMapping(value = "updateEmail.jspx")
	public String mySecureEmail(HttpServletRequest request, HttpServletResponse response, Model model,Integer userId) {
		User u = userService.get(userId);
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", userId);
		dataMap.put("email", u.getEmail());
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_EMAIL);
	}
	
	@RequestMapping(value = "updateEmailSuccess.jspx",method = RequestMethod.POST)
	public String mySecureEmailSuccess(HttpServletRequest request, HttpServletResponse response, Model model,Integer userId,String email) {
		userService.updateEmail(userId, email);
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE_EMAIL);
	}
	
	@RequestMapping(value = "myLibrary.jspx")
	public String myLibrary(HttpServletRequest request, HttpServletResponse response,String type, Model model) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			//TODO
		}
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", user.getId());
		dataMap.put("type", type);
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_LIBRARY);
	}
	
	@RequestMapping(value = "myScore.jspx")
	public String myScore(HttpServletRequest request, HttpServletResponse response, Model model) {
		ForeContext.setData(model.asMap(), request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SCORE);
	}
	
	@RequestMapping(value = "myOrder.jspx")
	public String myOrder(HttpServletRequest request, HttpServletResponse response, Model model,String status) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			//TODO
		}
		Integer userId = user.getId();
		List<Order> orderList =null;
		if(status==null){
			orderList = pay.findOrderByUserId(userId);
		}else{
			orderList = pay.findOrderByUserIdAndStatus(userId, status);
		}
		Map<String, Object> dataMap = model.asMap();
		dataMap.put("userId", userId);
		dataMap.put("orderList", orderList);
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_ORDER);
	}
	
	@ResponseBody
	@RequestMapping(value = "myOrderDelete.jspx" ,method = RequestMethod.POST)
	public String myOrderDelete(HttpServletRequest request, HttpServletResponse response, Model model,Integer orderId) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return "false";
		}
		pay.cancelOrder(orderId);
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);
		return "true";
	}
	
	
	
	
	@Autowired
	private PayService pay;
	@Autowired
	private UserService userService;
	
}
