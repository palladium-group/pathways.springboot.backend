package com.pathways.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KeyCloakAdminClient {
    private static final Logger logger = LoggerFactory.getLogger(KeyCloakAdminClient.class);
    private static final String REALM_NAME = "pathways";
    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("${keycloak_clientid}")
    private String clientId;
    @Value("${keycloak_username}")
    private String username;

    @Value("${keycloak_password}")
    private String password;

    public KeyCloakAdminClient(RestTemplateBuilder restTemplateBuilder, Environment environment) {
        this.restTemplate = restTemplateBuilder.build();
        this.environment = environment;
    }

    public String getToken() {
        String tokenUrl = environment.getProperty("token_url");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        // Replace "YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET", "YOUR_USERNAME", and "YOUR_PASSWORD" with actual values
        String requestBody = "grant_type=password&client_id=" + clientId + "&username=" + username + "&password="+ password;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, request, TokenResponse.class);
        // Assuming the response body contains the access token
        return response.getBody().getAccessToken();
    }

    public List<?> getUsers() {
        String token = getToken();
        String dataUrl = "https://nextgen-pct-kc.eastus.cloudapp.azure.com/auth/admin/realms/pathways/users";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(dataUrl, HttpMethod.GET, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<KeyCloakUser> userList = objectMapper.readValue(response.getBody(), new TypeReference<List<KeyCloakUser>>() {});
            return userList;
        } catch (IOException e) {
            // Handle JSON parsing exception
            e.printStackTrace();
            return null;
        }
    }

    public boolean CreateUser(String firstName, String lastName, String email, boolean isEnabled)
    {
        try {
            String token = getToken();
            String dataUrl = "https://nextgen-pct-kc.eastus.cloudapp.azure.com/auth/admin/realms/pathways/users";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            // Create credentials JSON array
            String credentialsJson = "[{\"type\":\"password\",\"value\":\"P@ss$G#tew@y\",\"temporary\":true}]";

            // Create requiredActions JSON array
            String requiredActionsJson = "[\"UPDATE_PASSWORD\"]";

            String requestBody = "{" +
                    "\"firstName\": \"" + firstName + "\"," +
                    "\"lastName\": \"" + lastName + "\", " +
                    "\"email\": \"" + email + "\", " +
                    "\"username\": \"" + email + "\"," +
                    " \"enabled\": " + isEnabled + ","
                    + "\"credentials\":" + credentialsJson + ","
                    + "\"requiredActions\":" + requiredActionsJson
                    + "}";

            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(dataUrl, HttpMethod.POST, request, String.class);

            // Get the response body and other information
            String responseBody = response.getBody();
            HttpStatus statusCode = (HttpStatus) response.getStatusCode();

            // You can handle the response as needed
            System.out.println("Response Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            // Return true if the request was successful (2xx status code)
            return statusCode.is2xxSuccessful();
        } catch (HttpClientErrorException e) {
            // Catch HttpClientErrorException for non-2xx status codes
            System.err.println("Error Response Code: " + e.getRawStatusCode());
            System.err.println("Error Response Body: " + e.getResponseBodyAsString());

            // Return false for unsuccessful requests
            return false;
        } catch (Exception e) {
            // Catch other exceptions
            e.printStackTrace();

            // Return false for other exceptions
            return false;
        }
    }
}
