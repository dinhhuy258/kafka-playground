package com.dinhhuy258.simple_producer.com.dinhhuy258.simple_kafka_stream

import com.dinhhuy258.simple_producer.KafkaConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.logging.log4j.LogManager
import java.util.*

const val clientId = "simple-kafka-stream"
const val topicName = "demo-topic"

fun main() {
    val logger = LogManager.getLogger()

    logger.info("Creating kafka stream...")

    val props = Properties()
    props[StreamsConfig.APPLICATION_ID_CONFIG] = clientId
    props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaConfig.BOOTSTRAP_SERVERS
    props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.Integer().javaClass
    props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass

    val streamBuilder = StreamsBuilder()
    val stream = streamBuilder.stream<Int, String>(topicName)
    stream.foreach { key: Int, value: String -> println("$key => $value") }

    val topology = streamBuilder.build()
    val kafkaStream = KafkaStreams(topology, props)
    kafkaStream.start()

    Runtime.getRuntime().addShutdownHook(Thread {
        logger.info("Shutdown the stream")
        kafkaStream.close()
    })
}

