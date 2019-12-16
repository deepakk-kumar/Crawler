package com.test.tsys.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.test.tsys.crawler.domain.Request;
import com.test.tsys.crawler.domain.Response;
import com.test.tsys.crawler.mapper.RequestMapper;
import com.test.tsys.crawler.mapper.ResponseMapper;
import com.test.tsys.crawler.models.DetailVO;
import com.test.tsys.crawler.models.RequestVO;
import com.test.tsys.crawler.models.ResponseVO;
import com.test.tsys.crawler.repository.IRequestRepository;
import com.test.tsys.crawler.repository.IResponseRepository;
import com.test.tsys.crawler.util.Status;

@Service(value="CrawlerService")
public class CrawlerServiceImpl implements ICrawlerService{

	private static final Logger log = LoggerFactory.getLogger(CrawlerServiceImpl.class);

	@Autowired
	private IRequestRepository requestRepository;
	
	@Autowired
	private IResponseRepository responseRepository;
	
	private static HashSet<String> links = new HashSet<>();	
	private static final String TOKEN_MESSAGE = "Your Request has been successfully submitted with us with token no:";
	private static final String ERROR_MESSAGE = "The Request for the given url has already been initiated. Please refer token no:";
	
	private ResponseMapper responseMapper = Mappers.getMapper(ResponseMapper.class);
	private RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);

	@Override
	public void getResponseForURL(String URL,Long depth) {
		Request request = requestRepository.findByUrlAndStatus(URL,Status.INPROGRESS.getEnumValue());
		try {
			log.debug("Processing async request for token: "+ request.getAckToken());
			ResponseVO responseVo = new ResponseVO();
			responseVo.setDetails(new ArrayList<DetailVO>());
			responseVo.setStartTime(new Date());
			crawlURL(URL,responseVo,depth);
			responseVo.setEndTime(new Date());
			Response response = responseRepository.save(responseMapper.convertResponseVOToResponse(responseVo));
			request.setResponseId(response.getId());
			request.setStatus(Status.PROCESSED.getEnumValue());
			log.debug("Processing completed for token: "+ request.getAckToken());
		}catch(Exception e) {
			log.error("Processing failed for url: "+ URL +" "+e.getMessage());
			request.setStatus(Status.FAILED.getEnumValue());
		}finally {
			requestRepository.save(request);
		}
	}
	
	private void crawlURL(String url,ResponseVO response,Long depth) throws Exception {
		if(depth==0) {
			return;
		}
        try {
            Document document = Jsoup.connect(url).get();
            Elements linksOnPage = document.getElementsByTag("a");
            Elements images = document.getElementsByTag("img");
            response.setTotalLinks(response.getTotalLinks()==null?1:1+response.getTotalLinks());
            response.setTotalImages(response.getTotalImages()==null?images.size():images.size()+response.getTotalImages());
            DetailVO detail = new DetailVO();
            detail.setPageTitle(document.title());  
            detail.setPageLink(url.length()>255?url.substring(0, 255):url);
            detail.setImageCount(Long.valueOf(images.size()));
        	response.getDetails().add(detail);
            for (Element link : linksOnPage) {
            	// Recursively calling the method as per the given depth
            	if(!StringUtils.isEmpty(link.attr("abs:href"))) {
                	crawlURL(link.attr("abs:href"),response,depth-1);
            	}
            }
        } catch (IOException e) {
            log.error("For '" + url + "': " + e.getMessage());
            throw new Exception("Invalid URL: "+url);
        }     
	}

	@Override
	public String saveRequest(RequestVO requestVO) {
		
		if (!links.contains(requestVO.getUrl())) {
			if (links.add(requestVO.getUrl())) {
				log.debug("Adding the URL "+requestVO.getUrl()+" in DB for processing");
            }
			Request request = new Request();		
			request.setUrl(requestVO.getUrl());
			request.setDepth(requestVO.getDepth());
			request.setStatus(Status.SUBMITTED.getEnumValue());
			String token = "HTTP-"+String.valueOf(Math.random());
			request.setAckToken(token);
			requestRepository.save(request);
			log.debug("Processing completed :"+requestVO.getUrl());
			return TOKEN_MESSAGE+token;
		}else {
			log.debug("Given URL is already submitted");
			Request request = requestRepository.findByUrl(requestVO.getUrl());
			return ERROR_MESSAGE+request.getAckToken();
		}
		
	}

	@Override
	public String checkStatus(String token) {
		log.debug("Getting status of token: "+token);
		Request request = requestRepository.findByAckToken(token);
		String status = Arrays.stream(Status.values()).filter(a->a.getEnumValue().equals(request.getStatus())).findAny().orElse(null).getEnumDescr();
		return "The Status of token no: "+token+" is "+status;
	}

	@Override
	public RequestVO getJsonResponse(String token) {
		log.debug("Getting response of token: "+token);
		Request request = requestRepository.findByAckToken(token);
		return requestMapper.convertRequestToRequestVO(request);
	}
	
	@PostConstruct
	public void initializeSet() {
		requestRepository.findAll().forEach(request->links.add(request.getUrl()));;
	}
}
