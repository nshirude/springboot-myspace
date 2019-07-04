package com.myspace.subscription.command;

import java.util.Optional;

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
public class FindSubscriptionCmd {
	
	private Logger logger = LoggerFactory.getLogger(FindSubscriptionCmd.class);
	
	public static String CLASS_NAME = FindSubscriptionCmd.class.getName();

	
	@Autowired
	private SubscriptionRepository repository;
	
	
	/**
	 * This is Hystrix command to find subscription using subscription id from cache
	 * @param subscriptionId
	 * @return Subscription
	 */
	@Cacheable("subCache")
	@HystrixCommand(groupKey = "getSubByIdHystrix", commandKey = "getSubByIdHystrix", fallbackMethod = "getSubByIdHystrixFallback")
	public Optional<Subscription> findSubscriptionById(Integer subscriptionId) {
		logger.info("Inside method findSubscriptionById " , CLASS_NAME);
		return repository.findById(subscriptionId);
	}

	/**
	 * This is fallback method which triggered when circuit breaker is enabled
	 * @throws SubscriptionException
	 */
	@HystrixCommand(groupKey = "getSubByIdHystrix")
	public Optional<Subscription> getSubByIdHystrixFallback(Integer subscriptionId, Throwable e) {
		throw new SubscriptionException(e,ErrorCode.SERVERL_ERROR);
	}

}
