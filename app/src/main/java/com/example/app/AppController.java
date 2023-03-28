package com.example.app;

import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AppController {
    @Autowired
    private AppService appService;
    @GetMapping()
    public String getIndex() throws JAXBException {
        return this.appService.getIndex();
    }
}
