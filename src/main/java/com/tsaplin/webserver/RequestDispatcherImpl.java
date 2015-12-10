package com.tsaplin.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestDispatcherImpl implements RequestDispatcher {

    private static Logger logger = LoggerFactory.getLogger(RequestDispatcherImpl.class);

    private final RequestTransformer requestTransformer;
    private final RequestHandlerFactory requestHandlerFactory;

    public RequestDispatcherImpl(RequestTransformer requestTransformer, RequestHandlerFactory requestHandlerFactory) {
        this.requestTransformer = requestTransformer;
        this.requestHandlerFactory = requestHandlerFactory;
    }

    @Override
    public HttpResponse dispatch(HttpRequest request) {
        logger.info("Handling request " + request);
        try {
            HttpRequest transformedRequest = requestTransformer.transform(request);
            return requestHandlerFactory.getRequestHandler(transformedRequest).handleRequest(transformedRequest);
        } catch (Exception e) {
            logger.error("Error while dispatching request", e);
            return HttpResponse.INTERNAL_SERVER_ERROR;
        }
    }
}
