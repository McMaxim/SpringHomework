package com.homework.spring1.repository;

import java.util.Random;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Repository
public class ExternalApiRepository {
    
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    
    private static final String RANDOM_DATA_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final Random rand = new Random();
    
    public ExternalApiRepository(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }
    
    public String getDataWithRestTemplate() {
        int randomId = rand.nextInt(100) + 1;
        return restTemplate.getForObject(RANDOM_DATA_URL + "/" + randomId, String.class);
    }
    
    public String getDataWithWebClientBlocking() {
        int randomId = rand.nextInt(100) + 1;
        return webClient.get()
                .uri("/posts/" + randomId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
} 