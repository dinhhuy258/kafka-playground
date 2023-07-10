# Producer 

Script to create topic

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic demo-transactional-topic-1 --partitions 5 --replication-factor 3 --config min.insync.replicas=2
kafka-topics --create --bootstrap-server kafka1:9091 --topic demo-transactional-topic-2 --partitions 5 --replication-factor 3 --config min.insync.replicas=2
```

Script to verify messages

```sh
kafka-console-consumer --bootstrap-server kafka1:9091 --from-beginning --whitelist "demo-transactional-topic-1|demo-transactional-topic-2"
```
