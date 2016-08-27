/**
 * 
 */
package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.Clearing;
import com.jspxcms.plug.domain.Resume;

/**
 * @author YRee
 *
 */
public interface ClearingDao extends Repository<Clearing, Integer>,ClearingDaoPlus{

	@Transactional
	public  void save(Clearing transientInstance);

	//public abstract Clearing findById(java.lang.Integer id);

	//public abstract List findByExample(Clearing instance);

	//public abstract List findByOrderId(Object orderId);

//	public abstract List findByOutTradeId(Object outTradeId);

	//public abstract List findByOutBuyerAccount(Object outBuyerAccount);

}