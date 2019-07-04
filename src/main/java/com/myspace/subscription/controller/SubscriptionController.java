package com.myspace.subscription.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.dto.addsubscription.CreateSubscriptionResponse;
import com.myspace.subscription.dto.fetchSubscription.GetSubscriptionResponse;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionRequest;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionResponse;
import com.myspace.subscription.service.SubscriptionService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "${api.subscriptionservice.basepath}")
@Validated
@RefreshScope
@ApiResponses(value = { @ApiResponse(code = 400, message = "requires user authentication"),
		@ApiResponse(code = 403, message = "forbidden error in accessing resource"),
		@ApiResponse(code = 404, message = "Request-URI path not found") })
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;
	

	/**
	 * This is GET operation retrieve all available subscription
	 * 
	 * @param traceId
	 * @return GetSubscriptionResponse
	 */
	@ApiOperation(value = "REST API to get All Subscriptions", response = GetSubscriptionResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "traceId", value = "traceId", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "{\"status\":\"FAILURE\",  \"errors\": [    {\"code\": \"SERVER_ERROR\",\"message\": \"Unable to process request.\"}]", response = GetSubscriptionResponse.class),
			@ApiResponse(code = 200, message = "OK", response = GetSubscriptionResponse.class) })
	@GetMapping(value = "${api.subscriptionservice.getall.subscriptions.GET.uri}")
	public ResponseEntity<GetSubscriptionResponse> retrieveAllSubscriptions(
			@RequestHeader(value = "trackingId", required = true) String trackingId) {

		return new ResponseEntity<>(subscriptionService.retrieveAllSubscription(), HttpStatus.OK);
	}

	/**
	 * This is GET operation retrieve specific subscription
	 * 
	 * @param traceId
	 * @param id
	 * @return GetSubscriptionResponse
	 */
	@ApiOperation(value = "REST API to get Subscription based on subscription Id ", response = GetSubscriptionResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "traceId", value = "traceId", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "{\"status\":\"FAILURE\",  \"errors\": [    {\"code\": \"SERVER_ERROR\",\"message\": \"Unable to process request.\"}]", response = GetSubscriptionResponse.class),
			@ApiResponse(code = 200, message = "OK", response = GetSubscriptionResponse.class) })
	@GetMapping(value = "${api.subscriptionservice.get.subscription.GET.uri}")
	public ResponseEntity<GetSubscriptionResponse> retrieveSubscriptionById(@PathVariable Integer id) {
		return new ResponseEntity<>(subscriptionService.findSubscriptionById(id), HttpStatus.OK);
	}
	
	/**
	 * This is POST operation save the new subscription into system
	 * 
	 * @param traceId
	 * @param subscription
	 * @return CreateSubscriptionResponse
	 */
	@ApiOperation(value = "REST API to Persist the Subscription", response = CreateSubscriptionResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "traceId", value = "traceId", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "{\"status\":\"FAILURE\",  \"errors\": [    {\"code\": \"SERVER_ERROR\",\"message\": \"Unable to process request.\"}]", response = CreateSubscriptionResponse.class),
			@ApiResponse(code = 200, message = "OK", response = CreateSubscriptionResponse.class) })
	@PostMapping(value = "${api.subscriptionservice.create.subscription.POST.uri}")
	public ResponseEntity<CreateSubscriptionResponse> addSubscription(
			@RequestHeader(value = "traceId", required = true) String traceId,
			@Valid @NotNull @RequestBody CreateSubscriptionRequest subscription) {

		return new ResponseEntity<>(subscriptionService.saveSubscription(subscription), HttpStatus.OK);
	}

	/**
	 * This is PUT operation update specific subscription
	 * 
	 * @param updateSubscriptionRequest
	 * @param id
	 * @param traceId
	 * @return
	 */
	@ApiOperation(value = "REST API to update Subscription based on subscription Id ", response = UpdateSubscriptionResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "traceId", value = "traceId", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Authorization", value = "Authorization", required = true, dataType = "string", paramType = "header") })
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "{\"status\":\"FAILURE\",  \"errors\": [    {\"code\": \"SERVER_ERROR\",\"message\": \"Unable to process request.\"}]", response = UpdateSubscriptionResponse.class),
			@ApiResponse(code = 200, message = "OK", response = UpdateSubscriptionResponse.class) })
	@PutMapping(value = "${api.subscriptionservice.update.subscription.PUT.uri}")
	public ResponseEntity<UpdateSubscriptionResponse> updateSubscriptionById(
			@RequestBody UpdateSubscriptionRequest updateSubscriptionRequest, @PathVariable Integer id) {
		return new ResponseEntity<>(subscriptionService.updateSubscription(updateSubscriptionRequest, id),
				HttpStatus.OK);
	}

}
