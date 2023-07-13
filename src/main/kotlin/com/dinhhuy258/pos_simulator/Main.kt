package com.dinhhuy258.simple_producer.com.dinhhuy258.pos_simulator

import com.dinhhuy258.pos_simulator.serde.JsonSerializer
import com.dinhhuy258.pos_simulator.types.PosInvoice
import com.dinhhuy258.simple_producer.KafkaConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

const val clientId = "pos-simulator"
const val topicName = "pos-invoice-topic"
const val noOfProducers = 2
const val produceSpeed = 100

private fun createKafkaProducer(): KafkaProducer<String, PosInvoice> {
    val properties = Properties()
    properties[ProducerConfig.CLIENT_ID_CONFIG] = clientId
    properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaConfig.BOOTSTRAP_SERVERS
    properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

    return KafkaProducer(properties)
}

fun main() {
    val kafkaProducer = createKafkaProducer()
    val executor = Executors.newFixedThreadPool(noOfProducers)
    val producers: MutableList<ProducerRunnable> = ArrayList()

    for (i in 0 until noOfProducers) {
        val producerRunnable = ProducerRunnable(i, kafkaProducer, topicName, produceSpeed)
        producers.add(producerRunnable)
        executor.submit(producerRunnable)
    }

    Runtime.getRuntime().addShutdownHook(Thread {
        for (p in producers) {
            p.shutdown()
        }
        try {
            executor.awaitTermination((produceSpeed * 2).toLong(), TimeUnit.MILLISECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    })
}