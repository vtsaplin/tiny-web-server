package com.tsaplin.webserver;

/**
 * Responsible for creating request handlers.
 */
public interface RequestHandlerFactory {
    RequestHandler getRequestHandler(HttpRequest request);
}
