package com.dinhhuy258.transactional_producer

import com.dinhhuy258.KafkaConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.logging.log4j.LogManager
import java.lang.Exception
import java.util.*

const val clientId = "transactional-producer"
const val topicName1 = "demo-transactional-topic-1"
const val topicName2 = "demo-transactional-topic-2"
const val transactionId = "transactional-producer"

fun main() {
    val logger = LogManager.getLogger()

    logger.info("Creating Kafka producer...")

    val props = Properties()
    props[ProducerConfig.CLIENT_ID_CONFIG] = clientId
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaConfig.BOOTSTRAP_SERVERS
    props[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = transactionId // idempotence is automatically enabled

    val kafkaProducer = KafkaProducer<Int, String>(props)
    kafkaProducer.initTransactions()

    logger.info("Start first transaction...")
    try {
        kafkaProducer.beginTransaction()
        for (i in (1..2)) {
            kafkaProducer.send(ProducerRecord(topicName1, i, "Simple message 1"))
        }
        logger.info("Committing first transaction...")
        kafkaProducer.commitTransaction()
    } catch (e: Throwable) {
        logger.error("Exception in the first transaction. Aborting...", e)

        kafkaProducer.abortTransaction()
    }

    logger.info("Start second transaction...")
    try {
        kafkaProducer.beginTransaction()
        for (i in (1..2)) {
            kafkaProducer.send(ProducerRecord(topicName2, i, "Simple message 2"))
        }

        throw Exception("Assume that exception is thrown here")
    } catch (e: Throwable) {
        logger.error("Exception in the second transaction. Aborting...", e)

        kafkaProducer.abortTransaction()
    }

    kafkaProducer.close()
}