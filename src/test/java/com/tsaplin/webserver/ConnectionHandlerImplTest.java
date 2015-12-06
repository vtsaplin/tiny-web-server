package com.tsaplin.webserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConnectionHandlerImplTest {

    ConnectionHandlerImpl connectionHandler;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration();
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(requestDispatcher.dispatch(any(HttpRequest.class))).thenReturn(HttpResponse.OK);
        connectionHandler = new ConnectionHandlerImpl(requestDispatcher, configuration);
    }

    @Test
    public void souldCreateNewRequestParser() throws Exception {
        assertNotNull(connectionHandler.newRequestParser());
    }

    @Test
    public void souldHandleConnection() throws Exception {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream bais = new ByteArrayInputStream("GET /index.html HTTP/1.0\r\n".getBytes());
        when(socket.getInputStream()).thenReturn(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(baos);
        connectionHandler.handleConnection(socket);
        assertTrue(baos.toString(StandardCharsets.UTF_8.name()).contains("200"));
    }

    @Test
    public void souldCheckIfConnectionPersistent() throws Exception {
        assertFalse(connectionHandler.isConnectionPersistent(HttpProtocolVersion.HTTP_1_0));
        assertTrue(connectionHandler.isConnectionPersistent(HttpProtocolVersion.HTTP_1_1));
    }
}
