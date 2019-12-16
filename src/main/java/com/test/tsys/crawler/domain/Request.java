package com.test.tsys.crawler.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Request extends BaseEntity{
	
	private static final long serialVersionUID = 4893799712290179195L;

	@Column
	private String url;
	
	@Column(name="RESPONSE_ID")
	private Long responseId;
	
	@Column
	private String ackToken;
	
	@Column
	private String status;
	
	@OneToOne
	@JoinColumn(name="RESPONSE_ID", referencedColumnName="ID", insertable=false,updatable=false)
	private Response response;

	@Column
	private Long depth;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

	public String getAckToken() {
		return ackToken;
	}

	public void setAckToken(String ackToken) {
		this.ackToken = ackToken;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}
	
	
}
