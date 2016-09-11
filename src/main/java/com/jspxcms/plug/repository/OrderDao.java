/**
 * 
 */
package com.jspxcms.plug.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.domain.Order;

/**
 * @author YRee
 * 
 */
public interface OrderDao extends Repository<Order, Integer>,OrderDaoPlus{

	public Order findById(Integer id);

	@Transactional
	public void save(Order transientInstance);

	public List<Order> findByBuyerId(Integer buyerId);
	
	@Query(value="from Order o where o.buyerId=?1 and o.status=?2")
	public List<Order> findByBuyerIdAndStatus(Integer buyerId,String status);
	
	@Query(value="from Order o where o.buyerId=?1 and o.status!='cancel'")
	public List<Order> findByUserId(Integer buyerId);
	
	
	@Query(value="from Order o where o.buyerId=?1 and subjectId=?2 and status=?3")
	public List<Order> findByUserIdAndSubjectIdAndStatus(Integer userId, Integer subjectId, String status);


}