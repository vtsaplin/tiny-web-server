package com.tsaplin.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.google.common.base.Preconditions.checkState;

/**
 * Represents a web server.
 */
public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private final Configuration configuration;
    private final ConnectionHandlerFactory connectionHandlerFactory;
    private final Executor executor;

    private AtomicBoolean running = new AtomicBoolean(false);

    public WebServer(RequestDispatcher requestDispatcher, Configuration configuration) {
        this.configuration = configuration;
        this.connectionHandlerFactory = newConnectionHandlerFactory(requestDispatcher);
        this.executor = newExecutor();
    }

    /**
     * Starts the server.
     */
    public void serve() {
        checkState(running.compareAndSet(false, true));

        logger.info("Starting web server with configuration " + configuration);
        try {
            ServerSocket serverSocket = newServerSocket();
            while (running.get()) {
                try {
                    Socket socket = serverSocket.accept();
                    ConnectionHandler connectionHandler = connectionHandlerFactory.getConnectionHandler();
                    executor.execute(() -> connectionHandler.handleConnection(socket));
                } catch (Exception e) {
                    logger.error("Error while accepting client connection", e);
                }
            }
            serverSocket.close();
        } catch (Exception e) {
            logger.error("Error while handling client requests. Exiting...", e);
        }
    }

    /**
     * Stops the server.
     */
    public void shutdown() {
        checkState(running.compareAndSet(true, false));
    }

    public boolean isRunning() {
        return running.get();
    }

    /**
     * Creates default connection factory.
     *
     * Note: this factory method is called from constructor.
     *
     * @param requestDispatcher dispatcher
     * @return new connection factory
     */
    protected ConnectionHandlerFactory newConnectionHandlerFactory(RequestDispatcher requestDispatcher) {
        return new ConnectionHandlerFactory() {
            @Override
            public ConnectionHandler getConnectionHandler() {
                return new ConnectionHandlerImpl(requestDispatcher, configuration);
            }
        };
    }



    /**
     * Creates default executor.
     *
     * Note: this factory method is called from constructor.
     *
     * @return new executor
     */
    protected Executor newExecutor() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    /**
     * Creates <code>ServerSocket</code> that will be used by this server
     *
     * @return a socket
     * @throws Exception
     */
    protected ServerSocket newServerSocket() throws Exception {
        return new ServerSocket(configuration.getPort(), 1, InetAddress.getByName(configuration.getAddress()));
    }
}
