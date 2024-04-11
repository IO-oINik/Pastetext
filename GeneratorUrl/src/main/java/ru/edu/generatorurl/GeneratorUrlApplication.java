package ru.edu.generatorurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class GeneratorUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneratorUrlApplication.class, args);
    }

}
