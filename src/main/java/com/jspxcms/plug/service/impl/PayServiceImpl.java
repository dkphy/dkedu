/**
 * 
 */
package com.jspxcms.plug.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.SpecialService;
import com.jspxcms.plug.bo.MiniPayBo;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.OrderDetail;
import com.jspxcms.plug.dto.AddItemDTO;
import com.jspxcms.plug.dto.CreateClearingDTO;
import com.jspxcms.plug.dto.CreateOrderDTO;
import com.jspxcms.plug.repository.OrderDao;
import com.jspxcms.plug.service.PayService;
import com.jspxcms.plug.status.OrderStatus;
import com.sun.star.uno.RuntimeException;

/**
 * @author YRee
 * 
 */
@Service(value = "payService")
public class PayServiceImpl implements PayService {

	@Autowired
	private MiniPayBo miniPayBo;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private SpecialService specialService;
	@Autowired
	private InfoService infoService;

	@Transactional
	public Order createOrder(CreateOrderDTO coDTO) {
		specialService.addSoldCount(coDTO.getSubjectId(), coDTO.getOrderItemCount());
		return miniPayBo.createOrder(coDTO);
	}

	public void addItem(AddItemDTO aiDTO) {
		miniPayBo.addItem(aiDTO);
	}

	public void updateCount(Integer orderId, Integer skuId, Integer skuAmmount) {
		miniPayBo.updateCount(orderId, skuId, skuAmmount);
	}

	public void deleteItem(Integer orderId, Integer skuId) {
		miniPayBo.deleteItem(skuId, orderId);
	}

	public void cancelOrder(Integer orderId) {
		miniPayBo.cancelOrder(orderId);
	}

	public OrderDetail findDetailByOrderIdAndSkuId(Integer orderId,
			Integer skuId) {
		return miniPayBo.findDetailByOrderIdAndSkuId(orderId, skuId);
	}

	public Order findOrderByOrderId(Integer orderId) {
		return miniPayBo.findOrderByOrderId(orderId);
	}

	public List<Order> findOrderByBuyerId(Integer buyerId) {
		return miniPayBo.findOrderByBuyerId(buyerId);
	}

	public Double calculateTotalMoney(Integer orderId) {
		return miniPayBo.calculateTotalMoney(orderId);
	}

	public List<OrderDetail> findDetailsByOrderId(Integer orderId) {
		return miniPayBo.findDetailsByOrderId(orderId);
	}
	
	public void createClearing(CreateClearingDTO dto) {
		// 生成业务流水
		miniPayBo.createClearing(dto);
	}
	
	@Transactional
	public void processTradeResult(CreateClearingDTO ccDTO) {
		if(ccDTO == null) {
			throw new IllegalArgumentException("input is null");
		}
		Order order = this.miniPayBo.findOrderByOrderId(ccDTO.getOrderId());
		if(order == null) {
			throw new RuntimeException("no such order, orderId=" + ccDTO.getOrderId());
		}
		// 支付成功
		if(OrderStatus.PAID.equals(ccDTO.getStatus())) {
			// 已支付成功的，直接返回(应对重复通知)
			if(OrderStatus.PAID.equals(order.getStatus())) {
				return;
			}
			// 金额不一致，报错
			if(Math.abs(order.getTotalMoney() - ccDTO.getTradeMoney()) > 0.01) {
				throw new RuntimeException("order totalMoney is not equal, orderId=" + order.getId());
			}
			// 回填订单
			miniPayBo.backfillOrder(ccDTO.getOrderId(), OrderStatus.PAID,
					ccDTO.getChannel());
			addMessageNotify(order.getBuyerName(), order.getBuyerId(), order.getSubject());
		}
		// 支付失败
		else {
			// 已支付失败的，直接返回(应对重复通知)
			if(OrderStatus.FAIL.equals(order.getStatus())) {
				return;
			}
			// 回填订单
			miniPayBo.backfillOrder(ccDTO.getOrderId(), OrderStatus.FAIL,
					ccDTO.getChannel());
		}
	}
	
	// 新增站内信通知
	private void addMessageNotify(String username, int userId, String subject) {
		int nodeId = Node.USER_MESSAGE_NODE_ID; // XXX 使用栏目、文章记录站内信
		final int defaultSiteId = 1;
		String title = "成功购买课程";
		String fmt = "大康心理教育：亲爱的%1$s，你已成功购买课程《%2$s》，请及时前往我的课程页面进行学习。";
		String text = String.format(fmt, username, subject);
		Info bean = new Info();
		InfoDetail detail = new InfoDetail();
		detail.setTitle(title);
		Map<String, String> clobs = new HashMap<String, String>();
		clobs.put("text", text);
		String status = Info.CONTRIBUTION;
		infoService.save(bean, detail, null, null, null, null, null, clobs,
				null, null, null, null, null, nodeId, userId, status,
				defaultSiteId);
	}
	
	public boolean isOrderCanBePay(Order order) {
		return miniPayBo.isOrderCanBePay(order);
	}

	public boolean isOrderCanBePay(Integer orderId) {
		return miniPayBo.isOrderCanBePay(orderId);
	}

	public boolean isOrderCanBeCancel(Order order) {
		return miniPayBo.isOrderCanBeCancel(order);
	}

	public boolean isOrderCanBeCancel(Integer orderId) {
		return miniPayBo.isOrderCanBeCancel(orderId);
	}

	@Override
	public boolean isBuyed(Integer userId, Integer subjectId) {
		List<Order> list =  orderDao.findByUserIdAndSubjectIdAndStatus(userId, subjectId, OrderStatus.PAID);
		return list != null && !list.isEmpty();
	}

	@Override
	public List<Order> findOrderByUserIdAndStatus(Integer userId, String status) {
		if(userId==null){
			throw new RuntimeException("查询订单：用户ID不能为空");
		}
		if(StringUtils.isBlank(status)){
			throw new RuntimeException("查询订单：用户ID不能为空");
		}
		return orderDao.findByBuyerIdAndStatus(userId, status);
	}

	@Override
	public List<Order> findOrderByUserId(Integer userId) {
		if(userId==null){
			throw new RuntimeException("查询订单：用户ID不能为空");
		}
		return orderDao.findByUserId(userId);
	}

}
