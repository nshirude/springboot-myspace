package com.myspace.subscription.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myspace.subscription.command.AddSubscriptionCmd;
import com.myspace.subscription.command.FindAllSubscriptionCmd;
import com.myspace.subscription.command.FindSubscriptionCmd;
import com.myspace.subscription.dto.addsubscription.CreateSubscriptionRequest;
import com.myspace.subscription.dto.fetchSubscription.GetSubscriptionResponse;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionRequest;
import com.myspace.subscription.dto.updateSubscription.UpdateSubscriptionResponse;
import com.myspace.subscription.model.Subscription;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {

	@InjectMocks
	private SubscriptionServiceImpl subscriptionService;
	@Mock
	FindSubscriptionCmd findSubscriptionCommand;
	@Mock
	FindAllSubscriptionCmd findAllSubscriptionCommand;
	@Mock
	AddSubscriptionCmd addSubscriptionCommand;


	private CreateSubscriptionRequest createSubscriptionRequest;

	private UpdateSubscriptionResponse updateSubscriptionResponse;

	private UpdateSubscriptionRequest updateSubRequest;

	private Subscription subscription;

	private List<Subscription> subscriptionList;

	@Before
	public void setUp() {
		this.subscription = new Subscription();
		this.subscription.setId(1001);
		this.subscription.setMonthlyPrice(112.50);
		this.subscription.setLastUpdate(new Date(0));
		this.subscription.setName("Custormer1");

		this.createSubscriptionRequest = new CreateSubscriptionRequest();
		this.createSubscriptionRequest.setMonthlyPrice(112.50);
		this.createSubscriptionRequest.setName("Custormer1");

		this.updateSubRequest = new UpdateSubscriptionRequest();
		this.updateSubRequest.setMonthlyPrice(102.22);
		this.updateSubRequest.setName("Custormer1");

		this.subscriptionList = new ArrayList<>();
		this.subscriptionList.add(subscription);

		this.updateSubscriptionResponse = new UpdateSubscriptionResponse();
		this.updateSubscriptionResponse.setStatus("SUCCESS");
	}

	@Test
	public void testGetSubscriptionById() {
		Mockito.when(this.findSubscriptionCommand.findSubscriptionById(this.subscription.getId()))
				.thenReturn(Optional.ofNullable(this.subscription));
		GetSubscriptionResponse getSubscriptionResponse = subscriptionService.findSubscriptionById(11);
		assertNotNull(getSubscriptionResponse);
	}

	@Test
	public void testGetSubscription() {
		Mockito.when(this.findAllSubscriptionCommand.findAllSubscription()).thenReturn(this.subscriptionList);
		GetSubscriptionResponse getSubscriptionsResponse = subscriptionService.retrieveAllSubscription();
		assertNotNull(getSubscriptionsResponse);
	}

	@Test
	public void testGetSubscriptionSuccess() {
		Mockito.when(this.findAllSubscriptionCommand.findAllSubscription()).thenReturn(this.subscriptionList);
		GetSubscriptionResponse getSubscriptionsResponse = subscriptionService.retrieveAllSubscription();
		assertEquals(1, getSubscriptionsResponse.getSubscription().size());
	}

	@Test
	public void testFindSubscriptionFailure() {
		Mockito.when(this.findAllSubscriptionCommand.findAllSubscription()).thenReturn(this.subscriptionList);
		GetSubscriptionResponse getSubscriptionsResponse = subscriptionService.retrieveAllSubscription();
		assertNotEquals("13", getSubscriptionsResponse.getSubscription().size());
	}

	@Test
	public void testUpdateSubscription() {

		Mockito.when(this.findSubscriptionCommand.findSubscriptionById(this.subscription.getId()))
				.thenReturn(Optional.ofNullable(this.subscription));

		Mockito.when(this.addSubscriptionCommand.addSubscription(this.subscription)).thenReturn(this.subscription);
		UpdateSubscriptionResponse updateSubscriptionResponse = subscriptionService
				.updateSubscription(this.updateSubRequest, this.subscription.getId());
		assertNotNull(updateSubscriptionResponse);
	}

	@Test
	public void testUpdateSubscriptionSuccess() {

		Mockito.when(this.findSubscriptionCommand.findSubscriptionById(this.subscription.getId()))
				.thenReturn(Optional.ofNullable(this.subscription));

		Mockito.when(this.addSubscriptionCommand.addSubscription(this.subscription)).thenReturn(this.subscription);
		UpdateSubscriptionResponse updateSubscriptionResponse = subscriptionService
				.updateSubscription(this.updateSubRequest, this.subscription.getId());
		assertEquals("SUCCESS", updateSubscriptionResponse.getStatus());
	}

	@Test
	public void testUpdateSubscriptionFailure() {

		Mockito.when(this.findSubscriptionCommand.findSubscriptionById(this.subscription.getId()))
				.thenReturn(Optional.ofNullable(this.subscription));

		Mockito.when(this.addSubscriptionCommand.addSubscription(this.subscription)).thenReturn(this.subscription);
		UpdateSubscriptionResponse updateSubscriptionResponse = subscriptionService
				.updateSubscription(this.updateSubRequest, this.subscription.getId());
		assertNotEquals("SUCCESSES", updateSubscriptionResponse.getStatus());
	}

}
