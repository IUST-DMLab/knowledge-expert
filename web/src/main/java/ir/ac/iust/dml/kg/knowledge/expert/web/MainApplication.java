package ir.ac.iust.dml.kg.knowledge.expert.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * Farsi Knowledge Graph Project
 * Iran University of Science and Technology (Year 2017)
 * Developed by HosseiN Khademi khaledi
 *
 * MainApplication of web
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ImportResource({"classpath:persistence-context.xml", "classpath:web-services-def.xml"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
