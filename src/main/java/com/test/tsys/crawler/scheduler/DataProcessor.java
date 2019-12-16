package com.test.tsys.crawler.scheduler;

import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.test.tsys.crawler.domain.Request;
import com.test.tsys.crawler.service.ICrawlerService;

@Component
@Scope(value="prototype")
public class DataProcessor implements Runnable{

	@Autowired
	@Qualifier("commonsPool2TargetSource")
	private CommonsPool2TargetSource commonsPool2TargetSource;
	
	@Autowired
	private ICrawlerService crawlerService;
	
	private Request request;
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public void run() {
		
		try {
			crawlerService.getResponseForURL(request.getUrl(), request.getDepth());
		}finally {
			try {
				commonsPool2TargetSource.releaseTarget(this);
				request=null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	

}
