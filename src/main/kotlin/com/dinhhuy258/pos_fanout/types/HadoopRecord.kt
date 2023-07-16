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
    "CustomerType",
    "PaymentMethod",
    "DeliveryType",
    "City",
    "State",
    "PinCode",
    "ItemCode",
    "ItemDescription",
    "ItemPrice",
    "ItemQty",
    "TotalValue"
)
data class HadoopRecord(
    @JsonProperty("InvoiceNumber")
    var invoiceNumber: String? = null,
    @JsonProperty("CreatedTime")
    var createdTime: Long? = null,
    @JsonProperty("StoreID")
    var storeID: String? = null,
    @JsonProperty("PosID")
    var posID: String? = null,
    @JsonProperty("CustomerType")
    var customerType: String? = null,
    @JsonProperty("PaymentMethod")
    var paymentMethod: String? = null,
    @JsonProperty("DeliveryType")
    var deliveryType: String? = null,
    @JsonProperty("City")
    var city: String? = null,
    @JsonProperty("State")
    var state: String? = null,
    @JsonProperty("PinCode")
    var pinCode: String? = null,
    @JsonProperty("ItemCode")
    var itemCode: String? = null,
    @JsonProperty("ItemDescription")
    var itemDescription: String? = null,
    @JsonProperty("ItemPrice")
    var itemPrice: Double? = null,
    @JsonProperty("ItemQty")
    var itemQty: Int? = null,
    @JsonProperty("TotalValue")
    var totalValue: Double? = null
) {
    @JsonProperty("InvoiceNumber")
    fun withInvoiceNumber(invoiceNumber: String?): HadoopRecord {
        this.invoiceNumber = invoiceNumber
        return this
    }

    @JsonProperty("CreatedTime")
    fun withCreatedTime(createdTime: Long?): HadoopRecord {
        this.createdTime = createdTime
        return this
    }

    @JsonProperty("StoreID")
    fun withStoreID(storeID: String?): HadoopRecord {
        this.storeID = storeID
        return this
    }

    @JsonProperty("PosID")
    fun withPosID(posID: String?): HadoopRecord {
        this.posID = posID
        return this
    }

    @JsonProperty("CustomerType")
    fun withCustomerType(customerType: String?): HadoopRecord {
        this.customerType = customerType
        return this
    }

    @JsonProperty("PaymentMethod")
    fun withPaymentMethod(paymentMethod: String?): HadoopRecord {
        this.paymentMethod = paymentMethod
        return this
    }

    @JsonProperty("DeliveryType")
    fun withDeliveryType(deliveryType: String?): HadoopRecord {
        this.deliveryType = deliveryType
        return this
    }

    @JsonProperty("City")
    fun withCity(city: String?): HadoopRecord {
        this.city = city
        return this
    }

    @JsonProperty("State")
    fun withState(state: String?): HadoopRecord {
        this.state = state
        return this
    }

    @JsonProperty("PinCode")
    fun withPinCode(pinCode: String?): HadoopRecord {
        this.pinCode = pinCode
        return this
    }

    @JsonProperty("ItemCode")
    fun withItemCode(itemCode: String?): HadoopRecord {
        this.itemCode = itemCode
        return this
    }

    @JsonProperty("ItemDescription")
    fun withItemDescription(itemDescription: String?): HadoopRecord {
        this.itemDescription = itemDescription
        return this
    }

    @JsonProperty("ItemPrice")
    fun withItemPrice(itemPrice: Double?): HadoopRecord {
        this.itemPrice = itemPrice
        return this
    }

    @JsonProperty("ItemQty")
    fun withItemQty(itemQty: Int?): HadoopRecord {
        this.itemQty = itemQty
        return this
    }

    @JsonProperty("TotalValue")
    fun withTotalValue(totalValue: Double?): HadoopRecord {
        this.totalValue = totalValue
        return this
    }

    override fun toString(): String {
        return ToStringBuilder(this)
            .append("invoiceNumber", invoiceNumber)
            .append("createdTime", createdTime)
            .append("storeID", storeID)
            .append("posID", posID)
            .append("customerType", customerType)
            .append("paymentMethod", paymentMethod)
            .append("deliveryType", deliveryType)
            .append("city", city)
            .append("state", state)
            .append("pinCode", pinCode)
            .append("itemCode", itemCode)
            .append("itemDescription", itemDescription)
            .append("itemPrice", itemPrice)
            .append("itemQty", itemQty)
            .append("totalValue", totalValue)
            .toString()
    }
}