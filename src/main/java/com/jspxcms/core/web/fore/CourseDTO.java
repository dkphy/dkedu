package com.jspxcms.core.web.fore;

import org.apache.commons.lang3.StringUtils;

public class CourseDTO {

	private Integer id;
	private String title;
	private String metaKeywords;
	private String metaDescription;
	private String specialTemplate;
	private String smallImage;
	private String largeImage;
	private Integer refers;
	private Integer soldCount;
	private Integer views;
	private Boolean withImage;
	private Integer recommend;
	private Integer comments;
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMetaKeywords() {
		if(metaKeywords == null) {
			return StringUtils.EMPTY;
		}
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getSpecialTemplate() {
		return specialTemplate;
	}

	public void setSpecialTemplate(String specialTemplate) {
		this.specialTemplate = specialTemplate;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public Integer getRefers() {
		if(refers == null) {
			return 0;
		}
		return refers;
	}

	public void setRefers(Integer refers) {
		this.refers = refers;
	}

	public Integer getSoldCount() {
		if(soldCount == null) {
			return 0;
		}
		return soldCount;
	}

	public void setSoldCount(Integer soldCount) {
		this.soldCount = soldCount;
	}

	public Integer getViews() {
		if(views == null) {
			return 0;
		}
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Boolean getWithImage() {
		return withImage;
	}

	public void setWithImage(Boolean withImage) {
		this.withImage = withImage;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getComments() {
		if(comments == null) {
			return 0;
		}
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Double getPrice() {
		if(price == null) {
			return 0.0;
		}
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
