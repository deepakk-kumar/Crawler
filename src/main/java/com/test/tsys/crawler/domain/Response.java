package com.test.tsys.crawler.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Response extends BaseEntity{


	private static final long serialVersionUID = 4423984494838894584L;
	
	@Column
	private Long totalLinks;
	
	@Column
	private Long totalImages;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Detail> details;
	
	
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

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
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
