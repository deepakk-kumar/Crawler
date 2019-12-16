package com.test.tsys.crawler.service;

import com.test.tsys.crawler.models.RequestVO;

public interface ICrawlerService {

	public void getResponseForURL(String url,Long depth);
	
	public String saveRequest(RequestVO requestVO);

	public String checkStatus(String token);

	public RequestVO getJsonResponse(String token);
}
