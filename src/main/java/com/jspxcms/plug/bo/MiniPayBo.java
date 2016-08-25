/**
 * 
 */
package com.jspxcms.plug.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jspxcms.plug.dto.AddItemDTO;
import com.jspxcms.plug.dto.CreateClearingDTO;
import com.jspxcms.plug.dto.CreateOrderDTO;
import com.jspxcms.plug.domain.Clearing;
import com.jspxcms.plug.domain.Order;
import com.jspxcms.plug.domain.OrderDetail;

/**
 * @author YRee
 * 
 */
public interface MiniPayBo {
	/**
	 * 创建订单
	 * 
	 * @param coDTO
	 * @return
	 */
	public Order createOrder(CreateOrderDTO coDTO);

	/**
	 * 向订单中添加购买项,其实就是新增detail中的东西
	 * 
	 * @param aiDTO
	 */
	public void addItem(AddItemDTO aiDTO);

	/**
	 * 修改订单中某件商品的数量
	 * 
	 * @param orderId
	 * @param skuId
	 * @param skuAmmount
	 */
	public void updateCount(Integer orderId, Integer skuId, Integer skuAmmount);

	/**
	 * 通过orderId和skuId查询detail
	 * 
	 * @param orderId
	 * @param skuId
	 * @return
	 */
	public OrderDetail findDetailByOrderIdAndSkuId(Integer orderId,
			Integer skuId);

	/**
	 * 删除某个订单中的某个商品
	 * 
	 * @param skuId
	 * @param orderId
	 */
	public void deleteItem(Integer skuId, Integer orderId);

	/**
	 * 通过orderId查找订单
	 * 
	 * @param orderId
	 * @return
	 */
	public Order findOrderByOrderId(Integer orderId);

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 */
	public void cancelOrder(Integer orderId);

	/**
	 * 查找某个用户的所有订单
	 * 
	 * @param buyerId
	 * @return
	 */
	public List<Order> findOrderByBuyerId(Integer buyerId);

	/**
	 * 计算某个订单的总价
	 * 
	 * @param orderId
	 * @return
	 */
	public Double calculateTotalMoney(Integer orderId);

	/**
	 * 通过orderId查询明细
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OrderDetail> findDetailsByOrderId(Integer orderId);

	/**
	 * 生成流水
	 * 
	 * @param ccDTO
	 * @return
	 */
	public Clearing createClearing(CreateClearingDTO ccDTO);

	/**
	 * 判断是否够支付
	 * 
	 * @param order
	 * @return
	 */
	public boolean isOrderCanBePay(Order order);

	/**
	 * 判断是否够支付
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean isOrderCanBePay(Integer orderId);

	/**
	 * 盘点是否能被取消
	 * 
	 * @param order
	 * @return
	 */
	public boolean isOrderCanBeCancel(Order order);

	/**
	 * 盘点是否能被取消
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean isOrderCanBeCancel(Integer orderId);

	/**
	 * 回填订单
	 * 
	 * @param orderId
	 * @param channel
	 */
	public void backfillOrder(Integer orderId, String status, String channel);

}
