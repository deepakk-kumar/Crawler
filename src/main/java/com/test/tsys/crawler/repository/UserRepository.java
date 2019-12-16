package com.test.tsys.crawler.repository;

import org.springframework.data.repository.CrudRepository;

import com.test.tsys.crawler.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
}
