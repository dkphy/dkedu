package com.jspxcms.plug.web.fore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jspxcms.core.domain.User;
import com.jspxcms.core.support.Context;
import com.jspxcms.plug.domain.Favorites;
import com.jspxcms.plug.service.FavoritesService;

@Controller
public class FavoritesController {
	
	@Resource
	private FavoritesService fas;
	
	@ResponseBody
	@RequestMapping(value = "/addFavorite.jspx",method = RequestMethod.POST)
	public String addConnection(Integer page, Integer objectId, String type,
			Model modMap, HttpServletRequest request){
		User user = Context.getCurrentUser(request);
		if(user == null) {
			return "false";
		}
		Favorites f = fas.addCollectionItem(user.getId(), objectId, type);
		if(f == null){
			return "false"; 
		}else{
			return "true";
		}
	}
}
