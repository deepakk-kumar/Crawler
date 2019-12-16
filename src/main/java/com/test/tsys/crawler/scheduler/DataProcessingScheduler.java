package com.test.tsys.crawler.scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.tsys.crawler.domain.Request;
import com.test.tsys.crawler.repository.IRequestRepository;
import com.test.tsys.crawler.util.Status;

@Component
public class DataProcessingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DataProcessingScheduler.class);
    private ThreadPoolExecutor executor;
    private BlockingQueue<Runnable> workQueue;
    
    @Autowired
    private IRequestRepository requestRepository;
    
    @Autowired
    @Qualifier("commonsPool2TargetSource")
    private CommonsPool2TargetSource commonsPool2TargetSource;
    
    @PostConstruct
    public void initialize() {
    	workQueue = new ArrayBlockingQueue<>(650);
    	executor= new ThreadPoolExecutor(5, 10, 1500, TimeUnit.MILLISECONDS, workQueue);
    }
    
    public BlockingQueue<Runnable> getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(BlockingQueue<Runnable> workQueue) {
		this.workQueue = workQueue;
	}

	@Scheduled(fixedRate=5000)
    public void processRecords() {
    	logger.debug("Fetching Records From Database with status S");
    	
    	if(executor.getQueue().remainingCapacity()>0) {
    		Pageable pageable = PageRequest.of(0, executor.getQueue().remainingCapacity(), Sort.by(Sort.Direction.ASC, "id"));
    		Page<Request> requests = requestRepository.findAllByStatus(Status.SUBMITTED.getEnumValue(),pageable);
        	for(Request request:requests) {
        		doProcessing(request);
        	}

    	}
    }
	
	private void doProcessing(Request request) {
		
		try {
			request.setStatus(Status.INPROGRESS.getEnumValue());
			requestRepository.save(request);
			DataProcessor dataProcessor=(DataProcessor)commonsPool2TargetSource.getTarget();			
			dataProcessor.setRequest(request);
			executor.submit(dataProcessor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
