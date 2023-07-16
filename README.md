# Kafka playground 

## Simple producer

Script to create topic

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic demo-topic --partitions 5 --replication-factor 3
```

## Multi threaded producer 

Script to create topic

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic demo-multi-threaded-topic --partitions 5 --replication-factor 3
```

## Transactional producer 

Script to create topics

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic demo-transactional-topic-1 --partitions 5 --replication-factor 3 --config min.insync.replicas=2
kafka-topics --create --bootstrap-server kafka1:9091 --topic demo-transactional-topic-2 --partitions 5 --replication-factor 3 --config min.insync.replicas=2
```

Script to verify messages

```sh
kafka-console-consumer --bootstrap-server kafka1:9091 --from-beginning --whitelist "demo-transactional-topic-1|demo-transactional-topic-2"
```

## Pos simulator

Script to create topic

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic pos-invoice-topic --partitions 5 --replication-factor 3 --config min.insync.replicas=2
```

Script to verify messages

```sh
kafka-console-consumer --bootstrap-server kafka1:9091 --from-beginning --whitelist "pos-invoice-topic"
```

## Simple kafka stream

Run this demo with the simple producer demo.

## Pos fanout

Run this demo with the pos simulator demo.

Script to create topic

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic pos-invoice-topic --partitions 5 --replication-factor 3 --config min.insync.replicas=2

kafka-topics --create --bootstrap-server kafka1:9091 --topic shipment-topic --partitions 5 --replication-factor 3 --config min.insync.replicas=2

kafka-topics --create --bootstrap-server kafka1:9091 --topic notification-topic --partitions 5 --replication-factor 3 --config min.insync.replicas=2

kafka-topics --create --bootstrap-server kafka1:9091 --topic hadoop-topic --partitions 5 --replication-factor 3 --config min.insync.replicas=2
```

Script to verify messages

```sh
kafka-console-consumer --bootstrap-server kafka1:9091 --from-beginning --whitelist "shipment-topic"
```
