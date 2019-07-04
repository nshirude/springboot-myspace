package com.myspace.subscription.service;

import org.springframework.stereotype.Service;

import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.dto.addsubscription.CreateSubscriptionResponse;
import com.myspace.subscription.dto.fetchSubscription.GetSubscriptionResponse;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionRequest;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionResponse;

@Service
public interface SubscriptionService {
	
	GetSubscriptionResponse retrieveAllSubscription();

	GetSubscriptionResponse findSubscriptionById(int subscriptionId);
	
	CreateSubscriptionResponse saveSubscription(CreateSubscriptionRequest subscriptionReq);

	UpdateSubscriptionResponse updateSubscription(UpdateSubscriptionRequest updateSubRequest, Integer id);

}
