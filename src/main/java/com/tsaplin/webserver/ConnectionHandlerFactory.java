package com.tsaplin.webserver;

/**
 * Responsible for creating connection handlers.
 */
public interface ConnectionHandlerFactory {
    ConnectionHandler getConnectionHandler();
}
