package com.tsaplin.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDispatcherImpl implements RequestDispatcher {

    private static Logger logger = LoggerFactory.getLogger(RequestDispatcherImpl.class);

    private final RequestHandlerFactory requestHandlerFactory;

    public RequestDispatcherImpl(RequestHandlerFactory requestHandlerFactory) {
        this.requestHandlerFactory = requestHandlerFactory;
    }

    @Override
    public HttpResponse dispatch(HttpRequest request) {
        logger.info("Handling request " + request);
        try {
            return requestHandlerFactory.getRequestHandler(request).handleRequest(request);
        } catch (Exception e) {
            return HttpResponse.INTERNAL_SERVER_ERROR;
        }
    }
}
