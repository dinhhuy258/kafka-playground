package com.dinhhuy258.pos_simulator.datagenerator

import com.dinhhuy258.pos_simulator.types.LineItem
import com.dinhhuy258.pos_simulator.types.PosInvoice
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.util.*

class InvoiceGenerator private constructor() {
    private val invoiceIndex: Random
    private val invoiceNumber: Random
    private val numberOfItems: Random
    private val invoices: Array<PosInvoice>

    companion object {
        private val logger: Logger = LogManager.getLogger()
        private val ourInstance = InvoiceGenerator()

        fun getInstance(): InvoiceGenerator {
            return ourInstance
        }
    }

    init {
        val invoicesPath = "src/main/resources/data/pos-simulator/invoices.json"
        val mapper = ObjectMapper()
        invoiceIndex = Random()
        invoiceNumber = Random()
        numberOfItems = Random()
        try {
            invoices = mapper.readValue(File(invoicesPath), Array<PosInvoice>::class.java)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getIndex(): Int {
        return invoiceIndex.nextInt(100)
    }

    private fun getNewInvoiceNumber(): Int {
        return invoiceNumber.nextInt(99999999) + 99999
    }

    private fun getNoOfItems(): Int {
        return numberOfItems.nextInt(4) + 1
    }

    fun getNextInvoice(): PosInvoice {
        val invoice = invoices[getIndex()]
        invoice.invoiceNumber = getNewInvoiceNumber().toString()
        invoice.createdTime = System.currentTimeMillis()
        if ("HOME-DELIVERY".equals(invoice.deliveryType, ignoreCase = true)) {
            val deliveryAddress = AddressGenerator.getInstance().getNextAddress()
            invoice.deliveryAddress = deliveryAddress
        }
        val itemCount = getNoOfItems()
        var totalAmount = 0.0
        val items: MutableList<LineItem> = ArrayList()
        val productGenerator = ProductGenerator.getInstance()
        for (i in 0 until itemCount) {
            val item = productGenerator.getNextProduct()
            totalAmount += item.totalValue!!
            items.add(item)
        }
        invoice.numberOfItems = itemCount
        invoice.invoiceLineItems = items
        invoice.totalAmount = totalAmount
        invoice.taxableAmount = totalAmount
        invoice.cGST = totalAmount * 0.025
        invoice.sGST = totalAmount * 0.025
        invoice.cESS = totalAmount * 0.00125
        logger.debug(invoice)

        return invoice
    }
}
