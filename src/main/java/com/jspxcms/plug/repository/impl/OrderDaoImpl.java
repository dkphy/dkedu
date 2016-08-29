package com.jspxcms.plug.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.repository.OrderDaoPlus;

@Component
public class OrderDaoImpl implements OrderDaoPlus{
	@Transactional
	public void update(Order o) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("update Order o set o.buyerId = (:buyerId) " +
				",o.buyerName= (:buyerName)" +
				",o.totalMoney=(:totalMoney)"+
				",o.subject=(:subject)" +
				",o.channel=(:channel)"+
				",o.status=(:status)"+
				",o.receiverName=(:receiverName)"+
				",o.receiverPhone=(:receiverPhone)"+
				",o.receiverCityId=(:receiverCityId)"+
				",o.receiverDetailAddr=(:receiverDetailAddr)"+
				",o.buyerMess=(:buyerMess)"+
				",o.gmtCreate=(:gmtCreate)"+
				",o.gmtModify=(:gmtModify)"+
				",o.version=(:version) where o.id=(:orderId)");
		jpql.setParameter("buyerId", o.getBuyerId());
		jpql.setParameter("buyerName", o.getBuyerName());
		jpql.setParameter("totalMoney", o.getTotalMoney());
		jpql.setParameter("subject", o.getSubject());
		jpql.setParameter("channel", o.getChannel());
		jpql.setParameter("status", o.getStatus());
		jpql.setParameter("receiverName", o.getReceiverName());
		jpql.setParameter("receiverPhone", o.getReceiverPhone());
		jpql.setParameter("receiverCityId", o.getReceiverCityId());
		jpql.setParameter("receiverDetailAddr", o.getReceiverDetailAddr());
		jpql.setParameter("buyerMess", o.getBuyerMess());
		jpql.setParameter("gmtCreate", o.getGmtCreate());
		jpql.setParameter("gmtModify", o.getGmtModify());
		jpql.setParameter("version", o.getVersion());
		jpql.setParameter("orderId", o.getId());
		 jpql.createQuery(em);		
	}
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
