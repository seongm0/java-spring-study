package com.example.app;

import com.example.oadr20b.model.oadr.OadrPayload;
import com.example.oadr20b.model.oadr.OadrPollType;
import com.example.oadr20b.model.oadr.OadrSignedObject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class AppService implements InitializingBean {
    JAXBContext jaxbContext;

    public String getResourceFileAsString(String fileName) {
        InputStream is = this.getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return (String) reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    public InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = AppService.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public String getIndex() throws JAXBException {
        System.out.println("start getIndex method");

        this.xmlToPojo("xml-samples/kpx/20180614/CSW-18AM-N01_135010.xml");
        this.pojoToXml();

        System.out.println("end getIndex method");
        return "Hello Service";
    }

    private void xmlToPojo(String filePath) throws JAXBException {
        System.out.println("start xmlToPojo method");
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();

        InputStream xml = this.getResourceFileAsInputStream(filePath);

        OadrPayload payload = (OadrPayload) unmarshaller.unmarshal(xml);
        System.out.println(payload.getOadrSignedObject().getOadrDistributeEvent().getEiResponse().getRequestID());
        System.out.println("end xmlToPojo method");
    }

    private void pojoToXml() throws JAXBException {
        System.out.println("start pojoToXml method");
        Marshaller marshaller = this.jaxbContext.createMarshaller();

        OadrPollType oadrPollType = new OadrPollType();
        oadrPollType.setVenID("venId");

        OadrSignedObject oadrSignedObject = new OadrSignedObject();
        oadrSignedObject.setOadrPoll(oadrPollType);
        OadrPayload oadrPayload = new OadrPayload();
        oadrPayload.setOadrSignedObject(oadrSignedObject);

        // The following code adds a new line to improve readability.
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        // read xml string from marshaller
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(oadrPayload, os);

        String xmlString = os.toString(StandardCharsets.UTF_8);

        System.out.println(xmlString);
        System.out.println("end pojoToXml method");
    }

    @Override
    public void afterPropertiesSet() throws JAXBException {
        StringJoiner joiner = new StringJoiner(":");
        joiner.add("com.example.oadr20b.model.atom");
        joiner.add("com.example.oadr20b.model.ei");
        joiner.add("com.example.oadr20b.model.emix");
        joiner.add("com.example.oadr20b.model.gml");
        joiner.add("com.example.oadr20b.model.greenbutton");
        joiner.add("com.example.oadr20b.model.iso");
        joiner.add("com.example.oadr20b.model.oadr");
        joiner.add("com.example.oadr20b.model.power");
        joiner.add("com.example.oadr20b.model.pyld");
        joiner.add("com.example.oadr20b.model.siscale");
        joiner.add("com.example.oadr20b.model.strm");
        joiner.add("com.example.oadr20b.model.xcal");
        joiner.add("com.example.oadr20b.model.xmldsig");
//        joiner.add("com.example.oadr20b.model.xmldsig.properties");
        joiner.add("com.example.oadr20b.model.xmldsig11");

        this.jaxbContext = JAXBContext.newInstance(joiner.toString());
    }
}
