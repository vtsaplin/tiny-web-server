package com.tsaplin.webserver;

/**
 * Responsible for handling HTTP request.
 */
public interface RequestHandler {
    HttpResponse handleRequest(HttpRequest request) throws Exception;
}
