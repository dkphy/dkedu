package com.jspxcms.core.web.fore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

@Controller
public class UMemberController {
	
	private static final String MY_COURSE = "a_my_course.html";
	private static final String MY_PROFILE = "a_my_profile.html";
	private static final String MY_SECURE = "a_my_secure.html";
	private static final String MY_LIBRARY = "a_my_library.html";
	private static final String MY_SCORE = "a_my_score.html";
	private static final String MY_ORDER = "a_my_order.html";

	@RequestMapping(value = "myCourse.jspx")
	public String myCourse(HttpServletRequest request, HttpServletResponse response, Model model) {
		ForeContext.setData(model.asMap(), request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_COURSE);
	}
	
	@RequestMapping(value = "myProfile.jspx")
	public String myProfile(HttpServletRequest request, HttpServletResponse response, Model model) {
		ForeContext.setData(model.asMap(), request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_PROFILE);
	}
	
	@RequestMapping(value = "mySecure.jspx")
	public String mySecure(HttpServletRequest request, HttpServletResponse response, Model model) {
		ForeContext.setData(model.asMap(), request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_SECURE);
	}
	
	@RequestMapping(value = "myLibrary.jspx")
	public String myLibrary(HttpServletRequest request, HttpServletResponse response, Model model) {
		ForeContext.setData(model.asMap(), request);
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
	public String myOrder(HttpServletRequest request, HttpServletResponse response, Model model) {
		ForeContext.setData(model.asMap(), request);
		Site site = Context.getCurrentSite(request);
		return site.getTemplate(MY_ORDER);
	}
}
