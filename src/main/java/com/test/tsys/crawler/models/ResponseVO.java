package com.test.tsys.crawler.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseVO {

	@JsonProperty(value="total_links")
	private Long totalLinks;
	
	@JsonProperty(value="total_images")
	private Long totalImages;
	
	@JsonProperty(value="details")
	private List<DetailVO> details;
	
	@JsonIgnore
	private Date startTime;
	
	@JsonIgnore
	private Date endTime;
	
	public Long getTotalLinks() {
		return totalLinks;
	}
	public void setTotalLinks(Long totalLinks) {
		this.totalLinks = totalLinks;
	}
	public Long getTotalImages() {
		return totalImages;
	}
	public void setTotalImages(Long totalImages) {
		this.totalImages = totalImages;
	}
	public List<DetailVO> getDetails() {
		return details;
	}
	public void setDetails(List<DetailVO> details) {
		this.details = details;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
