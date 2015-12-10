package com.tsaplin.webserver;

/**
 * Responsible for transforming HTTP requests (i.e. URL rewriting).
 */
public interface RequestTransformer {
    HttpRequest transform(HttpRequest request);
}
