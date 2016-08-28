package com.jspxcms.plug.repository;

import java.util.List;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.domain.Favorites;

public interface BrowseDaoPlus {
	/**
	 * 据学员ID查询
	 * @param userId
	 * @param limitCount
	 * @return
	 */
	public List<Browse> findByUserId(Integer userId,Limitable limitCount);

	/**
	 * 据学员ID和课程ID查询
	 * @param userId
	 * @param courseId
	 * @return
	 */
	public List<Browse> findByUserIdAndCourseId(Integer userId,Integer courseId);
	
	/**
	 * 修改
	 * @param b
	 */
	public void update (Browse b);
}
