package com.dinhhuy258.producer

class AppConfig {
    companion object {
        const val APPLICATION_ID = "MultiThreadedProducer";
        const val BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093";
    }
}
