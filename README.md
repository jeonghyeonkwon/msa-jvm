# MSA-JVM

## 도커 실행

```shell
docker docker-compose

```


## 카프카 토픽 생성

```shell
docker exec -it kafka-jvm bash

/opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic auth-users --replication-factor 1 --partitions 3

/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic auth-users --from-beginning
```