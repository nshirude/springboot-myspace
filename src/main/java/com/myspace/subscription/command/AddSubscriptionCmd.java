package com.myspace.subscription.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.myspace.subscription.model.Subscription;
import com.myspace.subscription.exception.SubscriptionException;
import com.myspace.subscription.repository.SubscriptionRepository;
import com.myspace.subscription.util.ErrorCode;

@Component
public class AddSubscriptionCmd {
	
	private Logger logger = LoggerFactory.getLogger(AddSubscriptionCmd.class);
	public static String CLASS_NAME = AddSubscriptionCmd.class.getName();
	
	@Autowired
	private SubscriptionRepository repository;

	
	/**
	 * This is Hystrix command to save new subscription into data base as well as into cache
	 * @param subscription
	 * @return subscription
	 */
	@CachePut(cacheNames="subCache",value="subCache")
	@HystrixCommand(groupKey = "addSubscriptionHystrix", commandKey = "addSubscriptionHystrix", fallbackMethod = "addSubscriptionFallback")
	public Subscription addSubscription(Subscription subscription) {
		logger.info("Inside addSubscription " , CLASS_NAME);
		return repository.save(subscription);
	}

	/**
	 * This is fallback method which get triggered once circuit breaker is enabled
	 * @param subscription
	 * @param e
	 * @return subscription
	 */
	@HystrixCommand(groupKey = "addSubscriptionHystrix")
	public Subscription addSubscriptionFallback(Subscription subscription, Throwable e) {
		throw new SubscriptionException(e,ErrorCode.SERVERL_ERROR);
	}


}
