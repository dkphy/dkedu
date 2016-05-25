package com.jspxcms.core.web.fore;

import java.util.List;
import java.util.Map;

import com.jspxcms.core.domain.Tag;

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

	private Integer[] tagIds;

	private String[] tagNames;

	public Integer[] getTagIds() {
		return tagIds;
	}

	public void setTagIds(Integer[] tagIds) {
		this.tagIds = tagIds;
	}

	public String[] getTagNames() {
		return tagNames;
	}

	public void setTagNames(String[] tagNames) {
		this.tagNames = tagNames;
	}

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
