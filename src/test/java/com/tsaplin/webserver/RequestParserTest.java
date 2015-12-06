package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import java.io.ByteArrayInputStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    RequestParser requestParser = new RequestParser(new Configuration());

    @Test
    public void shouldParseRequestWithoutQueryParameters() throws Exception {
        String requestString = "GET /index.html HTTP/1.1\r\n";
        HttpRequest request = new HttpRequest(
                HttpMethod.GET, "/index.html", "", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);
        ByteArrayInputStream bais = new ByteArrayInputStream(requestString.getBytes());
        assertEquals(requestParser.parse(bais), request);
    }

    @Test
    public void shouldParseRequestWithQueryParameters() throws Exception {
        String requestString = "GET /index.html?value=1&value=2 HTTP/1.1\r\n";
        HttpRequest request = new HttpRequest(
                HttpMethod.GET, "/index.html", "value=1&value=2", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);
        ByteArrayInputStream bais = new ByteArrayInputStream(requestString.getBytes());
        assertEquals(requestParser.parse(bais), request);
    }
}
