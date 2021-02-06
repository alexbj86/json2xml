package com.convertjson2xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Json2xmlApplication {

    @Autowired
    private ConverterJson2Xml converterJson2Xml;

    public static void main(String[] args) {
        SpringApplication.run(Json2xmlApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> converterJson2Xml.converter();
    }

}
