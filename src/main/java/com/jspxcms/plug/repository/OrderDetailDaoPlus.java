package com.jspxcms.plug.repository;

import java.util.List;

import com.jspxcms.plug.domain.OrderDetail;

public interface OrderDetailDaoPlus {
	public  void update(OrderDetail orderDetail);
	
	public List<OrderDetail> findBy2(OrderDetail d);

	public List<OrderDetail> findBy3(OrderDetail d);
}
