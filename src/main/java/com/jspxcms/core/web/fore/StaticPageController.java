package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;

@Controller
public class StaticPageController {
	
	@RequestMapping(value = "/dk_introduce.jspx")
	public String introduce(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("dk_introduce.html");
	}
	
	@RequestMapping(value = "/dk_process.jspx")
	public String process(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("dk_process.html");
	}
	
	@RequestMapping(value = "/dk_exchange.jspx")
	public String exchange(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("dk_exchange.html");
	}
	
	@RequestMapping(value = "/dk_recruit.jspx")
	public String recruit(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("dk_recruit.html");
	}
	
	@RequestMapping(value = "/dk_connection.jspx")
	public String connection(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("dk_connection.html");
	}
	
	@RequestMapping(value = "/dk_building.jspx")
	public String building(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("dk_building.html");
	}
	
	@RequestMapping(value = "/list_new_news.jspx")
	public String newNews(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("list_new_news.html");
	}
	
	@RequestMapping(value = "/edu.jspx")
	public String dk_edu(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("a_index.html");
	}
	
	/**
	 * 网络课程列表页跳转
	 * @param page
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list_online.jspx")
	public String indexListOnline(Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("a_list_online_course.html");
	}
	/**
	 * 认证考试跳转
	 * @param page
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list_online_test.jspx")
	public String indexTest(Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("a_list_online_course_test.html");
	}
	/**
	 * 个人成长
	 * @param page
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list_online_person.jspx")
	public String indexPerson(Integer page, HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("a_list_online_course_person.html");
	}
	/**
	 * 帮助中心
	 * @param page
	 * @param userId
	 * @param modMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dk_help.jspx")
	public String helpCenter(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("a_help_center.html");
	}
	/**
	 * 成绩查询跳转
	 * @param page
	 * @param userId
	 * @param modMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dk_codeSearch.jspx")
	public String myCodeSearch(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("a_code_search.html");
	}
	
	
}
