

# Customer microservice example


## Getting Started

To get you started you can simply clone the `customer-microservice-example` repository and install the dependencies:

### Prerequisites

You need [git][git] to clone the `customer-microservice-example` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone `customer-microservice-example`

Clone the `customer-microservice-example` repository using git:

```bash
git clone https://github.com/systelab/customer-microservice-example.git
cd customer-microservice-example
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
java -jar customer-microservice-example-1.0.jar
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
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic customer-type
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic customer
```

> This commands are not mandatory as the application will create the topics if they do not exist.

6.  Send some messages

```bash
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic modulab
```

The message should be a valid representation of an CustomerTypeEvent, for example:

```json
{"action": "CREATE", "payload": {"id": "121212", "name": "VIP"}}
```

or 

```json
{"action": "DELETE", "payload": {"id": "543", "name": "Discount"}}
```

7. Receive messages

```bash
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic customer --from-beginning
```
8. Remove topic

```bash
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic customer-type
bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic customer
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
