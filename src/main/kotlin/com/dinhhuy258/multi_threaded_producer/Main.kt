package com.dinhhuy258.multi_threaded_producer

import com.dinhhuy258.KafkaConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.logging.log4j.LogManager
import java.util.*

const val clientId = "multi-threaded-producer"
const val topicName = "demo-multi-threaded-topic"

fun main() {
    val logger = LogManager.getLogger()

    logger.info("Creating Kafka producer...")
    val props = Properties()
    props[ProducerConfig.CLIENT_ID_CONFIG] = clientId
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaConfig.BOOTSTRAP_SERVERS

    val kafkaProducer = KafkaProducer<Int, String>(props)

    val filePaths = listOf(
        "src/main/resources/data/multi-threaded-producer/NSE05NOV2018BHAV.csv",
        "src/main/resources/data/multi-threaded-producer/NSE06NOV2018BHAV.csv"
    )

    val dispatchers = mutableListOf<Thread>()

    filePaths.forEach { filePath ->
        val dispatcher = Thread(EventDispatcher(topicName, filePath, kafkaProducer))
        dispatcher.start()

        dispatchers.add(dispatcher)
    }

    dispatchers.forEach { dispatcher ->
        dispatcher.join()
    }

    kafkaProducer.close()
}
