package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Browse;

public interface BrowseDao extends Repository<Browse, Integer>{
	
	public Browse save(Browse bean);
	
	/**
	 * 据学员ID查询
	 * @param userId
	 * @param limitCount
	 * @return
	 */
	@Query("from Browse f where f.userId=?1")
	public List<Browse> findByUserId(Integer userId,Pageable pageable);

	/**
	 * 据学员ID和课程ID查询
	 * @param userId
	 * @param courseId
	 * @return
	 */
	@Query("from Browse b where b.userId=?1 and b.courseId=?2")
	public List<Browse> findByUserIdAndCourseId(Integer userId,Integer courseId);
	
	/**
	 * 修改
	 * @param b
	 */
	@Modifying
	@Query("update Browse f set f.gmtModify = now() where f.id = ?1")
	public void modifyBrowse (Integer id);
	
	@Query("from Browse b where b.id=?1")
	public Browse findById(Integer id);
}
