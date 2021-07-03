package com.rajivderas.twoway.ssl.ms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/two-way-ssl-service")
public class TwoWaySSLController {

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String getData(){
        System.out.println("Returning data from two-way-ssl-service data method");
        return "Hello from two-way-ssl-service-data method";
    }
}
