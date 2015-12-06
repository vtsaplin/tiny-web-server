package com.tsaplin.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Maps.newHashMap;

/**
 * Parses HTTP request.
 */
public class RequestParser {

    private static final String CONTENT_LENGTH = "Content-Length";

    private final Configuration configuration;

    public RequestParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public HttpRequest parse(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        RequestBuilder requestBuilder = new RequestBuilder();

        // request line
        String requestLineParts[] = reader.readLine().split("\\s+");
        checkState(requestLineParts.length == 3);

        // method
        requestBuilder.setMethod(HttpMethod.valueOf(requestLineParts[0]));

        // url & query string
        String[] urlParts = requestLineParts[1].split("\\?");
        requestBuilder.setUrl(resolveUrl(urlParts[0]));
        requestBuilder.setQuery(urlParts.length > 1 ? urlParts[1] : "");

        // protocol version
        requestBuilder.setVersion(HttpProtocolVersion.parse(requestLineParts[2]));

        // headers
        Map<String, String> headers = newHashMap();
        String headerLine = reader.readLine();
        while(headerLine != null && !headerLine.isEmpty()) {
            int colonIndex = headerLine.indexOf(':');
            checkState(colonIndex != -1);
            headers.put(headerLine.substring(0, colonIndex).trim(), headerLine.substring(colonIndex + 1).trim());
            headerLine = reader.readLine();
        }

        return requestBuilder.setHeaders(headers).setContent(readContent(headers, is)).build();
    }

    private byte[] readContent(Map<String, String> headers, InputStream is) throws IOException {
        if (headers.containsKey(CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
            byte[] content = new byte[contentLength];
            is.read(content);
            return content;
        }
        return new byte[0];
    }

    protected String resolveUrl(String url) {
        if (url.endsWith("/")) {
            return url + configuration.getWelcomeFile();
        }
        return url;
    }
}
