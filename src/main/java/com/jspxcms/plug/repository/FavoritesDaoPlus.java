package com.jspxcms.plug.repository;

import java.util.List;

import com.jspxcms.plug.domain.Favorites;

public interface FavoritesDaoPlus {
	
	public void update (Favorites f);
	
	public List<Favorites> findByUserIdAndType(Integer userId,String type,Integer limitCount);
	
}
