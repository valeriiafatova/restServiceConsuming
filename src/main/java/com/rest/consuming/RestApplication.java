package com.rest.consuming;

import com.rest.consuming.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestApplication {
    private static final Logger LOG = LoggerFactory.getLogger(RestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
    
    @Bean
    public ResponseErrorHandler responseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .errorHandler(responseErrorHandler())
                .build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            try{
                ResponseEntity<Course> result 
                    = restTemplate.getForEntity("http://localhost:8080/courses/6", Course.class);
                Course course = result.getBody();
                LOG.info("We are on course: {}", course.getTitle());
            } catch (IllegalArgumentException ex){
              LOG.error("Error while consuming: {}", ex.getMessage());  
            }
                
        };
    }
}
