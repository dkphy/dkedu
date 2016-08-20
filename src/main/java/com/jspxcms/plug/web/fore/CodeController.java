package com.jspxcms.plug.web.fore;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.plug.domain.ScoreStatus;
import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.service.CodeService;
import com.jspxcms.plug.web.back.ResumeController;

@Controller
public class CodeController {
	private static final Logger log = LoggerFactory
			.getLogger(ResumeController.class);
	
	@Resource
	private CodeService fc;
	
	@RequestMapping(value = "/findCode.jspx")
	public String findCode(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("myFindCode.html");
	}
	//录入成绩
	@RequestMapping(value = "/addCode.jspx")
	public String addCode(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("myAddCode.html");
	}
	//录入成功
	@RequestMapping(value = "/addSuccessCode.jspx")
	public String addSuccessCode(Integer page,String name,String idCard,Double score,Model modMap,HttpServletRequest request){
		log.info("entrer findCodeController");
		Site site = Context.getCurrentSite(request);
		modMap.addAttribute("codeList", fc.add(name, idCard,score));
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("myCodeSuccess.html");
	}
	
	//查询成绩
	@RequestMapping(value = "/getCode.jspx")
	public String findCode(Integer page,Integer userId,String name,String idCard,Model modMap,HttpServletRequest request){
		log.info("entrer findCodeController");
		Site site = Context.getCurrentSite(request);
		log.info("idCard="+idCard);
		UserCode u = fc.find(name, idCard);
		modMap.addAttribute("codeList", u);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("myCode.html");
	}
	
	//修改成绩
	@RequestMapping(value = "/updateCode.jspx")
	public String updateCode(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("updateCode.html");
	}
	
	//删除成绩
	@RequestMapping(value = "/deleteCode.jspx")
	public String deleteCode(Integer page,Integer userId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate("myCodeSuccess.html");
	}
	
}
