package com.dinhhuy258.pos_fanout.serde

import com.dinhhuy258.pos_fanout.types.HadoopRecord
import com.dinhhuy258.pos_fanout.types.Notification
import com.dinhhuy258.pos_fanout.types.PosInvoice
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes


class AppSerdes: Serdes() {
    companion object {
        internal class PosInvoiceSerde :
            WrapperSerde<PosInvoice>(JsonSerializer(), JsonDeserializer<PosInvoice>())

        fun PosInvoice(): Serde<PosInvoice> {
            val serde = PosInvoiceSerde()
            val serdeConfigs: MutableMap<String, Any?> = HashMap()
            serdeConfigs[JsonDeserializer.VALUE_CLASS_NAME_CONFIG] =
                PosInvoice::class.java
            serde.configure(serdeConfigs, false)

            return serde
        }

        internal class NotificationSerde :
            WrapperSerde<Notification>(JsonSerializer(), JsonDeserializer<Notification>())

        fun Notification(): Serde<Notification> {
            val serde = NotificationSerde()
            val serdeConfigs: MutableMap<String, Any?> = HashMap()
            serdeConfigs[JsonDeserializer.VALUE_CLASS_NAME_CONFIG] =
                Notification::class.java
            serde.configure(serdeConfigs, false)

            return serde
        }

        internal class HadoopRecordSerde :
            WrapperSerde<HadoopRecord>(JsonSerializer(), JsonDeserializer<HadoopRecord>())

        fun HadoopRecord(): Serde<HadoopRecord> {
            val serde = HadoopRecordSerde()
            val serdeConfigs: MutableMap<String, Any?> = HashMap()
            serdeConfigs[JsonDeserializer.VALUE_CLASS_NAME_CONFIG] =
                HadoopRecord::class.java
            serde.configure(serdeConfigs, false)

            return serde
        }
    }
}