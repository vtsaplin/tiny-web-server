package com.tsaplin.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Represents HTTP request.
 */
public class HttpRequest {

    private final HttpMethod method;
    private final String url;
    private final String query;
    private final HttpProtocolVersion version;
    private final Map<String, String> headers;
    private final byte[] content;

    public HttpRequest(
            HttpMethod method,
            String url,
            String query,
            HttpProtocolVersion version,
            Map<String, String> headers,
            byte[] content) {

        this.method = method;
        this.url = url;
        this.query = query;
        this.version = version;
        this.headers = ImmutableMap.copyOf(headers);
        this.content = content;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getQuery() {
        return query;
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
                Objects.equals(url, that.url) &&
                Objects.equals(query, that.query) &&
                Objects.equals(version, that.version) &&
                Objects.equals(headers, that.headers) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url, query, version, headers, content);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("method", method)
                .add("url", url)
                .add("query", query)
                .add("version", version)
                .add("headers", headers)
                .add("content", content)
                .toString();
    }
}
