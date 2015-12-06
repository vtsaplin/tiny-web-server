package com.tsaplin.webserver;

/**
 * Responsible for dispatching HTTP request.
 */
public interface RequestDispatcher {
    HttpResponse dispatch(HttpRequest request);
}
