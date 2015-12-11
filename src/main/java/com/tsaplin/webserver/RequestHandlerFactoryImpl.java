package com.tsaplin.webserver;

import com.google.common.base.Joiner;
import java.util.Map;
import java.util.regex.Pattern;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Returns request handler for a given URL.
 */
public class RequestHandlerFactoryImpl implements RequestHandlerFactory {

    private final Map<Pattern, RequestHandler> handlers = newHashMap();

    private final Configuration configuration;
    private final RequestHandler defaultRequestHandler;

    public RequestHandlerFactoryImpl(RequestHandler defaultRequestHandler, Configuration configuration) {
        this.configuration = configuration;
        this.defaultRequestHandler = defaultRequestHandler;
    }

    public void addRequestHandler(String pattern, RequestHandler handler) {
        handlers.put(Pattern.compile(pattern), handler);
    }

    public void addRequestHandler(String[] fileExtensions, RequestHandler handler) {
        addRequestHandler("([^\\s]+(\\.(?i)(" + Joiner.on('|').join(fileExtensions) + "))$)", handler);
    }

    @Override
    public RequestHandler getRequestHandler(HttpRequest request) {
        for (Map.Entry<Pattern, RequestHandler> entry : handlers.entrySet()) {
            if (entry.getKey().matcher(request.getUrl()).find()) {
                return entry.getValue();
            }
        }
        return  defaultRequestHandler;
    }
}
