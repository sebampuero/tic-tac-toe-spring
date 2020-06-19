package com.example.sockets.SocketDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.example.sockets.SocketDemo.entity.Client;

import org.springframework.stereotype.Component;

//TODO: implement this as a separate application
// the separate application will have a REST API that responds to session id requests

@Component
public class SessionsManager {

	public SessionsManager() {
		this.clientsMapping = new ConcurrentHashMap<String, Client[]>();
	}

	private Map<String, Client[]> clientsMapping;

	public void addClientOne(Client clientOne) {
		Client[] clients = {clientOne, null};
		clientsMapping.put(clientOne.getSessionId(), clients);
	}

	public void addClientTwo(String clientOneSessionId, Client clientTwo) {
		Client[] clients;
		if(clientsMapping.containsKey(clientOneSessionId)){
			clients = clientsMapping.get(clientOneSessionId);
			clients[1] = clientTwo;
			clientsMapping.put(clientTwo.getSessionId(), clients);
		}
	}

	public void removeClient(String clientSessionId) {
		if (clientsMapping.containsKey(clientSessionId))
			clientsMapping.remove(clientSessionId);
	}

	public Map<String, Client[]> getClientsMapping() {
		return clientsMapping;
	}	

}