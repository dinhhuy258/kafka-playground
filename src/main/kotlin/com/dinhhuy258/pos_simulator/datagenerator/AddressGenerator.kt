package com.dinhhuy258.pos_simulator.datagenerator

import com.dinhhuy258.pos_simulator.types.DeliveryAddress
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.util.Random

class AddressGenerator private constructor() {
    private val random: Random
    private var addresses: Array<DeliveryAddress>

    companion object {
        private val ourInstance = AddressGenerator()

        fun getInstance(): AddressGenerator {
            return ourInstance
        }
    }

    init {
        val addressesPath = "src/main/resources/data/pos-simulator/addresses.json"
        val mapper = ObjectMapper()
        random = Random()
        try {
            addresses = mapper.readValue(File(addressesPath), Array<DeliveryAddress>::class.java)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getIndex(): Int {
        return random.nextInt(100)
    }

    fun getNextAddress(): DeliveryAddress {
        return addresses[getIndex()]
    }
}
