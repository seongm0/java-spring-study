package com.example.app.common;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.StringJoiner;

@Component
public class JaxbService {
    private final String DEFAULT_JAXB_CONTEXT_PATH = "com.example.oadr20b.model";
    private final JAXBContext _jaxbContext;

    public JaxbService() throws JAXBException {
        StringJoiner joiner = new StringJoiner(":");
        joiner.add(this.getPath("atom"));
        joiner.add(this.getPath("ei"));
        joiner.add(this.getPath("emix"));
        joiner.add(this.getPath("gml"));
        joiner.add(this.getPath("greenbutton"));
        joiner.add(this.getPath("iso"));
        joiner.add(this.getPath("oadr"));
        joiner.add(this.getPath("power"));
        joiner.add(this.getPath("pyld"));
        joiner.add(this.getPath("siscale"));
        joiner.add(this.getPath("strm"));
        joiner.add(this.getPath("xcal"));
        joiner.add(this.getPath("xmldsig"));
        joiner.add(this.getPath("xmldsig11"));
//        joiner.add(this.getPath("xmldsig.properties"));

        this._jaxbContext = JAXBContext.newInstance(joiner.toString());
    }

    private String getPath(String path) {
        return this.DEFAULT_JAXB_CONTEXT_PATH + '.' + path;
    }

    public Marshaller createMarshaller() {
        try {
            Marshaller marshaller = this._jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            return marshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public Unmarshaller createUnmarshaller() {
        try {
            return this._jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
