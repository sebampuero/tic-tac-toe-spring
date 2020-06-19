package com.example.sockets.SocketDemo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.sockets.SocketDemo.SessionsManager;
import com.example.sockets.SocketDemo.entity.Client;
import com.example.sockets.SocketDemo.entity.DataExchangeMessage;
import com.example.sockets.SocketDemo.entity.GameInitMessage;
import com.example.sockets.SocketDemo.service.SessionService;

@Controller
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	private SessionsManager manager;

	@Autowired
	SessionService sessionService;

	@MessageMapping("/client-one-start")
	public void handleGameStart(@Payload GameInitMessage msg, @Header("simpSessionId") String clientOneSessionId) {
		String gamerName = msg.getGamerName();
		// manager.addClientOne(new Client(clientOneSessionId, gamerName)); //TODO: call
		// to the REST API: POST
		sessionService.addClient(new Client(clientOneSessionId, gamerName));
	}

	@MessageMapping("/client-two-start")
	public void handleOpponentJoinToGame(@Payload GameInitMessage msg,
			@Header("simpSessionId") String clientTwoSessionId) {
		String clientOneSessionId = msg.getOpponentSessionId();
		String clientTwoOpponentName = msg.getGamerName();

		if (sessionService.sessionIdExists(clientOneSessionId)) {
			String firstTurnSessionId = calculateFirstTurnSessionId(clientOneSessionId, clientTwoSessionId);
			// manager.addClientTwo(clientOneSessionId, new Client(clientTwoSessionId,
			// clientTwoOpponentName));
			sessionService.addClient(new Client(clientTwoSessionId, clientTwoOpponentName), clientOneSessionId);

			msg.setOpponentName(clientTwoOpponentName);
			msg.setOpponentSessionId(clientTwoSessionId);
			msg.setFirstTurnSessionId(firstTurnSessionId);
			simpMessagingTemplate.convertAndSendToUser(clientOneSessionId, "/game-init", msg);

			String clientOneOpponentName = manager.getClientsMapping().get(clientOneSessionId)[0].getGamerName();
			msg.setOpponentName(clientOneOpponentName);
			msg.setOpponentSessionId(clientOneSessionId);
			simpMessagingTemplate.convertAndSendToUser(clientTwoSessionId, "/game-init", msg);
		} else {
			msg.setMetadata("Game ID does not exist");
			simpMessagingTemplate.convertAndSendToUser(clientTwoSessionId, "/error", msg);
		}
	}

	@MessageMapping("/turn-info")
	public void selectRandomTurn(@Payload DataExchangeMessage msg, @Header("simpSessionId") String sessionId) {
		if (sessionService.sessionIdExists(sessionId)) {
			String peerOne = sessionId;
			String peerTwo = msg.getOpponentSessionId();
			String firstTurnSessionId = calculateFirstTurnSessionId(peerOne, peerTwo);
			msg.setFirstTurnSessionId(firstTurnSessionId);
			simpMessagingTemplate.convertAndSendToUser(peerOne, "/turn-info", msg);
			simpMessagingTemplate.convertAndSendToUser(peerTwo, "/turn-info", msg);
		}
	}

	private String calculateFirstTurnSessionId(String peerOne, String peerTwo) {
		String[] sessionIds = { peerOne, peerTwo };
		int random = (int) (Math.random() * 2);
		return sessionIds[random];
	}

	@MessageMapping("/game-result")
	public void handleGameResult(@Payload DataExchangeMessage msg) {
		String msgTo = msg.getOpponentSessionId();
		simpMessagingTemplate.convertAndSendToUser(msgTo, "/game-result", msg);
	}

	@MessageMapping("/game-reset")
	public void handleGameReset(@Payload DataExchangeMessage msg) { // apply DRY on these 3 methods
		String msgTo = msg.getOpponentSessionId();
		simpMessagingTemplate.convertAndSendToUser(msgTo, "/game-reset", msg);
	}

	@MessageMapping("/game")
	public void handleGameDataExchange(@Payload DataExchangeMessage msg, @Header("simpSessionId") String sessionId) {
		String msgTo = msg.getOpponentSessionId();
		simpMessagingTemplate.convertAndSendToUser(msgTo, "/game", msg);
	}

}
