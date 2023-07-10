package com.dinhhuy258.producer

class AppConfig {
    companion object {
        const val APPLICATION_ID = "transactional-producer"
        const val BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093"
        const val TRANSACTION_ID = "transactional-producer"
    }
}