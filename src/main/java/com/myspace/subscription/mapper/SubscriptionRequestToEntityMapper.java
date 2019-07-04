package com.myspace.subscription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.model.Subscription;



@Mapper
public interface SubscriptionRequestToEntityMapper {
	
	@Mappings({
		@Mapping(target = "name", source = "name"),
		@Mapping(target = "monthlyPrice", source = "monthlyPrice")		
	})
	
	Subscription mapSubscriptionRequestToEntity(CreateSubscriptionRequest createSubscriptionRequest);

}

