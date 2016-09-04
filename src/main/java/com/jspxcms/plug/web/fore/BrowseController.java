package com.jspxcms.plug.web.fore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.service.BrowseService;

@Controller
public class BrowseController {
	
	@Resource
	private BrowseService bs;

	@ResponseBody
	@RequestMapping(value = "/addLog.jspx")
	public boolean addLog(Integer page,Integer userId,Integer courseId,Model modMap,HttpServletRequest request){
		Browse b = bs.addBrowse(userId, courseId);
		if(b == null){
			return false; 
		}else{
			return true;
		}
	}
	
	
}