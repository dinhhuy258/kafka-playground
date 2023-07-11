package com.dinhhuy258;

import com.dinhhuy258.datagenerator.InvoiceGenerator;
import com.dinhhuy258.types.PosInvoice;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

public class ProducerRunnable implements Runnable {
    private static final Logger logger = LogManager.getLogger();

    final int id;

    private final KafkaProducer<String, PosInvoice> kafkaProducer;

    private final String topicName;

    private final int produceSpeed;

    private final InvoiceGenerator invoiceGenerator;

    private AtomicBoolean stop;

    public ProducerRunnable(int id, KafkaProducer<String, PosInvoice> kafkaProducer, String topicName, int produceSpeed) {
        this.id = id;
        this.kafkaProducer = kafkaProducer;
        this.topicName = topicName;
        this.produceSpeed = produceSpeed;
        this.invoiceGenerator = InvoiceGenerator.getInstance();
        this.stop = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        logger.info("Start producer thread - " + id);

        try {
            while (!stop.get()) {
                PosInvoice invoice = invoiceGenerator.getNextInvoice();
                kafkaProducer.send(new ProducerRecord<>(topicName, invoice.getStoreID(), invoice));
                Thread.sleep(produceSpeed);
            }
        } catch (InterruptedException e) {
            logger.error("Exception in Producer thread - " + id);
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        logger.info("Shutting down producer thread - " + id);
        stop.set(true);
    }
}
