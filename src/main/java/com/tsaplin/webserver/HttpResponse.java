package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Represents HTTP response.
 */
public class HttpResponse {

    public static final HttpResponse OK = new HttpResponse(HttpResponseStatus.OK);
    public static final HttpResponse NOT_FOUND = new HttpResponse(HttpResponseStatus.NOT_FOUND);
    public static final HttpResponse INTERNAL_SERVER_ERROR = new HttpResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR);

    private final HttpResponseStatus status;

    private final Map<String, String> headers;

    public HttpResponse(HttpResponseStatus status) {
        this(status, ImmutableMap.of());
    }

    public HttpResponse(HttpResponseStatus status, Map<String, String> headers) {
        this.status = status;
        this.headers = headers;
    }

    public void write(OutputStream os) throws IOException {
        writeStatusLineAndHeaders(os);
        writeContent(os);
    }

    protected void writeStatusLineAndHeaders(OutputStream os) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

        writer.write(HttpProtocolVersion.HTTP_1_1.asVersionString());
        writer.write(" ");
        writer.write(Integer.toString(status.statusCode));
        writer.write(" ");
        writer.write(status.statusText);
        writer.write("\r\n");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            writer.write(entry.getKey());
            writer.write(":");
            writer.write(entry.getValue());
            writer.write("\r\n");
        }
        writer.write("\r\n");

        writer.flush();
    }

    public void writeContent(OutputStream os) throws IOException {}
}
