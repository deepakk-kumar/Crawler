package com.test.tsys.crawler.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.test.tsys.crawler.domain.Request;

public interface IRequestRepository extends PagingAndSortingRepository<Request, Long>{

	Request findByAckToken(String ackToken);
	Request findByUrl(String url);
	Page<Request> findAllByStatus(String status,Pageable pageable);
	Request findByUrlAndStatus(String url,String status);
	List<Request> findAll();
}
