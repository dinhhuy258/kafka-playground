package com.dinhhuy258.multi_threaded_producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.logging.log4j.LogManager
import java.io.File

class EventDispatcher(
    private val topicName: String,
    private val filePath: String,
    private val kafkaProducer: KafkaProducer<Int, String>
) : Runnable {
    private val logger = LogManager.getLogger()

    override fun run() {
        logger.info("Start processing $filePath")

        var counter = 0
        File(filePath).forEachLine {
            kafkaProducer.send(ProducerRecord(topicName, null, it))

            ++counter
        }

        logger.info("Finished sending $counter message from $filePath")
    }
}