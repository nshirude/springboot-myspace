package com.myspace.subscription.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.myspace.subscription.exception.SubscriptionException;
import com.myspace.subscription.model.Subscription;
import com.myspace.subscription.repository.SubscriptionRepository;
import com.myspace.subscription.util.ErrorCode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class FindAllSubscriptionCmd {
	
	private Logger logger = LoggerFactory.getLogger(FindAllSubscriptionCmd.class);
	public static String CLASS_NAME = FindAllSubscriptionCmd.class.getName();
	
	@Autowired
	private SubscriptionRepository repository;
	
	/**
	 * This is Hystrix command to find all available subscription in cache
	 * @return Subscription
	 */
	@Cacheable(cacheNames="subCache",value="subCache",keyGenerator="KeyGen")
	@HystrixCommand(groupKey = "getAllSubHystrix", commandKey = "getAllSubHystrix", fallbackMethod = "getAllSubFallback")
	public List<Subscription> findAllSubscription()  {
		logger.info("Inside method findAllSubscription " , CLASS_NAME);
		return repository.findAll();
	}

	/**
	 * This is fallback method which triggered once circuit breaker is enabled 	 
	 */
	@HystrixCommand(groupKey = "getAllSubHystrix")
	public List<Subscription> getAllSubFallback(Throwable e) {
		throw new SubscriptionException(e,ErrorCode.SERVERL_ERROR);
	}

}
