package com.test.tsys.crawler.models;

public class RequestVO {
	
	private String url;
	private Long depth;
	private Long responseId;
	private String ackToken;	
	private ResponseVO response;
	private String status;
	
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
	public ResponseVO getResponse() {
		return response;
	}
	public void setResponse(ResponseVO response) {
		this.response = response;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
