package com.example.sockets.SocketDemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.example.sockets.SocketDemo.SessionsManager;
import com.example.sockets.SocketDemo.entity.Client;

@Component
public class WebsocketEventListener {

	@Autowired
	private SessionsManager manager;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@EventListener
	public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
		GenericMessage message = (GenericMessage) event.getMessage();
		String sessionId = (String) message.getHeaders().get("simpSessionId");
		if(manager.getClientsMapping().containsKey(sessionId)){ //TODO: call to the REST API: GET
			Client clientOne = manager.getClientsMapping().get(sessionId)[0];
			Client clientTwo = manager.getClientsMapping().get(sessionId)[1];
			simpMessagingTemplate.convertAndSendToUser(clientOne.getSessionId(), "/peer-disconnected", "Peer disconnected");
			simpMessagingTemplate.convertAndSendToUser(clientTwo.getSessionId(), "/peer-disconnected", "Peer disconnected");
			manager.removeClient(sessionId); //TODO: call to the REST API: DELETE
		}
	}

}
