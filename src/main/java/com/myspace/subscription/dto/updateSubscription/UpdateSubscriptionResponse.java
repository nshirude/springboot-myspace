
package com.myspace.subscription.dto.updateSubscription;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
    "status",
    "errors"
})
public class UpdateSubscriptionResponse {

    @JsonProperty("status")
    @ApiModelProperty(required = false, dataType = "string")
    private String status;
    @JsonProperty("errors")
    @ApiModelProperty(required = false, dataType = "array")
    @Valid
    private List<SubscriptionError> errors = new ArrayList<SubscriptionError>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateSubscriptionResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("errors")
    public List<SubscriptionError> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<SubscriptionError> errors) {
        this.errors = errors;
    }

    public UpdateSubscriptionResponse withErrors(List<SubscriptionError> errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(status).append(errors).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UpdateSubscriptionResponse) == false) {
            return false;
        }
        UpdateSubscriptionResponse response = ((UpdateSubscriptionResponse) other);
        return new EqualsBuilder().append(status, response.status).append(errors, response.errors).isEquals();
    }

}
