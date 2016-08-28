package com.jspxcms.plug.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.common.orm.Limitable;
import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.domain.Favorites;
import com.jspxcms.plug.repository.BrowseDaoPlus;
@Component
public class BrowseDaoImpl implements BrowseDaoPlus{

	@Override
	public List<Browse> findByUserId(Integer userId,Limitable limitCount) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from Browse b where userId in (:userId)");
		jpql.setParameter("userId", userId);
		
		return jpql.list(em,limitCount);
	}
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Browse> findByUserIdAndCourseId(Integer userId, Integer courseId) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from Browse b where b.userId in (:userId) and b.courseId in(:courseId)");
		jpql.setParameter("userId", userId);
		jpql.setParameter("courseId", courseId);
		
		return jpql.list(em);
	}

	@Override
	public void update(Browse f) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("update Browse f set f.userId = (:userId)," +
				"f.coursetId = (:courseId)," +
				"f.gmtModify = (:gmtModify),"+
				"f.version = (:version)");
		jpql.setParameter("userId", f.getUserId());
		jpql.setParameter("objectId", f.getCourseId());
		jpql.setParameter("gmtModify", f.getGmtModify());
		jpql.setParameter("version", f.getVersion());
		
		jpql.createQuery(em);
		
	}
}
