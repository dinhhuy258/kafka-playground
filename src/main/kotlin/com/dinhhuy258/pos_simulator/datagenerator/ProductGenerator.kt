package com.dinhhuy258.pos_simulator.datagenerator

import com.dinhhuy258.pos_simulator.types.LineItem
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.util.Random

class ProductGenerator private constructor() {
    private val random: Random
    private val qty: Random
    private val products: Array<LineItem>

    companion object {
        private val ourInstance = ProductGenerator()

        fun getInstance(): ProductGenerator {
            return ourInstance
        }
    }

    init {
        val productsPath = "src/main/resources/data/pos-simulator/products.json"
        val mapper = ObjectMapper()
        random = Random()
        qty = Random()
        try {
            products = mapper.readValue(File(productsPath), Array<LineItem>::class.java)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getIndex(): Int {
        return random.nextInt(100)
    }

    private fun getQuantity(): Int {
        return qty.nextInt(2) + 1
    }

    fun getNextProduct(): LineItem {
        val lineItem = products[getIndex()]
        lineItem.itemQty = getQuantity()
        lineItem.totalValue = lineItem.itemPrice!! * lineItem.itemQty!!

        return lineItem
    }
}
