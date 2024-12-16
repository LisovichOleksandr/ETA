package ua.apparatus.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ua.apparatus.manager.client.RestClientVerbRestClient;

@Configuration
public class ClientBean {

    @Bean
    public RestClientVerbRestClient verbRestClient(
            @Value("${eta.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientVerbRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());
    }
}
