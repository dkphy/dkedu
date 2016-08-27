package com.jspxcms.plug.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OrderDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "pay_order_detail")
public class OrderDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Integer orderId;
	private Integer productionId;
	private Integer skuId;
	private Integer skuAmmount;
	private String productionName;
	private Double skuPrice;
	private String skuDesc;
	private String status;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public OrderDetail() {
	}
	@Id
	@Column(name = "detail_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "order_id")
	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "production_id")
	public Integer getProductionId() {
		return productionId;
	}

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}

	@Column(name = "sku_id")
	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	@Column(name = "sku_ammount")
	public Integer getSkuAmmount() {
		return skuAmmount;
	}

	public void setSkuAmmount(Integer skuAmmount) {
		this.skuAmmount = skuAmmount;
	}

	@Column(name = "production_name")
	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	@Column(name = "sku_price")
	public Double getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(Double skuPrice) {
		this.skuPrice = skuPrice;
	}

	@Column(name = "sku_desc")
	public String getSkuDesc() {
		return skuDesc;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public OrderDetail(Integer id, Integer orderId, Integer productionId,
			Integer skuId, Integer skuAmmount, String productionName,
			Double skuPrice, String skuDesc, String status, Date gmtCreate,
			Date gmtModify, Integer version) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productionId = productionId;
		this.skuId = skuId;
		this.skuAmmount = skuAmmount;
		this.productionName = productionName;
		this.skuPrice = skuPrice;
		this.skuDesc = skuDesc;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}



}