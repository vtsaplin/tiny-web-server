package com.tsaplin.webserver;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Responsible for building immutable requests.
 */
public class RequestBuilder {

    private HttpMethod method;
    private String url;
    private String query;
    private HttpProtocolVersion version;
    private Map<String, String> headers;
    private byte[] content;

    public RequestBuilder() {
    }

    public RequestBuilder(HttpRequest request) {
        this.method = request.getMethod();
        this.url = request.getUrl();
        this.query = request.getQuery();
        this.version = request.getVersion();
        this.headers = request.getHeaders();
        this.content = request.getContent();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public RequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public RequestBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public HttpProtocolVersion getVersion() {
        return version;
    }

    public RequestBuilder setVersion(HttpProtocolVersion version) {
        this.version = version;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public RequestBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public byte[] getContent() {
        return content;
    }

    public RequestBuilder setContent(byte[] content) {
        this.content = content;
        return this;
    }

    public HttpRequest build() {
        return new HttpRequest(
                checkNotNull(method),
                checkNotNull(url),
                checkNotNull(query),
                checkNotNull(version),
                checkNotNull(headers),
                checkNotNull(content));
    }
}
