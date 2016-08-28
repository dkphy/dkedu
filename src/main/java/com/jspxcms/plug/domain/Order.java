package com.jspxcms.plug.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Order entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pay_order")
public class Order implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Integer buyerId;
	private String buyerName;

	private Double totalMoney;
	private String subject;
	private Integer subjectId;
	private String channel;
	private String status;
	private String receiverName;
	private String receiverPhone;
	private Integer receiverCityId;
	private String receiverDetailAddr;
	private String buyerMess;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public Order() {
	}
	@Id
	@Column(name = "order_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "buyer_id")
	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	@Column(name = "buyer_name")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	@Column(name = "total_money")
	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	@Column(name = "subject")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name = "subject_id")
	public Integer getSubjectId() {
		return subjectId;
	}
	
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	@Column(name = "channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "receiver_name")
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	@Column(name = "receiver_phone")
	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	@Column(name = "receiver_city_id")
	public Integer getReceiverCityId() {
		return receiverCityId;
	}

	public void setReceiverCityId(Integer receiverCityId) {
		this.receiverCityId = receiverCityId;
	}
	
	@Column(name = "receiver_detail_addr")
	public String getReceiverDetailAddr() {
		return receiverDetailAddr;
	}

	public void setReceiverDetailAddr(String receiverDetailAddr) {
		this.receiverDetailAddr = receiverDetailAddr;
	}
	@Column(name = "buyer_mess")
	public String getBuyerMess() {
		return buyerMess;
	}

	public void setBuyerMess(String buyerMess) {
		this.buyerMess = buyerMess;
	}
	@Column(name = "GMT_CREATE")
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Column(name = "GMT_MODIFY")
	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	public Order(Integer id, Integer buyerId, String buyerName,
			Double totalMoney, String subject, String channel, String status,
			String receiverName, String receiverPhone, Integer receiverCityId,
			String receiverDetailAddr, String buyerMess, Date gmtCreate,
			Date gmtModify, Integer version) {
		super();
		this.id = id;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.totalMoney = totalMoney;
		this.subject = subject;
		this.channel = channel;
		this.status = status;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverCityId = receiverCityId;
		this.receiverDetailAddr = receiverDetailAddr;
		this.buyerMess = buyerMess;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}


}