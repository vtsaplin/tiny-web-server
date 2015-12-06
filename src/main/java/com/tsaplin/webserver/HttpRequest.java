package com.tsaplin.webserver;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import java.util.Map;

/**
 * Represents HTTP request.
 */
public class HttpRequest {

    private HttpMethod method;
    private String url;
    private HttpProtocolVersion version;

    private Map<String, String> headers;

    private final byte[] content;

    public HttpRequest(HttpMethod method, String url, HttpProtocolVersion version, Map<String, String> headers, byte[] content) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.headers = headers;
        this.content = content;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HttpProtocolVersion getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequest that = (HttpRequest) o;
        return method == that.method &&
                java.util.Objects.equals(url, that.url) &&
                java.util.Objects.equals(version, that.version) &&
                java.util.Objects.equals(headers, that.headers) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(method, url, version, headers, content);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("method", method)
                .add("url", url)
                .add("version", version)
                .add("headers", headers)
                .toString();
    }
}
