package com.jspxcms.plug.web.fore;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.plug.domain.Favorites;
import com.jspxcms.plug.service.FavoritesService;
import com.jspxcms.plug.web.back.ResumeController;

@Controller
public class FavoritesController {
	
	private static final Logger log = LoggerFactory
			.getLogger(ResumeController.class);
	@Resource
	private FavoritesService fas;
	
	@ResponseBody
	@RequestMapping(value = "/addFavorite.jspx",method = RequestMethod.POST)
	public String addConnection(Integer page,Integer userId,Integer objectId,String type,Model modMap,HttpServletRequest request){
		log.info("aa");
		Site site = Context.getCurrentSite(request);
		Map<String, Object> data = modMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		Favorites f = fas.addCollectionItem(userId, objectId, type);
		if(f == null){
			return "false"; 
		}else{
			return "true";
		}
	}
}
