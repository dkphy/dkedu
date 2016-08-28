/**
 * 
 */
package com.jspxcms.plug.dto;

/**
 * @author YRee
 * 
 */
public class CreateOrderDTO {
	private Integer buyerId; //
	private Integer orderItemCount;
	private Double totalMoney;
	private String subject;
	private Integer subjectId;

	private Integer sellerId;
	private String receiverName;
	private String receiverPhone;
	private Integer receiverCityId;
	private String receiverDetailAddr;
	private String buyerMess;

	public CreateOrderDTO() {
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Integer getReceiverCityId() {
		return receiverCityId;
	}

	public void setReceiverCityId(Integer receiverCityId) {
		this.receiverCityId = receiverCityId;
	}

	public String getReceiverDetailAddr() {
		return receiverDetailAddr;
	}

	public void setReceiverDetailAddr(String receiverDetailAddr) {
		this.receiverDetailAddr = receiverDetailAddr;
	}

	public Integer getOrderItemCount() {
		return orderItemCount;
	}

	public void setOrderItemCount(Integer orderItemCount) {
		this.orderItemCount = orderItemCount;
	}

	public String getBuyerMess() {
		return buyerMess;
	}

	public void setBuyerMess(String buyerMess) {
		this.buyerMess = buyerMess;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
}
