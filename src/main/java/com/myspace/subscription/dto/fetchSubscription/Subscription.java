
package com.myspace.subscription.dto.fetchSubscription;

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
    "id",
    "name",
    "monthlyPrice",
    "lastUpdate"
})
public class Subscription {

    @NotNull
    @JsonProperty("id")
    @ApiModelProperty(required = true, dataType = "integer")
    private Integer id;

    @NotNull
    @JsonProperty("name")
    @ApiModelProperty(required = true, dataType = "string")
    private String name;
    @JsonProperty("monthlyPrice")
    @ApiModelProperty(required = false, dataType = "number")
    private Double monthlyPrice;
    @JsonProperty("lastUpdate")
    @ApiModelProperty(required = false, dataType = "string")
    private String lastUpdate;

    
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Subscription withId(Integer id) {
        this.id = id;
        return this;
    }

  
    @JsonProperty("name")
    public String getName() {
        return name;
    }

  
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Subscription withName(String name) {
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

    public Subscription withMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
        return this;
    }

    @JsonProperty("lastUpdate")
    public String getLastUpdate() {
        return lastUpdate;
    }

    @JsonProperty("lastUpdate")
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Subscription withLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(monthlyPrice).append(lastUpdate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Subscription) == false) {
            return false;
        }
        Subscription sub = ((Subscription) other);
        return new EqualsBuilder().append(id, sub.id).append(name, sub.name).append(monthlyPrice, sub.monthlyPrice).append(lastUpdate, sub.lastUpdate).isEquals();
    }

}
