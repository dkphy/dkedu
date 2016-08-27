package com.jspxcms.plug.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clearing entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pay_clearing")
public class Clearing implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Integer orderId;
	private String outTradeId;
	private String outBuyerAccount;
	private String channel;
	private Double tradeMoney;
	private String origMsg;
	private String status;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public Clearing() {
	}



	public Clearing(Integer id, Integer orderId, String outTradeId,
			String outBuyerAccount, String channel, Double tradeMoney,
			String origMsg, String status, Date gmtCreate, Date gmtModify,
			Integer version) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.outTradeId = outTradeId;
		this.outBuyerAccount = outBuyerAccount;
		this.channel = channel;
		this.tradeMoney = tradeMoney;
		this.origMsg = origMsg;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}



	// Property accessors
	@Id
	@Column(name = "clearing_id",unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	public Integer getId() {
		return id;
	}
	
	
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	@Column(name = "order_id")
	public Integer getOrderId() {
		return this.orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	@Column(name = "out_trade_id")
	public String getOutTradeId() {
		return this.outTradeId;
	}

	public void setOutTradeId(String outTradeId) {
		this.outTradeId = outTradeId;
	}

	@Column(name = "out_buyer_account")
	public String getOutBuyerAccount() {
		return this.outBuyerAccount;
	}

	public void setOutBuyerAccount(String outBuyerAccount) {
		this.outBuyerAccount = outBuyerAccount;
	}

	@Column(name = "channel")
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "trade_money")
	public Double getTradeMoney() {
		return this.tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	@Column(name = "orig_msg")
	public String getOrigMsg() {
		return this.origMsg;
	}

	public void setOrigMsg(String origMsg) {
		this.origMsg = origMsg;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "GMT_CREATE")
	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Column(name = "GMT_MODIFY")
	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}