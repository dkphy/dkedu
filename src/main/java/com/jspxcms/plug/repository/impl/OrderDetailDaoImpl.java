package com.jspxcms.plug.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.plug.domain.OrderDetail;
import com.jspxcms.plug.repository.OrderDetailDaoPlus;
@Component
public class OrderDetailDaoImpl implements OrderDetailDaoPlus {

	public void update(OrderDetail d) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("update OrderDetail d set d.orderId = (:orderId) " +
				"d.productionId= (:productionId)" +
				"d.skuId= (:skuId)" +
				"d.skuAmmount= (:skuAmmount)" +
				"d.productionName= (:productionName)" +
				"d.skuPrice= (:skuPrice)" +
				"d.skuDesc= (:skuDesc)" +
				"d.status= (:status)" +
				"d.gmtCreate= (:gmtCreate)" +
				"d.gmtModify= (:gmtModify)" +
				"d.version=(:version) where d.id=(:detailId)" );
		
		jpql.setParameter("orderId", d.getOrderId());
		jpql.setParameter("productionId", d.getProductionId());
		jpql.setParameter("skuId", d.getSkuId());
		jpql.setParameter("skuAmmount", d.getSkuAmmount());
		jpql.setParameter("productionName", d.getProductionName());
		jpql.setParameter("skuPrice", d.getSkuPrice());
		jpql.setParameter("skuDesc", d.getSkuDesc());
		jpql.setParameter("status", d.getStatus());
		jpql.setParameter("gmtCreate", d.getGmtCreate());
		jpql.setParameter("gmtModify", d.getGmtModify());
	    jpql.setParameter("version", d.getVersion());
	    jpql.setParameter("detailId", d.getId());
		 jpql.createQuery(em);				
	}
	private EntityManager em;
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
	public List<OrderDetail> findBy2(OrderDetail d) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from OrderDetail d  where d.orderId in (:orderId) and d.status in (:status)" );
		   jpql.setParameter("orderId", d.getOrderId());
		   jpql.setParameter("status", d.getStatus());
		return jpql.list(em);
	}
	public List<OrderDetail> findBy3(OrderDetail d) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from OrderDetail d  where d.orderId in (:orderId) and d.status in (:status) and d.skuId in (:skuId)" );
		   jpql.setParameter("orderId", d.getOrderId());
		   jpql.setParameter("status", d.getStatus());
		   jpql.setParameter("skuId", d.getSkuId());
		return jpql.list(em);	}
}
