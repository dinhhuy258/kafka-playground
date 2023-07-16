package com.dinhhuy258.pos_fanout.types

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.apache.commons.lang.builder.ToStringBuilder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "InvoiceNumber",
    "CreatedTime",
    "StoreID",
    "PosID",
    "CashierID",
    "CustomerType",
    "CustomerCardNo",
    "TotalAmount",
    "NumberOfItems",
    "PaymentMethod",
    "TaxableAmount",
    "CGST",
    "SGST",
    "CESS",
    "DeliveryType",
    "DeliveryAddress",
    "InvoiceLineItems"
)
class PosInvoice {
    @JsonProperty("InvoiceNumber")
    var invoiceNumber: String? = null

    @JsonProperty("CreatedTime")
    var createdTime: Long? = null

    @JsonProperty("StoreID")
    var storeID: String? = null

    @JsonProperty("PosID")
    var posID: String? = null

    @JsonProperty("CashierID")
    var cashierID: String? = null

    @JsonProperty("CustomerType")
    var customerType: String? = null

    @JsonProperty("CustomerCardNo")
    var customerCardNo: String? = null

    @JsonProperty("TotalAmount")
    var totalAmount: Double? = null

    @JsonProperty("NumberOfItems")
    var numberOfItems: Int? = null

    @JsonProperty("PaymentMethod")
    var paymentMethod: String? = null

    @JsonProperty("TaxableAmount")
    var taxableAmount: Double? = null

    @JsonProperty("CGST")
    var cGST: Double? = null

    @JsonProperty("SGST")
    var sGST: Double? = null

    @JsonProperty("CESS")
    var cESS: Double? = null

    @JsonProperty("DeliveryType")
    var deliveryType: String? = null

    @JsonProperty("DeliveryAddress")
    var deliveryAddress: DeliveryAddress? = null

    @JsonProperty("InvoiceLineItems")
    var invoiceLineItems: List<LineItem> = ArrayList()

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("invoiceNumber", invoiceNumber)
            .append("createdTime", createdTime)
            .append("storeID", storeID)
            .append("posID", posID)
            .append("cashierID", cashierID)
            .append("customerType", customerType)
            .append("customerCardNo", customerCardNo)
            .append("totalAmount", totalAmount)
            .append("numberOfItems", numberOfItems)
            .append("paymentMethod", paymentMethod)
            .append("taxableAmount", taxableAmount)
            .append("cGST", cGST)
            .append("sGST", sGST)
            .append("cESS", cESS)
            .append("deliveryType", deliveryType)
            .append("deliveryAddress", deliveryAddress)
            .append("invoiceLineItems", invoiceLineItems)
            .toString()
    }
}