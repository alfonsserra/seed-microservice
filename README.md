

# Patient microservice example


This project is an application skeleton for a typical [Spring Boot][sboot] microservice. You can use it
to quickly bootstrap your projects and dev environment.

The seed contains a Patient Management sample microservice and is preconfigured to install a full framework and a bunch of development and testing tools for instant development gratification.

The app doesn't do too much, just shows how to use different standards and other suggested tools together:

* Spring Boot
* Spring Boot Web
* Spring Boot Data JPA
* Spring Boot Security
* Spring Boot Test
* [Kafka][kafka] for data synchronization 
* [JWT][jwt]
* [Swagger][swagger]
* Spring Boot Actuator and [Micrometer][micrometer] for metrics with [Prometheus][prometheus]
* [Sleuth][sleuth] and [Zipkin][zipkin] for distributed tracing


## Getting Started

To get you started you can simply clone the `seed-microservice` repository and install the dependencies:

### Prerequisites

You need [git][git] to clone the `seed-microservice` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone `seed-microservice`

Clone the `seed-microservice` repository using git:

```bash
git clone https://github.com/systelab/seed-microservice.git
cd seed-microservice
```

### Install Dependencies

In order to install the dependencies and generate the Uber jar you must run:

```bash
mvn clean install
```

### Run

To launch the server, simply run with java -jar the generated jar file.

```bash
cd target
java -jar seed-microservice-1.0.jar
```

## Getting Started with Kafka

1.  Visit the [Apache Kafka download page][kafka] to install the most recent version (1.0.0 as of this writing).
2.	Extract the binaries into a software/kafka folder. For the current version, it's software/kafka_2.12-1.0.0.
3.	Change your current directory to point to the new folder.
4.	Run Apache ZooKeeper and Kafka:

```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```
5.  Create a test topic to use for testing:

```bash
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic center
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic patient
```

> This commands are not mandatory as the application will create the topics if they do not exist.

6.  Send some messages

```bash
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic center
```

The message should be a valid representation of Center, for example:

```json
{"action": "CREATE", "payload": {"id": "121212", "name": "Center 1"}}
```

or 

```json
{"action": "DELETE", "payload": {"id": "543", "name": "Center 2"}}
```

7. Receive messages

```bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic patient --from-beginning
```
8. Remove topic

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic center
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic patient
```

9. Traces

By default traces will be sent to Zipkin.

Run a Zipkin server with the following command

```bash
docker run -d -p 9411:9411 openzipkin/zipkin
```
    
## API

You will find the swagger UI at http://localhost:8080/swagger-ui.html


[git]: https://git-scm.com/
[sboot]: https://projects.spring.io/spring-boot/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
[kafka]: http://kafka.apache.org/downloads.html
[micrometer]: https://micrometer.io/
[prometheus]: https://prometheus.io/
[sleuth]: https://spring.io/projects/spring-cloud-sleuth
[zipkin]: https://zipkin.apache.org/
