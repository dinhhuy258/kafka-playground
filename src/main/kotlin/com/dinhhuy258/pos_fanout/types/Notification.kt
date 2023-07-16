package com.dinhhuy258.pos_fanout.types

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.apache.commons.lang.builder.ToStringBuilder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "InvoiceNumber",
    "CustomerCardNo",
    "TotalAmount",
    "EarnedLoyaltyPoints"
)
data class Notification(
    @JsonProperty("InvoiceNumber")
    var invoiceNumber: String? = null,
    @JsonProperty("CustomerCardNo")
    var customerCardNo: String? = null,
    @JsonProperty("TotalAmount")
    var totalAmount: Double? = null,
    @JsonProperty("EarnedLoyaltyPoints")
    var earnedLoyaltyPoints: Double? = null
) {
    @JsonProperty("InvoiceNumber")
    fun withInvoiceNumber(invoiceNumber: String?): Notification {
        this.invoiceNumber = invoiceNumber
        return this
    }

    @JsonProperty("CustomerCardNo")
    fun withCustomerCardNo(customerCardNo: String?): Notification {
        this.customerCardNo = customerCardNo
        return this
    }

    @JsonProperty("TotalAmount")
    fun withTotalAmount(totalAmount: Double?): Notification {
        this.totalAmount = totalAmount
        return this
    }

    @JsonProperty("EarnedLoyaltyPoints")
    fun withEarnedLoyaltyPoints(earnedLoyaltyPoints: Double?): Notification {
        this.earnedLoyaltyPoints = earnedLoyaltyPoints
        return this
    }

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("invoiceNumber", invoiceNumber)
            .append("customerCardNo", customerCardNo)
            .append("totalAmount", totalAmount)
            .append("earnedLoyaltyPoints", earnedLoyaltyPoints)
            .toString()
    }
}