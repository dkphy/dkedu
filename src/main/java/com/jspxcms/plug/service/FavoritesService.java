package com.jspxcms.plug.service;


import org.springframework.data.domain.Pageable;
import java.util.List;

import com.jspxcms.plug.domain.Favorites;

public interface FavoritesService {
	
	/**
	 * 添加到收藏
	 * @param userId
	 * @param objectId
	 * @param type
	 */
	public Favorites addCollectionItem(Integer userId,Integer objectId,String type);

	/**
	 * 按照类型和用户查找收藏
	 * @param userId
	 * @param type
	 * @param limitCount
	 * @return
	 */
	public List<Favorites> findByUserIdAndType(Integer userId,String type,Pageable pageable);
	
}
