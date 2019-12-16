package com.test.tsys.crawler.util;

import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansUtil {

	@Bean(name="commonsPool2TargetSource")
	public CommonsPool2TargetSource getCommonsPool2TargetSource() {
		CommonsPool2TargetSource commonsPool2TargetSource = new CommonsPool2TargetSource();
		commonsPool2TargetSource.setTargetBeanName("dataProcessor");
		commonsPool2TargetSource.setMaxIdle(2400);
		commonsPool2TargetSource.setMinIdle(1200);
		commonsPool2TargetSource.setMaxSize(2400);
		return commonsPool2TargetSource;
	}
}
