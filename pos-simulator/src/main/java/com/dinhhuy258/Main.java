package com.dinhhuy258;

import com.dinhhuy258.serde.JsonSerializer;
import com.dinhhuy258.types.PosInvoice;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static KafkaProducer<String, PosInvoice> createKafkaProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, AppConfigs.bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new KafkaProducer<>(properties);
    }

    public static void main(String[] args) {
        String topicName = "pos-invoice-topic";
        int noOfProducers = 2;
        int produceSpeed = 100;

        KafkaProducer<String, PosInvoice> kafkaProducer = createKafkaProducer();
        ExecutorService executor = Executors.newFixedThreadPool(noOfProducers);
        final List<ProducerRunnable> producers = new ArrayList<>();

        for (int i = 0; i < noOfProducers; ++i) {
            ProducerRunnable producerRunnable = new ProducerRunnable(i, kafkaProducer, topicName, produceSpeed);
            producers.add(producerRunnable);
            executor.submit(producerRunnable);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            for (ProducerRunnable p : producers) {
                p.shutdown();
            }

            try {
                executor.awaitTermination(produceSpeed * 2, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}