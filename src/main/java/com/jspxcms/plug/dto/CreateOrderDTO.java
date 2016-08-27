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
	private Integer sellerId;

	private String receiverName;
	private String receiverPhone;
	private Integer receiverCityId;
	private String receiverDetailAddr;
	private Integer orderItemCount;
	private String buyerMess;

	public CreateOrderDTO(Integer buyerId, Integer sellerId,
			String receiverName, String receiverPhone, Integer receiverCityId,
			String receiverDetailAddr, Integer orderItemCount, String buyerMess) {
		super();
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverCityId = receiverCityId;
		this.receiverDetailAddr = receiverDetailAddr;
		this.orderItemCount = orderItemCount;
		this.buyerMess = buyerMess;
	}

	@Override
	public String toString() {
		return "CreateOrderDTO [buyerId=" + buyerId + ", sellerId=" + sellerId
				+ ", receiverName=" + receiverName + ", receiverPhone="
				+ receiverPhone + ", receiverCityId=" + receiverCityId
				+ ", receiverDetailAddr=" + receiverDetailAddr
				+ ", orderItemCount=" + orderItemCount + ", buyerMessage="
				+ buyerMess + "]";
	}

	public CreateOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
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
}
