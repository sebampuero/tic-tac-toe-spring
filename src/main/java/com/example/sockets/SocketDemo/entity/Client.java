package com.example.sockets.SocketDemo.entity;

public class Client {
    
    private String gamerName;
    private String sessionId;

    public Client() {

    }

    public Client(String sessionId) {
        this.sessionId = sessionId;
    }

    public Client(String sessionId, String gamerName) {
        this.sessionId = sessionId;
        this.gamerName = gamerName;
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}