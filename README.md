Quarkus Kafka Quickstart
========================

This project illustrates how you can interact with Apache Kafka using MicroProfile Reactive Messaging. I have used the Quarkus base example project and modified it. So the html page name is still prices. 


1. It includes SSL integration
2. Value serialization and deserialization
3. Manual Message Acknowledgement
4. Failure Strategy

## Kafka cluster

First you need a Kafka cluster. You can follow the instructions from the [Apache Kafka web site](https://kafka.apache.org/quickstart) or run `docker-compose up` if you have docker installed on your machine.

## Start the application

The application can be started using: 

```bash
mvn quarkus:dev
```

Then, open your browser to `http://localhost:8089/prices.html`, and you should show the raw XML.

## Anatomy

In addition to the `prices.html` page, the application is composed by 3 components:

* `MessageProducer` - a bean generating Inflight kafka payload. They are sent to a Kafka topic
* `MessageConsumer` - on the consuming side, the `MessageConsumer` receives the Kafka message and adds an additional property to the payload.
The result is sent to an in-memory stream of data
* `MessageStreamResource`  - the `MessageStreamResource` retrieves the in-memory stream of data and send these payloads to the browser using Server-Sent Events.

The interaction with Kafka is managed by MicroProfile Reactive Messaging.
The configuration is located in the application configuration.

## Running in native

You can compile the application into a native binary using:

`mvn clean install -Pnative`

and run with:

`./target/kafka-quickstart-1.0.0-SNAPSHOT-runner` 
