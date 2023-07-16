package com.dinhhuy258.pos_fanout

import com.dinhhuy258.pos_fanout.RecordBuilder.getHadoopRecords
import com.dinhhuy258.pos_fanout.RecordBuilder.getMaskedInvoice
import com.dinhhuy258.pos_fanout.serde.AppSerdes
import com.dinhhuy258.pos_fanout.types.PosInvoice
import com.dinhhuy258.KafkaConfig
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.apache.logging.log4j.LogManager
import java.util.*


const val clientId = "pos-fanout"
const val kafkaStreamTopicName = "pos-invoice-topic"
const val shipmentTopicName = "shipment-topic"
const val notificationTopicName = "notification-topic"
const val hadoopTopicName = "hadoop-topic"

fun main() {
    val logger = LogManager.getLogger()
    logger.info("Creating kafka stream...")

    val props = Properties()
    props[StreamsConfig.APPLICATION_ID_CONFIG] = clientId
    props[StreamsConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaConfig.BOOTSTRAP_SERVERS

    val streamsBuilder = StreamsBuilder()
    val kafkaStream = streamsBuilder.stream(kafkaStreamTopicName, Consumed.with(Serdes.String(), AppSerdes.PosInvoice()))

    kafkaStream
        .filter { _: String, posInvoice: PosInvoice -> posInvoice.deliveryType.equals("HOME-DELIVERY", true) }
        .to(shipmentTopicName, Produced.with(Serdes.String(), AppSerdes.PosInvoice()))

    kafkaStream
        .filter { _: String, posInvoice: PosInvoice -> posInvoice.customerType.equals("PRIME", true) }
        .mapValues { posInvoice: PosInvoice -> RecordBuilder.getNotification(posInvoice) }
        .to(notificationTopicName, Produced.with(Serdes.String(), AppSerdes.Notification()))

    kafkaStream.mapValues { invoice -> getMaskedInvoice(invoice) }
        .flatMapValues { invoice -> getHadoopRecords(invoice) }
        .to(hadoopTopicName, Produced.with(Serdes.String(), AppSerdes.HadoopRecord()))

    val stream = KafkaStreams(streamsBuilder.build(), props)
    stream.start()

    Runtime.getRuntime().addShutdownHook(Thread {
        logger.info("Stopping stream");

        stream.close()
    })
}
