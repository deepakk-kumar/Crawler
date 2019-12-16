package com.test.tsys.crawler.mapper;

import org.mapstruct.Mapper;

import com.test.tsys.crawler.domain.Request;
import com.test.tsys.crawler.models.RequestVO;

@Mapper(componentModel = "spring")
public interface RequestMapper {

	RequestVO convertRequestToRequestVO(Request request);
	Request convertRequestVOToRequest(RequestVO requestVO);
	
}
