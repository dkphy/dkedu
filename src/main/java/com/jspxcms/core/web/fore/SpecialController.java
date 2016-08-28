package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.domain.Special;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.plug.domain.Browse;
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
			HttpServletRequest request, org.springframework.ui.Model modelMap) {
		Special special = service.get(id);
		modelMap.addAttribute("special", special);
		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		User user = Context.getCurrentUser(request);
		//浏览记录
		if(user != null) {
			bs.addBrowse(user.getId(), id);
		}
		
		return special.getTemplate();
	}

	@Autowired
	private SpecialService service;
	
	@Autowired
	private BrowseService bs;
}
