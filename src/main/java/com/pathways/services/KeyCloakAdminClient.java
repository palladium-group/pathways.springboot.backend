package com.pathways.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class KeyCloakAdminClient {
    private static final Logger logger = LoggerFactory.getLogger(KeyCloakAdminClient.class);
    private static final String REALM_NAME = "pathways";

    @Autowired
    private Keycloak keycloak;


    public void searchByUsername(String username, boolean exact) {
        logger.info("Searching by username: {} (exact {})", username, exact);
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users()
                .searchByUsername(username, exact);
        logger.info("Users found by username {}", users.stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList()));
    }

    public List<UserRepresentation> getUsers() {
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users().list();
        return users;
    }
}
