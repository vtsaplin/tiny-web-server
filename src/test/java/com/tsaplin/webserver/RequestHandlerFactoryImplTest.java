package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerFactoryImplTest {

    static final HttpRequest REQUEST = new HttpRequest(
            HttpMethod.GET, "/index.html", "", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);

    static final RequestHandler REQUEST_HANDLER = mock(RequestHandler.class);

    RequestHandler defaultRequestHandler;
    RequestHandlerFactoryImpl requestHandlerFactory;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration();
        defaultRequestHandler = mock(RequestHandler.class);
        when(defaultRequestHandler.handleRequest(REQUEST)).thenReturn(HttpResponse.OK);
        requestHandlerFactory = new RequestHandlerFactoryImpl(defaultRequestHandler, configuration);
    }

    @Test
    public void shouldAddRequestHandlerWithPattern() throws Exception {
        requestHandlerFactory.addRequestHandler("index", REQUEST_HANDLER);
        assertEquals(requestHandlerFactory.getRequestHandler(REQUEST), REQUEST_HANDLER);
    }

    @Test
    public void shouldAddRequestHandlerWithFileExtensions() throws Exception {
        requestHandlerFactory.addRequestHandler(new String[] {"html"}, REQUEST_HANDLER);
        assertEquals(requestHandlerFactory.getRequestHandler(REQUEST), REQUEST_HANDLER);
    }

    @Test
    public void shouldReturnDefaultRequestHandler() throws Exception {
        assertEquals(defaultRequestHandler, requestHandlerFactory.getRequestHandler(REQUEST));
    }
}
