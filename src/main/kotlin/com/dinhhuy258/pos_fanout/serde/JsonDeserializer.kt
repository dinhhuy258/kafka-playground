package com.dinhhuy258.pos_fanout.serde

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer

class JsonDeserializer<T> : Deserializer<T> {
    private val objectMapper = ObjectMapper()
    private lateinit var className: Class<T>

    companion object {
        const val KEY_CLASS_NAME_CONFIG = "key.class.name"
        const val VALUE_CLASS_NAME_CONFIG = "value.class.name"
    }

    override fun configure(props: Map<String, *>, isKey: Boolean) {
        className = if (isKey) {
            props[KEY_CLASS_NAME_CONFIG] as Class<T>
        } else {
            props[VALUE_CLASS_NAME_CONFIG] as Class<T>
        }
    }

    override fun deserialize(topic: String, data: ByteArray?): T? {
        if (data == null) {
            return null
        }
        try {
            return objectMapper.readValue(data, className)
        } catch (e: Exception) {
            throw SerializationException(e)
        }
    }

    override fun close() {
        // nothing to close
    }
}