package com.jspxcms.plug.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="cms_favorites")
public class Favorites {
	private Integer id;
	private Integer userId;
	private Integer objectId;
	private String objectSmallImage;
	private String objectName;
	private Integer objectViewCount;
	private Integer objectTag;
	private Double objectPrice;
	private String type;
	private Date gmtCreate;
	private Date gmtModify;
	private Integer version;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_favorites", pkColumnValue = "cms_favorites", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_favorites")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "object_id")
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "GMT_CREATE")
	public Date getGmtCreate() {
		return gmtCreate;
	}
	
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "GMT_MODIFY")
	public Date getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}
	@Column(name = "VERSION")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "object_smallImage")
	public String getObjectSmallImage() {
		return objectSmallImage;
	}
	public void setObjectSmallImage(String objectSmallImage) {
		this.objectSmallImage = objectSmallImage;
	}
	@Column(name = "object_name")
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	@Column(name = "object_view_count")
	public Integer getObjectViewCount() {
		return objectViewCount;
	}
	public void setObjectViewCount(Integer objectViewCount) {
		this.objectViewCount = objectViewCount;
	}
	@Column(name = "object_tag")
	public Integer getObjectTag() {
		return objectTag;
	}
	public void setObjectTag(Integer objectTag) {
		this.objectTag = objectTag;
	}
	@Column(name = "object_price")
	public Double getObjectPrice() {
		return objectPrice;
	}
	public void setObjectPrice(Double objectPrice) {
		this.objectPrice = objectPrice;
	} 

}
