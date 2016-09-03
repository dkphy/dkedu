package com.jspxcms.core.web.fore;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.service.InfoBufferService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.service.PayService;

@Controller
public class UMemberController implements ServletContextAware {
	
	private static final String MY_COURSE = "a_my_course.html";
	private static final String MY_PROFILE = "a_my_profile.html";
	private static final String MY_SECURE = "a_my_secure.html";
	private static final String MY_SECURE_PASS = "a_my_secure_pass.html";
	private static final String MY_SECURE_PHONE = "a_my_secure_phone.html";
	private static final String MY_SECURE_EMAIL = "a_my_secure_email.html";
	private static final String MY_LIBRARY = "a_my_library.html";
	private static final String MY_SCORE = "a_my_score.html";
	private static final String MY_ORDER = "a_my_order.html";
	public static final String TO_LOGIN = "sys_member_login.html";
	
	//Spring这里是通过实现ServletContextAware接口来注入ServletContext对象  
    private ServletContext servletContext;  

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
		Map<String, Object> dataMap = model.asMap();
		ForeContext.setData(dataMap, request);
		Site site = Context.getCurrentSite(request);

		User user = Context.getCurrentUser(request);
		if(user == null) {
			return site.getTemplate(TO_LOGIN);
		}
		dataMap.put("userId", user.getId());
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
	
	@ResponseBody
	@RequestMapping(value = "myOrderDelete.jspx", method = RequestMethod.POST)
	public String myOrderDelete(HttpServletRequest request, HttpServletResponse response, Model model,Integer orderId) {
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return "false";
		}
		pay.cancelOrder(orderId);
		return "true";
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

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
}
