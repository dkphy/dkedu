/**
 * 
 */
package com.jspxcms.plug.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspxcms.plug.bo.MiniPayBo;
import com.jspxcms.plug.dto.AddItemDTO;
import com.jspxcms.plug.dto.CreateClearingDTO;
import com.jspxcms.plug.dto.CreateOrderDTO;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.OrderDetail;
import com.jspxcms.plug.service.PayService;
import com.jspxcms.plug.status.OrderStatus;

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

	public void tradeSuccess(CreateClearingDTO ccDTO) {
		// 生成业务流水
		miniPayBo.createClearing(ccDTO);
		// 回填订单
		miniPayBo.backfillOrder(ccDTO.getOrderId(), OrderStatus.PAID,
				ccDTO.getChannel());
	}

	public void tradeFail(CreateClearingDTO ccDTO) {
		// 生成业务流水
		miniPayBo.createClearing(ccDTO);
		// 回填订单
		miniPayBo.backfillOrder(ccDTO.getOrderId(), OrderStatus.FAIL,
				ccDTO.getChannel());
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
