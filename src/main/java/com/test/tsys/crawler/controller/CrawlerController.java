package com.test.tsys.crawler.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.tsys.crawler.models.RequestVO;
import com.test.tsys.crawler.service.ICrawlerService;
import com.test.tsys.crawler.util.Status;

/**
 * This controller will be used as web application crawler.
 * @author dk951_000
 *
 */
@RestController
@RequestMapping("/api/v1")
public class CrawlerController {

	@Autowired
	@Qualifier("CrawlerService")
	private ICrawlerService crawlerService;
	
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	@PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseEntity<String> onSubmit(@RequestParam(name="url") String url,
			@RequestParam(name="depth") String depth){
		
		RequestVO requestVO = new RequestVO();
		requestVO.setUrl(url);
		requestVO.setDepth(Long.valueOf(depth));
		String response = crawlerService.saveRequest(requestVO);
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value="/checkstatus",method=RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<String> checkStatus(@RequestParam(name="token") String token){	
		
		String response = crawlerService.checkStatus(token);
		return ResponseEntity.ok(response);
	}
	
	
	@RequestMapping(value="/getresponse",method=RequestMethod.GET)
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<Object> getResponse(@RequestParam(name="token") String token){	
		
		RequestVO requestVO = crawlerService.getJsonResponse(token);
		if(!Status.PROCESSED.getEnumValue().equals(requestVO.getStatus())) {
			String status = Arrays.stream(Status.values()).filter(a->a.getEnumValue().equals(requestVO.getStatus())).findAny().orElse(null).getEnumDescr();
			return ResponseEntity.ok("The response cannot fetched. The status of "+token+" is "+status);
		}
		return ResponseEntity.ok(requestVO.getResponse());
	}
	
}
