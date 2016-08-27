/**
 * 
 */
package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.Clearing;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.OrderDetail;
import com.jspxcms.plug.domain.Resume;

/**
 * @author YRee
 * 
 */
public interface OrderDetailDao extends
Repository<OrderDetail, Integer>,OrderDetailDaoPlus{

	@Transactional
	public  void save(OrderDetail transientInstance);
	
	

	//public abstract List findByProductionId(java.lang.Integer productionId);

//	public abstract OrderDetail findById(java.lang.Integer id);

//	public abstract List findByExample(OrderDetail instance);

//	public abstract List findByOrderId(Object orderId);

//	public abstract List findBySkuId(Object skuId);

//	public abstract List findBySkuName(Object skuName);


}