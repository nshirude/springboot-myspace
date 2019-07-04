
package com.myspace.subscription.dto.updateSubscription;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
    "name",
    "monthlyPrice",
    "lastUpdate"
})
public class UpdateSubscriptionRequest {

 
    @NotNull
    @JsonProperty("name")
    @ApiModelProperty(required = true, dataType = "string")
    private String name;
    @JsonProperty("monthlyPrice")
    @ApiModelProperty(required = false, dataType = "number")
    private Double monthlyPrice;
    

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public UpdateSubscriptionRequest withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("monthlyPrice")
    public Double getMonthlyPrice() {
        return monthlyPrice;
    }

    @JsonProperty("monthlyPrice")
    public void setMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public UpdateSubscriptionRequest withMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(monthlyPrice).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UpdateSubscriptionRequest) == false) {
            return false;
        }
        UpdateSubscriptionRequest updateSub = ((UpdateSubscriptionRequest) other);
        return new EqualsBuilder().append(name, updateSub.name).append(monthlyPrice, updateSub.monthlyPrice).isEquals();
    }

}
