package com.dinhhuy258.pos_simulator

import com.dinhhuy258.pos_simulator.datagenerator.InvoiceGenerator
import com.dinhhuy258.pos_simulator.types.PosInvoice
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.logging.log4j.LogManager
import java.util.concurrent.atomic.AtomicBoolean

class ProducerRunnable(
    private val id: Int,
    private val kafkaProducer: KafkaProducer<String, PosInvoice>,
    private val topicName: String,
    private val produceSpeed: Int
) : Runnable {
    private val stop = AtomicBoolean(false)

    override fun run() {
        val invoiceGenerator = InvoiceGenerator.getInstance()

        logger.info("Start producer thread - $id")

        try {
            while (!stop.get()) {
                val invoice = invoiceGenerator.getNextInvoice()
                kafkaProducer.send(ProducerRecord(topicName, invoice.storeID, invoice))
                Thread.sleep(produceSpeed.toLong())
            }
        } catch (e: InterruptedException) {
            logger.error("Exception in Producer thread - $id")
            throw RuntimeException(e)
        }
    }

    fun shutdown() {
        logger.info("Shutting down producer thread - $id")
        stop.set(true)
    }

    companion object {
        private val logger = LogManager.getLogger()
    }
}