package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RequestDispatcherImplTest {

    static final HttpRequest REQUEST = new HttpRequest(
            HttpMethod.GET, "/index.html", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);

    RequestHandler requestHandler;
    RequestHandlerFactory requestHandlerFactory;
    RequestDispatcherImpl requestDispatcher;

    @Before
    public void setUp() throws Exception {
        requestHandler = mock(RequestHandler.class);
        when(requestHandler.handleRequest(REQUEST)).thenReturn(HttpResponse.OK);
        requestHandlerFactory = mock(RequestHandlerFactory.class);
        when(requestHandlerFactory.getRequestHandler(REQUEST)).thenReturn(requestHandler);
        requestDispatcher = new RequestDispatcherImpl(requestHandlerFactory);
    }

    @Test
    public void shouldDispatchRequest() throws Exception {
        assertEquals(requestDispatcher.dispatch(REQUEST), HttpResponse.OK);
        verify(requestHandlerFactory).getRequestHandler(REQUEST);
        verify(requestHandler).handleRequest(REQUEST);
    }
}
