/**
 * 
 */
package com.jspxcms.plug.dto;

/**
 * @author YRee
 * 
 */
public class AddItemDTO {
	private Integer productionId;
	private Integer orderId;
	private String productionName;
	private Integer skuId;
	private Integer skuAmmount;
	private Double skuPrice;

	public AddItemDTO() {
		super();
	}

	public Integer getProductionId() {
		return productionId;
	}

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuAmmount() {
		return skuAmmount;
	}

	public void setSkuAmmount(Integer skuAmmount) {
		this.skuAmmount = skuAmmount;
	}

	public Double getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(Double skuPrice) {
		this.skuPrice = skuPrice;
	}

	public AddItemDTO(Integer productionId, Integer orderId,
			String productionName, Integer skuId, Integer skuAmmount,
			Double skuPrice) {
		super();
		this.productionId = productionId;
		this.orderId = orderId;
		this.productionName = productionName;
		this.skuId = skuId;
		this.skuAmmount = skuAmmount;
		this.skuPrice = skuPrice;
	}

	@Override
	public String toString() {
		return "AddItemDTO [productionId=" + productionId + ", orderId="
				+ orderId + ", productionName=" + productionName + ", skuId="
				+ skuId + ", skuAmmount=" + skuAmmount + ", skuPrice="
				+ skuPrice + "]";
	}

}
