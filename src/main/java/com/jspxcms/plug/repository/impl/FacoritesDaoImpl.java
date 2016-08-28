package com.jspxcms.plug.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.common.orm.Limitable;
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
		jpql.setParameter("gmtModify", f.getGmtModify());
		jpql.setParameter("version", f.getVersion());
		
		jpql.createQuery(em);
		
	}

	@Override
	public List<Favorites> findByUserIdAndType(Integer userId, String type,
			Limitable limitCount) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from Favorites f where f.userId in (:userId)" +
				" and f.type in (:type)");
		jpql.setParameter("userId", userId);
		jpql.setParameter("type", type);
		
		return jpql.list(em,limitCount);
	}
	
	@Override
	public List<Favorites> findByUserIdTypeAndCourseId(Integer userId,
			Integer objectId, String type) {
		
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from Favorites f where f.userId in (:userId)" +
				" and f.type in (:type) and f.objectId in (:objectId)");
		jpql.setParameter("userId", userId);
		jpql.setParameter("type", type);
		jpql.setParameter("objectId", objectId);
		
		return jpql.list(em);
	}
	
	private EntityManager em;
	
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
