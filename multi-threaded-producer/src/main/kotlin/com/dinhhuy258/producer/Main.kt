import com.dinhhuy258.producer.AppConfig
import com.dinhhuy258.producer.EventDispatcher
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main() {
    println("Creating Kafka producer...")
    val props = Properties()
    props[ProducerConfig.CLIENT_ID_CONFIG] = AppConfig.APPLICATION_ID;
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = AppConfig.BOOTSTRAP_SERVERS

    val kafkaProducer = KafkaProducer<Int, String>(props)

    val topicName = "demo-multi-threaded-topic"
    val filePaths = listOf(
        "src/main/resources/NSE05NOV2018BHAV.csv",
        "src/main/resources/NSE06NOV2018BHAV.csv"
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
