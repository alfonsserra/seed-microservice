

#  Kafka Spring boot example


## Getting Started

To get you started you can simply clone the `kafka-springboot-example` repository and install the dependencies:

### Prerequisites

You need [git][git] to clone the `kafka-springboot-example` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone `kafka-springboot-example`

Clone the `kafka-springboot-example` repository using git:

```bash
git clone https://github.com/systelab/kafka-springboot-example.git
cd kafka-springboot-example
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
java -jar kafka-springboot-example-1.0.jar
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
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic modulab
```

6.  Send some messages

```bash
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic modulab
```

The message should be a valid representation of an CustomerTypeEvent, for example:

```json
{"action": "CREATE", "type": {"id": "121212", "name": "VIP"}}
```

or 

```json
{"action": "DELETE", "type": {"id": "543", "name": "Discount"}}
```

## API

You will find the swagger UI at http://localhost:8080/swagger-ui.html


[git]: https://git-scm.com/
[sboot]: https://projects.spring.io/spring-boot/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
[kafka]: http://kafka.apache.org/downloads.html
