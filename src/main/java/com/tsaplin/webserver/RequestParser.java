package com.tsaplin.webserver;

import com.google.common.base.Optional;

import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public Optional<HttpRequest> parse(InputStream is) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(is);
        RequestBuilder requestBuilder = new RequestBuilder();

        // request line
        String requestLine = readLine(bis);
        if (requestLine == null) {
            return Optional.absent();
        }
        String requestLineParts[] = requestLine.split("\\s+");
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
        String headerLine = readLine(bis);
        while(headerLine != null && !headerLine.isEmpty()) {
            int colonIndex = headerLine.indexOf(':');
            checkState(colonIndex != -1);
            headers.put(headerLine.substring(0, colonIndex).trim(), headerLine.substring(colonIndex + 1).trim());
            headerLine = readLine(bis);
        }

        return Optional.of(requestBuilder.setHeaders(headers).setContent(readContent(headers, bis)).build());
    }

    private @Nullable String readLine(BufferedInputStream bis) throws IOException {
        StringBuilder sb = new StringBuilder();
        while(true) {
            int ch = bis.read();
            if (ch == '\r') {
                checkState((char) bis.read() == '\n');
                return sb.toString();
            } else if (ch == -1) {
                return null;
            } else {
                sb.append((char) ch);
            }
        }
    }

    private byte[] readContent(Map<String, String> headers, BufferedInputStream bis) throws IOException {
        if (headers.containsKey(CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
            byte[] content = new byte[contentLength];
            bis.read(content);
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
