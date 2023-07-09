package com.dinhhuy258.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

fun main() {
    println("Creating Kafka producer...")
    val props = Properties()
    props[ProducerConfig.CLIENT_ID_CONFIG] = AppConfig.APPLICATION_ID;
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = AppConfig.BOOTSTRAP_SERVERS

    val kafkaProducer = KafkaProducer<Int, String>(props)

    println("Start sending messages")
    val topicName = "demo-topic"

    for (i in 1..100000) {
        println("Sending message $i")
        kafkaProducer.send(ProducerRecord(topicName, i, "Kafka message $i"))
    }

    println("Finished sending messages")

    kafkaProducer.close()
}