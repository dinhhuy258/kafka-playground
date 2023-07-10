package com.dinhhuy258.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.lang.Exception
import java.util.*

fun main() {
    println("Creating Kafka producer...")

    val props = Properties()
    props[ProducerConfig.CLIENT_ID_CONFIG] = AppConfig.APPLICATION_ID
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = AppConfig.BOOTSTRAP_SERVERS
    props[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = AppConfig.TRANSACTION_ID // idempotence is automatically enabled

    val kafkaProducer = KafkaProducer<Int, String>(props)
    kafkaProducer.initTransactions()

    val topicName1 = "demo-transactional-topic-1"
    println("Start first transaction...")
    try {
        kafkaProducer.beginTransaction()
        for (i in (1..2)) {
            kafkaProducer.send(ProducerRecord(topicName1, i, "Simple message 1"))
        }
        println("Committing first transaction...")
        kafkaProducer.commitTransaction()
    } catch (e: Throwable) {
        println("Exception in the first transaction. Aborting...")
        kafkaProducer.abortTransaction()
    }

    val topicName2 = "demo-transactional-topic-2"
    println("Start second transaction...")
    try {
        kafkaProducer.beginTransaction()
        for (i in (1..2)) {
            kafkaProducer.send(ProducerRecord(topicName2, i, "Simple message 2"))
        }

        throw Exception("Assume that exception is thrown here")
    } catch (e: Throwable) {
        println("Exception in the second transaction. Aborting...")
        kafkaProducer.abortTransaction()
    }

    kafkaProducer.close()
}