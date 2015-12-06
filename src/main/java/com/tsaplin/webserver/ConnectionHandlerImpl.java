package com.tsaplin.webserver;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandlerImpl implements ConnectionHandler {

    private static Logger logger = LoggerFactory.getLogger(ConnectionHandlerImpl.class);

    private final RequestDispatcher requestDispatcher;
    private final RequestParser requestParser;
    private final Configuration configuration;

    public ConnectionHandlerImpl(RequestDispatcher requestDispatcher, Configuration configuration) {
        this.requestDispatcher = requestDispatcher;
        this.configuration = configuration;
        this.requestParser = newRequestParser();
    }

    protected RequestParser newRequestParser() {
        return new RequestParser(configuration);
    }

    @Override
    public void handleConnection(Socket socket) {
        logger.info("Handling new connection from " + socket.getInetAddress());

        try {
            socket.setSoTimeout(configuration.getConnectionTimeout());
            while (true) {
                HttpRequest request = requestParser.parse(socket.getInputStream());
                requestDispatcher.dispatch(request).write(socket.getOutputStream());
                if (!isConnectionPersistent(request.getVersion())) {
                    logger.info("Client does not support persistent connections");
                    break;
                }
            }
        }
        catch (SocketTimeoutException e) {
            logger.info("Connection timeout expired");
        }
        catch(Exception e) {
            logger.error("Error while handling connection", e);
        }
        finally {
            logger.info("Closing connection...");
            try {
                socket.close();
            } catch (IOException e) {
                logger.warn("Exception thrown while closing Socket", e);
            }
        }
    }

    public static boolean isConnectionPersistent(HttpProtocolVersion protocolVersion) {
        return protocolVersion.compareTo(HttpProtocolVersion.HTTP_1_1) >= 0;
    }
}
