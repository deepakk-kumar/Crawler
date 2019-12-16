package com.test.tsys.crawler.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Detail extends BaseEntity{

	private static final long serialVersionUID = -1233030975354390632L;

	@Column
	private String pageTitle;
	
	@Column
	private String pageLink;
	
	@Column
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
