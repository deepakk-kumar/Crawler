package com.test.tsys.crawler.mapper;

import org.mapstruct.Mapper;

import com.test.tsys.crawler.domain.Response;
import com.test.tsys.crawler.models.ResponseVO;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

	ResponseVO convertResponseToResponseVO(Response response);
	Response convertResponseVOToResponse(ResponseVO responseVo);

}
