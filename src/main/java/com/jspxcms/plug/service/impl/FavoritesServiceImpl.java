package com.jspxcms.plug.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Favorites;
import com.jspxcms.plug.repository.FavoritesDao;
import com.jspxcms.plug.service.FavoritesService;
import com.sun.star.uno.RuntimeException;

@Service
public class FavoritesServiceImpl implements FavoritesService{
	
	@Autowired
	private FavoritesDao faDao;
	
	@Transactional
	public Favorites addCollectionItem(Integer userId, Integer objectId, String type) {
		if(userId == null){
			throw new RuntimeException("添加收藏：用户ID不能不为空");
		}
		if(objectId == null){
			throw new RuntimeException("添加收藏：内容ID不能不为空");
		}
		if(StringUtils.isBlank(type)){
			throw new RuntimeException("添加收藏：类型不能不为空");
		}
		System.out.println("添加收藏：验证完毕");
		//查询
		List<Favorites> list = faDao.findByUserIdTypeAndCourseId(userId, objectId, type);
		if(list.size()>1){
			throw new RuntimeException("添加收藏:添加异常");
		}
		Favorites fa  = null;
		if(list.size()==1){
		    fa = new Favorites();
			fa.setUserId(userId);
			fa.setObjectId(objectId);
			fa.setType(type);
			Date date = new Date();
			fa.setGmtCreate(date);
			fa.setGmtModify(date);
			fa.setVersion(1);
			
			faDao.save(fa);
		}
		if(list.size()==1){
			fa = list.get(0);
			Date date = new Date();
			fa.setGmtModify(date);
			fa.setVersion(fa.getVersion()+1);
			faDao.update(fa);
		}
		return fa;
	}

	@Override
	public List<Favorites> findByUserIdAndType(Integer userId, String type,
			Limitable limitCount) {
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
