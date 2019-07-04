package com.myspace.subscription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.model.Subscription;



@Mapper(componentModel="spring")
public interface SubscriptionReqMapper {
	SubscriptionReqMapper subscriptionReqMapper = Mappers.getMapper(SubscriptionReqMapper.class);
	@Mappings({
		@Mapping(target = "name", source = "name"),
		@Mapping(target = "monthlyPrice", source = "monthlyPrice")
	})
	
	Subscription mapSubscriptionRequestToEntity(CreateSubscriptionRequest createSubscriptionRequest);

}

