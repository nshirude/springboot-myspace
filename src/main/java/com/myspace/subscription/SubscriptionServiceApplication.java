package com.myspace.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

import com.myspace.subscription.service.SubscriptionService;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableCaching
@RefreshScope
@PropertySource("classpath:application.properties")
public class SubscriptionServiceApplication {

	@Autowired
	private SubscriptionService subscriptionService;
	
	public static void main(String[] args) {
		SpringApplication.run(SubscriptionServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void populateCacheData() {
		subscriptionService.retrieveAllSubscription();
	}
}
