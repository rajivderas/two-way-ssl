package com.rajivderas.twoway.ssl.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequestMapping(value = "/two-way-ssl-service-gateway")
public class TwoWaySSLGatewayController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Environment environment;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String getData() {
        System.out.println("Returning data from two-way-ssl-service-gateway own data method");
        return "Hello from two-way-ssl-service-gateway-data method";
    }

    @RequestMapping(value = "/two-way-ssl-ms-data", method = RequestMethod.GET)
    public String getTwoWaySSLMSData() {
        System.out.println("Got inside two-way-ssl-service-gateway  two-way-ssl-ms-data method");
        try {
            String msEndpoint = environment.getProperty("endpoint.two-way-ssl-service");
            System.out.println("MS Endpoint name : [" + msEndpoint + "]");

            return restTemplate.getForObject(new URI(msEndpoint), String.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Exception occurred.. so, returning default data";
    }
}
