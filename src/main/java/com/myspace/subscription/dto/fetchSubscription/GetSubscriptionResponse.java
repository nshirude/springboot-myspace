
package com.myspace.subscription.dto.fetchSubscription;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.myspace.subscription.dto.SubscriptionError;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
    "Subscription"
})
public class GetSubscriptionResponse {
	
    @JsonProperty("status")
    @ApiModelProperty(required = false, dataType = "string")
    private String status;
    
    @JsonProperty("errors")
    @ApiModelProperty(required = false, dataType = "array")
    @Valid
    private List<SubscriptionError> errors = new ArrayList<SubscriptionError>();

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SubscriptionError> getErrors() {
		return errors;
	}

	public void setErrors(List<SubscriptionError> errors) {
		this.errors = errors;
	}

	
    @NotNull
    @JsonProperty("Subscription")
    @ApiModelProperty(required = true, dataType = "array")
    @Valid
    private List<Subscription> subscription = new ArrayList<Subscription>();

   
    @JsonProperty("Subscription")
    public List<Subscription> getSubscription() {
        return subscription;
    }

   
    @JsonProperty("Subscription")
    public void setSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
    }

    public GetSubscriptionResponse withSubscription(List<Subscription> subscription) {
        this.subscription = subscription;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(subscription).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetSubscriptionResponse) == false) {
            return false;
        }
        GetSubscriptionResponse rhs = ((GetSubscriptionResponse) other);
        return new EqualsBuilder().append(subscription, rhs.subscription).isEquals();
    }

}
