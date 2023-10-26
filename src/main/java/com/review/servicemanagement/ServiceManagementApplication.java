package com.review.servicemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
public class ServiceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceManagementApplication.class, args);
    }

}
