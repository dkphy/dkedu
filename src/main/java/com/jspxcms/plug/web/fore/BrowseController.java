package com.jspxcms.plug.web.fore;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.service.BrowseService;
import com.jspxcms.plug.web.back.ResumeController;

@Controller
public class BrowseController {
	private static final Logger log = LoggerFactory
			.getLogger(ResumeController.class);
	
	@Resource
	private BrowseService bs;

	@ResponseBody
	@RequestMapping(value = "/addLog.jspx")
	public boolean addLog(Integer page,Integer userId,Integer courseId,Model modMap,HttpServletRequest request){
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		Browse b = bs.addBrowse(userId, courseId);
		if(b == null){
			return false; 
		}else{
			return true;
		}
	}
	
	
}