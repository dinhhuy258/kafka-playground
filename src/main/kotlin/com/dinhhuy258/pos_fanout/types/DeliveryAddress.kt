package com.dinhhuy258.pos_fanout.types

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.apache.commons.lang.builder.ToStringBuilder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "AddressLine",
    "City",
    "State",
    "PinCode",
    "ContactNumber"
)
class DeliveryAddress {
    @JsonProperty("AddressLine")
    var addressLine: String? = null

    @JsonProperty("City")
    var city: String? = null

    @JsonProperty("State")
    var state: String? = null

    @JsonProperty("PinCode")
    var pinCode: String? = null

    @JsonProperty("ContactNumber")
    var contactNumber: String? = null

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("addressLine", addressLine)
            .append("city", city)
            .append("state", state)
            .append("pinCode", pinCode)
            .append("contactNumber", contactNumber)
            .toString()
    }
}