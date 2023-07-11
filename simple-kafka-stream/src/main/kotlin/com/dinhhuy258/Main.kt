package com.dinhhuy258

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import java.util.*

fun main() {
    val props = Properties()
    props[StreamsConfig.APPLICATION_ID_CONFIG] = AppConfig.APPLICATION_ID
    props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = AppConfig.BOOTSTRAP_SERVERS
    props[StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG] = Serdes.Integer().javaClass
    props[StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG] = Serdes.String().javaClass

    val topicName = "demo-topic"
    val streamBuilder = StreamsBuilder()
    val stream = streamBuilder.stream<Int, String>(topicName)
    stream.foreach { key: Int, value: String -> println("$key => $value") }

    val topology = streamBuilder.build()
    val kafkaStream = KafkaStreams(topology, props)
    kafkaStream.start()

    Runtime.getRuntime().addShutdownHook(Thread {
        println("Shutdown the stream")
        kafkaStream.close()
    })
}