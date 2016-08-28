package com.jspxcms.plug.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.Favorites;
import com.jspxcms.plug.repository.FavoritesDao;
import com.jspxcms.plug.service.FavoritesService;
import com.sun.star.uno.RuntimeException;

@Service
public class FavoritesServiceImpl implements FavoritesService{
	
	@Autowired
	private FavoritesDao faDao;
	
	@Override
	@Transactional
	public void addCollectionItem(Integer userId, Integer objectId, String type) {
		if(userId == null){
			throw new RuntimeException("添加收藏：用户ID不能不为空");
		}
		if(objectId == null){
			throw new RuntimeException("添加收藏：内容ID不能不为空");
		}
		if(StringUtils.isBlank(type)){
			throw new RuntimeException("添加收藏：类型不能不为空");
		}
		Favorites fa = new Favorites();
		fa.setUserId(userId);
		fa.setObjectId(objectId);
		fa.setType(type);
		Date date = new Date();
		fa.setGmtCreate(date);
		fa.setGmtModify(date);
		fa.setVersion(1);
		
		faDao.save(fa);
	}

	@Override
	public List<Favorites> findByUserIdAndType(Integer userId, String type,
			Integer limitCount) {
		if(userId == null){
			throw new RuntimeException("查询收藏：用户ID不能不为空");
		}
		if(StringUtils.isBlank(type)){
			throw new RuntimeException("查询收藏：类型不能不为空");
		}
		if(limitCount == null){
			throw new RuntimeException("查询收藏：每页量不能不为空");
		}
		List<Favorites> list = faDao.findByUserIdAndType(userId, type, limitCount);
			
		return list;
	}


}
