package com.example.demo;

import net.sf.saxon.Query;
import net.sf.saxon.s9api.*;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Arrays;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private XQuerySplitter xQuerySplitter;

	@Bean
	public KStream<?, ?> kStream(StreamsBuilder kStreamBuilder) {
		KStream<Integer, byte[]> stream = kStreamBuilder.stream(
				"kafka-xml-splitter-input",
				Consumed.with(new Serdes.IntegerSerde(), new Serdes.ByteArraySerde())
		);
		stream.mapValues(xQuerySplitter::getFirstPart)
				.to("kafka-xml-splitter-output1", Produced.with(new Serdes.IntegerSerde(), new Serdes.ByteArraySerde()));
		stream.mapValues(xQuerySplitter::getSecondPart)
				.to("kafka-xml-splitter-output2", Produced.with(new Serdes.IntegerSerde(), new Serdes.ByteArraySerde()));
		return stream;
	}

}

