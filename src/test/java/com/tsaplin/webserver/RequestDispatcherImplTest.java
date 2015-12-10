package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RequestDispatcherImplTest {

    static final HttpRequest REQUEST = new HttpRequest(
            HttpMethod.GET, "/index.html", "", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);

    RequestTransformer requestTransformer;
    RequestHandler requestHandler;
    RequestHandlerFactory requestHandlerFactory;
    RequestDispatcherImpl requestDispatcher;

    @Before
    public void setUp() throws Exception {
        requestTransformer = mock(RequestTransformer.class);
        when(requestTransformer.transform(REQUEST)).thenReturn(REQUEST);
        requestHandler = mock(RequestHandler.class);
        when(requestHandler.handleRequest(REQUEST)).thenReturn(HttpResponse.OK);
        requestHandlerFactory = mock(RequestHandlerFactory.class);
        when(requestHandlerFactory.getRequestHandler(REQUEST)).thenReturn(requestHandler);
        requestDispatcher = new RequestDispatcherImpl(requestTransformer, requestHandlerFactory);
    }

    @Test
    public void shouldDispatchRequest() throws Exception {
        assertEquals(HttpResponse.OK, requestDispatcher.dispatch(REQUEST));
        verify(requestTransformer).transform(REQUEST);
        verify(requestHandler).handleRequest(REQUEST);
        verify(requestHandlerFactory).getRequestHandler(REQUEST);
    }
}
