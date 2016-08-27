package com.jspxcms.plug.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.repository.BrowseDao;
import com.jspxcms.plug.service.BrowseService;
import com.sun.star.uno.RuntimeException;

@Service
public class BrowseServiceImpl implements BrowseService {

	@Autowired
	private BrowseDao boDao;
	
	@Override
	public void addBrowse(Integer userId, Integer courseId) {
		if(userId == null){
			throw new RuntimeException("添加浏览记录：用户Id不能为空");
		}
		if(courseId == null){
			throw new RuntimeException("添加浏览记录：课程Id不能为空");
		}
		Browse b = new Browse();
		b.setCourseId(courseId);
		b.setUserId(userId);
		Date date = new Date();
		b.setGmtCreate(date);
		b.setGmtModify(date);
		b.setVersion(1);
		
		boDao.save(b);
	}

	@Override
	public List<Browse> findByUserId(Integer userId, Integer limitCount) {
		if(userId == null){
			throw new RuntimeException("查询浏览记录：用户Id不能为空");
		}
		if(limitCount == null){
			throw new RuntimeException("查询浏览记录：课程Id不能为空");
		}
		
		List<Browse> list = boDao.findByUserId(userId, limitCount);
		
		return list;
	}

}
