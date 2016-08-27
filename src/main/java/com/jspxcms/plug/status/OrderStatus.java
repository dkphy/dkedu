/**
 * 
 */
package com.jspxcms.plug.status;

/**
 * @author YRee
 * 
 */
public class OrderStatus {
	public static final String INIT = "init";
	public static final String WAITING = "waiting";
	public static final String PAID = "paid";
	/**
	 * 支付失败
	 */
	public static final String FAIL = "fail";
	/**
	 * 取消订单
	 */
	public static final String CANCEL = "cancel";
}
