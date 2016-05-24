package com.jspxcms.core.web.fore;

import java.util.Map;

public class SimpleInfoDTO {

	private Integer id;

	private String url;

	private String title;

	private String smallImageUrl;

	private String description;

	private Integer bufferViews;

	private Map<String, String> customs;
	
	private String highlightTitle;
	
	private String highlightText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBufferViews() {
		return bufferViews;
	}

	public void setBufferViews(Integer bufferViews) {
		this.bufferViews = bufferViews;
	}

	public Map<String, String> getCustoms() {
		return customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	public String getHighlightTitle() {
		return highlightTitle;
	}

	public void setHighlightTitle(String highlightTitle) {
		this.highlightTitle = highlightTitle;
	}

	public String getHighlightText() {
		return highlightText;
	}

	public void setHighlightText(String highlightText) {
		this.highlightText = highlightText;
	}
}
