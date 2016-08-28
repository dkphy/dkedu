package com.jspxcms.plug.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.repository.BrowseDao;
import com.jspxcms.plug.service.BrowseService;
import com.sun.star.uno.RuntimeException;

@Service
public class BrowseServiceImpl implements BrowseService {

	@Autowired
	private BrowseDao boDao;

	@Override
	@Transactional
	public Browse addBrowse(Integer userId, Integer courseId) {
		if (userId == null) {
			throw new RuntimeException("添加浏览记录：用户Id不能为空");
		}
		if (courseId == null) {
			throw new RuntimeException("添加浏览记录：课程Id不能为空");
		}
		List<Browse> list = boDao.findByUserIdAndCourseId(userId, courseId);
		if (list == null || list.isEmpty()) {
			// 无浏览记录，则新增
			Browse b = new Browse();
			b.setCourseId(courseId);
			b.setUserId(userId);
			Date date = new Date();
			b.setGmtCreate(date);
			b.setGmtModify(date);
			b.setVersion(0);
			boDao.save(b);
			return b;
		} else if (list.size() == 1) {
			// 有且只有1条浏览记录，更新对应时间
			Browse b = list.get(0);
			b.setVersion(b.getVersion() + 1);
			boDao.modifyBrowse(b.getId());
			return b;
		}
		// 存在多条，属于数据异常
		throw new RuntimeException("添加浏览记录：当前添加对象异常");
	}

	@Override
	public List<Browse> findByUserId(Integer userId, Pageable pageable) {
		if (userId == null) {
			throw new RuntimeException("查询浏览记录：用户Id不能为空");
		}
		if (pageable == null) {
			throw new RuntimeException("查询浏览记录：课程Id不能为空");
		}

		List<Browse> list = boDao.findByUserId(userId, pageable);

		return list;
	}

}
