/**
 * 
 */
package com.jspxcms.plug.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.plug.bo.MiniPayBo;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.OrderDetail;
import com.jspxcms.plug.dto.AddItemDTO;
import com.jspxcms.plug.dto.CreateClearingDTO;
import com.jspxcms.plug.dto.CreateOrderDTO;
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
	MiniPayBo miniPayBo;

	public Order createOrder(CreateOrderDTO coDTO) {
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
	

}
