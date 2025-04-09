package com.example.demo.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(String... args) throws Exception {
      
        String[] endpoints = {
                "https://apex-new-framework-production.up.railway.app/",
                "https://apex-new-framework-production.up.railway.app/syllabus-IEB"
        };

        for (String url : endpoints) {
            try {
                String response = restTemplate.getForObject(url, String.class);
                System.out.println("Response from " + url + ": " + response);
            } catch (Exception e) {
                System.err.println("Error while calling " + url + ": " + e.getMessage());
            }
        }
    }
}