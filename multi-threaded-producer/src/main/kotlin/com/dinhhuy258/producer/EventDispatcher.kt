package com.dinhhuy258.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.io.File

class EventDispatcher(
    private val topicName: String,
    private val filePath: String,
    private val kafkaProducer: KafkaProducer<Int, String>
) : Runnable {

    override fun run() {
        println("Start processing $filePath")

        var counter = 0
        File(filePath).forEachLine {
            kafkaProducer.send(ProducerRecord(topicName, null, it))

            ++counter
        }

        println("Finished sending $counter message from $filePath")
    }
}
