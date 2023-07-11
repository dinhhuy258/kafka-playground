# Producer

Script to create topic

```sh
kafka-topics --create --bootstrap-server kafka1:9091 --topic pos-invoice-topic --partitions 5 --replication-factor 3 --config min.insync.replicas=2
```

Script to verify messages

```sh
kafka-console-consumer --bootstrap-server kafka1:9091 --from-beginning --whitelist "pos-invoice-topic"
```
