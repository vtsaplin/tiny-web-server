package com.tsaplin.webserver;

import java.net.Socket;

/**
 * Responsible for handling client connections.
 */
public interface ConnectionHandler {
    void handleConnection(Socket socket);
}
