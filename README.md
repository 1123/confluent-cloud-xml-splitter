# confluent-cloud-xml-splitter
Demo application for reading XML messages from an input topic and splitting to several output topics

This is a demo application that shows how to use Confluent Cloud, Kafka Streams, Spring Boot and the Saxon XQuery Processor to 

* read XML messages from an input topic
* split the XML messages using XQuery/XPath
* write the message parts to output topics

### Prerequisites

* Confluent Cloud account
* maven 
* JDK 1.11 or later
* kafka-console-producer for sample data generation. 

### How to run the demo

* Register with Confluent Cloud : https://confluent.cloud
* Create an API key / service account
* Grant prefixed read/write ACLs to the prefix 'kafka-xml-splitter' to the service account
* edit `src/main/resources/application.yaml` and insert your api-key and api-secret
* Use the Confluent Cloud UI to the confluent cloud cli to create the needed topics `kafka-xml-splitter-input`, `kafka-xml-splitter-output1`, `kafka-xml-splitter-output2`.
* start the application: `mvn spring-boot:run`
* produce some sample messages: `produce-xml.sh`
* watch the data coming into the input topic and the split messages arriving at the output topic using the Confluent Cloud UI. 

