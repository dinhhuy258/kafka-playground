package com.dinhhuy258.pos_simulator.types

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.apache.commons.lang.builder.ToStringBuilder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "ItemCode",
    "ItemDescription",
    "ItemPrice",
    "ItemQty",
    "TotalValue"
)
class LineItem {
    @JsonProperty("ItemCode")
    var itemCode: String? = null

    @JsonProperty("ItemDescription")
    var itemDescription: String? = null

    @JsonProperty("ItemPrice")
    var itemPrice: Double? = null

    @JsonProperty("ItemQty")
    var itemQty: Int? = null

    @JsonProperty("TotalValue")
    var totalValue: Double? = null

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("itemCode", itemCode)
            .append("itemDescription", itemDescription)
            .append("itemPrice", itemPrice)
            .append("itemQty", itemQty)
            .append("totalValue", totalValue)
            .toString()
    }
}