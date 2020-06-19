package com.example.sockets.SocketDemo.entity;

public class GameMessage {
	protected String metadata;
	protected String senderSessionId;
	protected String opponentSessionId;
	protected String firstTurnSessionId;

	public String getMetadata() {
		return this.metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getSenderSessionId() {
		return senderSessionId;
	}

	public void setSenderSessionId(String senderSessionId) {
		this.senderSessionId = senderSessionId;
	}

	public String getOpponentSessionId() {
		return opponentSessionId;
	}

	public void setOpponentSessionId(String opponentSessionId) {
		this.opponentSessionId = opponentSessionId;
	}

	public String getFirstTurnSessionId() {
		return firstTurnSessionId;
	}

	public void setFirstTurnSessionId(String firstTurnSessionId) {
		this.firstTurnSessionId = firstTurnSessionId;
	}

}
