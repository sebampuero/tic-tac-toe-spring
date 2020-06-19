package com.example.sockets.SocketDemo.dao;

import java.net.HttpURLConnection;

import com.example.sockets.SocketDemo.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SessionDAO {

    @Autowired
    private RestTemplate restTemplate;
    private final String baseURL = System.getenv("session-manager-ip") + ":8080" + "/api";

    public boolean addClient(Client clientOne) {
        // call restTemplate.postForEntity with client body
        String uri = baseURL + "/client";
        ResponseEntity<Void> response = restTemplate.postForEntity(uri, clientOne, Void.class);
        return response.getStatusCodeValue() == HttpURLConnection.HTTP_OK;
    }

    public boolean addClient(Client clientTwo, String clientOneSessionId) {
        // call restTemplate.postForEntity with client body and session id path var
        String uri = baseURL + "/client/" + clientOneSessionId;
        ResponseEntity<Void> response = restTemplate.postForEntity(uri, clientTwo, Void.class);
        return response.getStatusCodeValue() == HttpURLConnection.HTTP_OK;
    }

    public boolean sessionIdExists(String sessionId) {
        // call restTemplate.getForEntity and execute command when response back
        String uri = baseURL + "/client/" + sessionId;
        ResponseEntity<Void> response = restTemplate.getForEntity(uri, Void.class);
        return response.getStatusCodeValue() == HttpURLConnection.HTTP_OK;
    }

}