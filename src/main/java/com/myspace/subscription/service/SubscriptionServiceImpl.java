package com.myspace.subscription.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myspace.subscription.command.AddSubscriptionCmd;
import com.myspace.subscription.command.FindAllSubscriptionCmd;
import com.myspace.subscription.command.FindSubscriptionCmd;
import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.dto.addsubscription.CreateSubscriptionResponse;
import com.myspace.subscription.dto.fetchSubscription.GetSubscriptionResponse;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionRequest;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionResponse;
import com.myspace.subscription.exception.SubscriptionException;
import com.myspace.subscription.exception.SubscriptionNotFoundException;
import com.myspace.subscription.mapper.SubscriptionReqMapper;
import com.myspace.subscription.model.Subscription;
import com.myspace.subscription.util.Constants;
import com.myspace.subscription.util.ErrorCode;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	private Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	
	public static String CLASS_NAME = SubscriptionServiceImpl.class.getName();
	
	@Autowired
	FindSubscriptionCmd findSubscriptionCommand;

	@Autowired
	AddSubscriptionCmd addSubscriptionCommand;

	@Autowired
	FindAllSubscriptionCmd findAllSubscriptionCommand;

	
	
	/**
	 * This method retrieve all available subscription
	 * 
	 */
	
	@Override
	public GetSubscriptionResponse retrieveAllSubscription() {
		logger.info("Inside Method retrieveAllSubscription ", CLASS_NAME);
		try {
			GetSubscriptionResponse response = new GetSubscriptionResponse();

			List<Subscription> subscriptions = findAllSubscriptionCommand.findAllSubscription();
			List<com.myspace.subscription.dto.fetchSubscription.Subscription> subRequests = new ArrayList<>();
			if (subscriptions != null && !subscriptions.isEmpty()) {
				subscriptions.forEach(subscription -> {
					com.myspace.subscription.dto.fetchSubscription.Subscription newSubscription = new com.myspace.subscription.dto.fetchSubscription.Subscription();
					SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
					if (subscription.getLastUpdate() != null) {
						newSubscription.setLastUpdate(dateFormat.format(subscription.getLastUpdate()));
					}
					newSubscription.setId(subscription.getId());					
					newSubscription.setName(subscription.getName());
					newSubscription.setMonthlyPrice(subscription.getMonthlyPrice());
					subRequests.add(newSubscription);
				});
				response.setSubscription(subRequests);
				response.setStatus(Constants.SUCCESS_MESSAGE);
				logger.info("Method retrieveAllSubscription execution complete ", CLASS_NAME);
			} else {
				response.setStatus(Constants.RESOURCE_NOT_FOUND);
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception occured while geting all subscriptions " + e.getMessage());
			throw new SubscriptionException(e, ErrorCode.SERVERL_ERROR);
		}

	}

	/**
	 * This method return specific subscription using id
	 * 
	 * @param int
	 *            subscriptionId
	 * @return ResponseEntity<GetSubscriptionResponse>
	 */
	@Override
	public GetSubscriptionResponse findSubscriptionById(int subscriptionId) {
		logger.info("Inside Method findSubscriptionById ", CLASS_NAME);

		try {
			Optional<Subscription> subscriptionOpt = findSubscriptionCommand.findSubscriptionById(subscriptionId);
			GetSubscriptionResponse response = new GetSubscriptionResponse();
			if (subscriptionOpt.isPresent()) {
				Subscription subscription = subscriptionOpt.get();
				com.myspace.subscription.dto.fetchSubscription.Subscription newSubscription = new com.myspace.subscription.dto.fetchSubscription.Subscription();
				SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
				if (subscription.getLastUpdate() != null) {
					newSubscription.setLastUpdate(dateFormat.format(subscription.getLastUpdate()));
				}
				newSubscription.setId(subscription.getId());			
				newSubscription.setName(subscription.getName());
				newSubscription.setMonthlyPrice(subscription.getMonthlyPrice());
				List<com.myspace.subscription.dto.fetchSubscription.Subscription> subscriptionList = new ArrayList<>();
				subscriptionList.add(newSubscription);
				response.setStatus(Constants.SUCCESS_MESSAGE);
				response.setSubscription(subscriptionList);
				logger.info("Method findSubscriptionById execution complete ", CLASS_NAME);
			} else {
				response.setStatus(Constants.RESOURCE_NOT_FOUND);
				throw new SubscriptionNotFoundException("Subscription","ID",subscriptionId);
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception occured while geting specific subscriptions " + e.getMessage());
			throw new SubscriptionException(e, ErrorCode.SERVERL_ERROR);
		}
	}
	
	/**
	 * This Method is persist data in DB 
	 * @param CreateSubscriptionRequest
	 *            createSubscriptionRequest
	 * @return ResponseEntity<CreateSubscriptionResponse> *
	 */
	@Override
	public CreateSubscriptionResponse saveSubscription(CreateSubscriptionRequest createSubRequest) {
		logger.info("Inside Method SaveSubscription ", CLASS_NAME);
		try {
			
			Subscription subscription=SubscriptionReqMapper.subscriptionReqMapper.mapSubscriptionRequestToEntity(createSubRequest);
			subscription.setLastUpdate(new Date(System.currentTimeMillis()));
			subscription = addSubscriptionCommand.addSubscription(subscription);
			
			CreateSubscriptionResponse createSubscriptionResponse = new CreateSubscriptionResponse();
			createSubscriptionResponse.setStatus(Constants.SUCCESS_MESSAGE);
			logger.info("Subscription saved successfully with subscription id {}", subscription.getId(),CLASS_NAME);
			return createSubscriptionResponse;

		} catch (Exception e) {
			logger.error("Exception occured while persisting subscription " + e.getMessage());
			throw new SubscriptionException(e, ErrorCode.SERVERL_ERROR);
		}
	}

	/**
	 * This method update existing subscription
	 * 
	 * @param UpdateSubscriptionRequest
	 *            updateSubscriptionRequest
	 * @param Integer
	 *            subscriptionId
	 * @return ResponseEntity<UpdateSubscriptionResponse>
	 */
	@Override
	public UpdateSubscriptionResponse updateSubscription(
			UpdateSubscriptionRequest updateSubscriptionRequest, Integer subscriptionId) {
		logger.info("Inside Method updateSubscription ", CLASS_NAME);
		try {
			UpdateSubscriptionResponse response = new UpdateSubscriptionResponse();
			Optional<Subscription> subscriptionOptional = findSubscriptionCommand.findSubscriptionById(subscriptionId);

			if (subscriptionOptional.isPresent()) {
				Subscription subscription = subscriptionOptional.get();
				subscription.setMonthlyPrice(updateSubscriptionRequest.getMonthlyPrice());
				subscription.setName(updateSubscriptionRequest.getName());
				subscription = addSubscriptionCommand.addSubscription(subscription);
				response.setStatus(Constants.SUCCESS_MESSAGE);
				logger.info("Subscription Updated with Id {}", subscription.getId());
			} else {
				response.setStatus(Constants.RESOURCE_NOT_FOUND);
				throw new SubscriptionNotFoundException("Subscription","ID",subscriptionId);
				
			}
			
			logger.info("Method updateSubscription execution complete ", CLASS_NAME);
			return response;

		} catch (Exception e) {
			logger.error("Exception occured while updating subscription " + e.getMessage());
			throw new SubscriptionException(e, ErrorCode.SERVERL_ERROR);
		}
	}
}
