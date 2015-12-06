package com.tsaplin.webserver;

/**
 * Represents HTTP response status.
 */
public enum HttpResponseStatus {

    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    public final int statusCode;
    public final String statusText;

    private HttpResponseStatus(int statusCode, String statusText) {
        this.statusCode = statusCode;
        this.statusText = statusText;
    }
}
