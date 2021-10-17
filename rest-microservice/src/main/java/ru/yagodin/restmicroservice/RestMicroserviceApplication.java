package ru.yagodin.restmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RestMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestMicroserviceApplication.class, args);
    }

}
