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


## 프로젝트 구조

### auth

* [x] 회원가입 기능
* [x] 로그인, 로그아웃, 토큰 재발급
* [x] 유저 pk로 유저 반환 
* [x] 헤더 토큰으로 사용사 pk 반환 (클라이언트에서 페이지 마운트 시 userPk 값 사용)

 
### memo-service
* [x] 메모 생성
* [x] 메모 리스트
* [x] 메모 상세

### board-service
* [x] 게시글 생성
* [x] 게시글 리스트
* [x] 게시글 상세
* [x] 댓글 생성
* [x] 댓글 리스트
* [ ] 좋아요 생성, 삭제

### memo-batch
* [ ] 메모 상태 값 변경



