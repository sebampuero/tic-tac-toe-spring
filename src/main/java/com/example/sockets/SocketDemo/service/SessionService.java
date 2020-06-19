package com.example.sockets.SocketDemo.service;

import com.example.sockets.SocketDemo.dao.SessionDAO;
import com.example.sockets.SocketDemo.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    SessionDAO sessionDao;

    public boolean addClient(Client clientOne) {
        return sessionDao.addClient(clientOne);
    }

    public boolean addClient(Client clientTwo, String clientOneSessionId) {
        return sessionDao.addClient(clientTwo, clientOneSessionId);
    }

    public boolean sessionIdExists(String sessionId) {
        return sessionDao.sessionIdExists(sessionId);
    }

}