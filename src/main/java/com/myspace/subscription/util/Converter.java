package com.myspace.subscription.util;

import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.model.Subscription;

public class Converter {

	public static Subscription mapSubscriptionReqToEntity(CreateSubscriptionRequest req){
		
		Subscription sub = new Subscription();
		sub.setMonthlyPrice(req.getMonthlyPrice());
		sub.setName(req.getName());
		return sub;
	} 
}
