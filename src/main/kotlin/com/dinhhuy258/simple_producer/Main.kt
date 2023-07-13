package com.dinhhuy258.simple_producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.logging.log4j.LogManager
import java.util.Properties

const val clientId = "simple-producer"
const val topicName = "demo-topic"

fun main() {
    val logger = LogManager.getLogger()

    logger.info("Creating Kafka producer...")
    val props = Properties()
    props[ProducerConfig.CLIENT_ID_CONFIG] = clientId
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaConfig.BOOTSTRAP_SERVERS

    val kafkaProducer = KafkaProducer<Int, String>(props)

    logger.info("Start sending messages")

    for (i in 1..100000) {
        logger.info("Sending message $i")
        kafkaProducer.send(ProducerRecord(topicName, i, "Kafka message $i"))
    }

    logger.info("Finished sending messages")

    kafkaProducer.close()
}
