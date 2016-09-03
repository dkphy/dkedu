/**
 * 
 */
package com.jspxcms.plug.dto;

/**
 * @author YRee
 * 
 */
public class CreateClearingDTO {
	private Integer orderId;
	private String outTradeId;
	private String outBuyerAccount;
	private String channel;
	private Double tradeMoney;
	private String origMsg;
	private String status;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOutTradeId() {
		return outTradeId;
	}

	public void setOutTradeId(String outTradeId) {
		this.outTradeId = outTradeId;
	}

	public String getOutBuyerAccount() {
		return outBuyerAccount;
	}

	public void setOutBuyerAccount(String outBuyerAccount) {
		this.outBuyerAccount = outBuyerAccount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getOrigMsg() {
		return origMsg;
	}

	public void setOrigMsg(String origMsg) {
		this.origMsg = origMsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CreateClearingDTO [orderId=" + orderId + ", outTradeId="
				+ outTradeId + ", outBuyerAccount=" + outBuyerAccount
				+ ", channel=" + channel + ", tradeMoney=" + tradeMoney
				+ ", origMsg=" + origMsg + ", status=" + status + "]";
	}

	public CreateClearingDTO(Integer orderId, String outTradeId,
			String outBuyerAccount, String channel, Double tradeMoney,
			String origMsg, String status) {
		super();
		this.orderId = orderId;
		this.outTradeId = outTradeId;
		this.outBuyerAccount = outBuyerAccount;
		this.channel = channel;
		this.tradeMoney = tradeMoney;
		this.origMsg = origMsg;
		this.status = status;
	}

	public CreateClearingDTO() {
	}

}