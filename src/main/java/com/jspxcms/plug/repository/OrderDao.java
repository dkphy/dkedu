/**
 * 
 */
package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.Clearing;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.Resume;

/**
 * @author YRee
 * 
 */
public interface OrderDao extends Repository<Order, Integer>,OrderDaoPlus{


	public Order findById(Integer id);

	@Transactional
	public void save(Order transientInstance);

	//public abstract List findByExample(Order instance);

	public List findByBuyerId(Object buyerId);
	
	@Query(value="from Order o where o.buyerId=?1 and subjectId=?2")
	public List<Order> findByUserIdAndSubjectId(Integer userId, Integer subjectId);

//	public abstract List findByBuyerName(Object buyerName);

	//public abstract List findByRecevierName(Object recevierName);

//	public abstract List findByRecevierPhone(Object recevierPhone);


}