package com.example.sockets.SocketDemo.entity;

public class GameInitMessage extends GameMessage{

	private String gamerName;
	private String opponentName;
	private String moveChar;

	public String getGamerName() {
		return gamerName;
	}

	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
	}

	public String getOpponentName() {
		return opponentName;
	}

	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}
	
	
}
