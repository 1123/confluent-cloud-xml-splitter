package com.example.demo;

import lombok.SneakyThrows;
import net.sf.saxon.s9api.*;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.xml.transform.sax.SAXSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

@Service
public class XQuerySplitter {

    private XQueryEvaluator qe1;
    private XQueryEvaluator qe2;
    private Processor proc;

    @SneakyThrows
    public XQuerySplitter() {
        proc = new Processor(false);
        XQueryCompiler comp = proc.newXQueryCompiler();
        comp.declareNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        XQueryExecutable exp1 = comp.compile("<result>{/message/a}</result>");
        qe1 = exp1.load();
        XQueryExecutable exp2 = comp.compile("<result>{/message/b}</result>");
        qe2 = exp2.load();
    }

    public byte[] getFirstPart(byte[] bytes) {
        try {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            SAXSource source = new SAXSource(new InputSource(byteArrayInputStream));
            qe1.setSource(source);
            Serializer out = proc.newSerializer();
            out.setOutputProperty(Serializer.Property.METHOD, "xml");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            out.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            out.setOutputStream(byteArrayOutputStream);
            qe1.run(out);
            return byteArrayOutputStream.toByteArray();
        } catch (SaxonApiException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return new byte[0];
        }
    }

    public byte[] getSecondPart(byte[] bytes) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            SAXSource source = new SAXSource(new InputSource(byteArrayInputStream));
            qe2.setSource(source);
            Serializer out = proc.newSerializer();
            out.setOutputProperty(Serializer.Property.METHOD, "xml");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            out.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            out.setOutputStream(byteArrayOutputStream);
            qe2.run(out);
            return byteArrayOutputStream.toByteArray();
        } catch (SaxonApiException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
            return new byte[0];
        }
    }


}
