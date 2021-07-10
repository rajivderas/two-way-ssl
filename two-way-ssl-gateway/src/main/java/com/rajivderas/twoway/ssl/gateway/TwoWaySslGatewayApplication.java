package com.rajivderas.twoway.ssl.gateway;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.security.KeyStore;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Two Way SSL Gateway API", version = "1.0.0"))
public class TwoWaySslGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoWaySslGatewayApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        KeyStore keyStore;
        HttpComponentsClientHttpRequestFactory httpRequestFactory;

        try {
            keyStore = KeyStore.getInstance("jks");
            ClassPathResource classPathResource = new ClassPathResource("two-way-ssl-gateway.jks");
            InputStream inputStream = classPathResource.getInputStream();
            keyStore.load(inputStream, "rajivderas".toCharArray());

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    new SSLContextBuilder().loadTrustMaterial(
                            null, new TrustSelfSignedStrategy()).loadKeyMaterial(
                            keyStore, "rajivderas".toCharArray()).build(), NoopHostnameVerifier.INSTANCE);

            HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
                    .setMaxConnPerRoute(Integer.valueOf(5))
                    .setMaxConnTotal(Integer.valueOf(5))
                    .build();

            httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            httpRequestFactory.setReadTimeout(Integer.valueOf(10000));
            httpRequestFactory.setConnectTimeout(Integer.valueOf(10000));

            restTemplate.setRequestFactory(httpRequestFactory);


        } catch (Exception e) {
            System.out.println("Exception Occured while creating restTemplate " + e);
            e.printStackTrace();
        }
        return restTemplate;
    }
}
