package com.jspxcms.plug.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.plug.domain.Browse;
import com.jspxcms.plug.repository.BrowseDaoPlus;
@Component
public class BrowseDaoImpl implements BrowseDaoPlus{

	@Override
	public List<Browse> findByUserId(Integer userId,Integer limitCount) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from Browse b where userId in (:userId) limit(1,(:limitCount))");
		jpql.setParameter("userId", userId);
		jpql.setParameter("limitCount", limitCount);
		
		return jpql.list(em);
	}
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
