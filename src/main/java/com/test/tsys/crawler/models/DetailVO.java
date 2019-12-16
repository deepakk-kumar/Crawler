package com.test.tsys.crawler.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailVO {

	@JsonProperty(value="page_title")
	private String pageTitle;
	
	@JsonProperty(value="page_link")
	private String pageLink;
	
	@JsonProperty(value="image_count")
	private Long imageCount;
	
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageLink() {
		return pageLink;
	}
	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}
	public Long getImageCount() {
		return imageCount;
	}
	public void setImageCount(Long imageCount) {
		this.imageCount = imageCount;
	}
	
	
	
}
