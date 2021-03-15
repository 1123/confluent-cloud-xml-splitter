package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.s9api.SaxonApiException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@Slf4j
class XQuerySplitterTest {

	@Test
	void testSplit() throws SaxonApiException {
		XQuerySplitter xQuerySplitter = new XQuerySplitter();
		byte[] input = "<?xml version=\"1.0\"?><message><a>AAAA</a><b>BBBB</b></message>".getBytes(StandardCharsets.UTF_8);
		byte[] firstPart = xQuerySplitter.getFirstPart(input);
		byte[] secondPart = xQuerySplitter.getSecondPart(input);
		log.info(new String(firstPart));
		log.info(new String(secondPart));
	}

}
