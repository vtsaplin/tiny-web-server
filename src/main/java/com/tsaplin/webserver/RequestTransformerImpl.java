package com.tsaplin.webserver;

/**
 * Default implementation of <code>RequestTransformer</code>.
 */
public class RequestTransformerImpl implements RequestTransformer {

    private final Configuration configuration;

    public RequestTransformerImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public HttpRequest transform(HttpRequest request) {
        if (request.getUrl().endsWith("/")) {
            return new RequestBuilder(request).setUrl(request.getUrl() + configuration.getWelcomeFile()).build();
        }
        return request;
    }
}
