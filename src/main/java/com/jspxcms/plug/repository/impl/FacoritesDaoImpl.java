package com.jspxcms.plug.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.plug.domain.Favorites;
import com.jspxcms.plug.repository.FavoritesDaoPlus;

@Component
public class FacoritesDaoImpl implements FavoritesDaoPlus{

	@Override
	public void update(Favorites f) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("update Favorites f set f.userId = (:userId)," +
				"f.objectId = (:objectId),f.type=(:type)," +
				"f.gmtModify = (:gmtModify),"+
				"f.version = (:version)");
		jpql.setParameter("userId", f.getUserId());
		jpql.setParameter("objectId", f.getObjectId());
		jpql.setParameter("type", f.getType());
		jpql.setParameter("version", f.getVersion());
		
		jpql.createQuery(em);
		
	}

	@Override
	public List<Favorites> findByUserIdAndType(Integer userId, String type,
			Integer limitCount) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from Favorites f where f.userId in (:userId)" +
				" and f.type in (:type) limit(1,(:limitCount)) ");
		jpql.setParameter("userId", userId);
		jpql.setParameter("type", type);
		jpql.setParameter("limitCount", limitCount);
		
		return jpql.list(em);
	}
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
