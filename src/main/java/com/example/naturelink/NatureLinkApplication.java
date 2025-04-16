package com.example.naturelink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.naturelink.repository")
@EntityScan("com.example.naturelink.entity")

@SpringBootApplication
public class NatureLinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatureLinkApplication.class, args);
    }

}
