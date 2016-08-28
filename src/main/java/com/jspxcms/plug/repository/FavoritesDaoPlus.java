package com.jspxcms.plug.repository;

import java.util.List;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Favorites;

public interface FavoritesDaoPlus {
	
	public void update (Favorites f);
	
	public List<Favorites> findByUserIdAndType(Integer userId,String type,Limitable limitCount);
	
	public List<Favorites> findByUserIdTypeAndCourseId(Integer userId,Integer objectId,String type);
	
}
