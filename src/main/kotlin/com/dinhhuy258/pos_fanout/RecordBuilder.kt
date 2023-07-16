package com.dinhhuy258.pos_fanout

import com.dinhhuy258.pos_fanout.types.HadoopRecord
import com.dinhhuy258.pos_fanout.types.Notification
import com.dinhhuy258.pos_fanout.types.PosInvoice

object RecordBuilder {
    fun getHadoopRecords(invoice: PosInvoice): List<HadoopRecord> {
        val records = mutableListOf<HadoopRecord>()
        for (i in invoice.invoiceLineItems) {
            val record = HadoopRecord().apply {
                invoiceNumber = invoice.invoiceNumber
                createdTime = invoice.createdTime
                storeID = invoice.storeID
                posID = invoice.posID
                customerType = invoice.customerType
                paymentMethod = invoice.paymentMethod
                deliveryType = invoice.deliveryType
                itemCode = i.itemCode
                itemDescription = i.itemDescription
                itemPrice = i.itemPrice
                itemQty = i.itemQty
                totalValue = i.totalValue
                if (invoice.deliveryType.equals("HOME-DELIVERY", true)) {
                    city = invoice.deliveryAddress!!.city
                    state = invoice.deliveryAddress!!.state
                    pinCode = invoice.deliveryAddress!!.pinCode
                }
            }
            records.add(record)
        }

        return records
    }

    fun getMaskedInvoice(invoice: PosInvoice): PosInvoice {
        invoice.customerCardNo = null
        if (invoice.deliveryType.equals("HOME-DELIVERY", true)) {
            invoice.deliveryAddress!!.addressLine = null
            invoice.deliveryAddress!!.contactNumber = null
        }

        return invoice
    }

    fun getNotification(invoice: PosInvoice): Notification {
        return Notification().apply {
            invoiceNumber = invoice.invoiceNumber
            customerCardNo = invoice.customerCardNo
            totalAmount = invoice.totalAmount
            earnedLoyaltyPoints = invoice.totalAmount?.times(0.02)
        }
    }
}
