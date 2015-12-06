package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import java.io.ByteArrayInputStream;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestParserTest {

    static final String ORIGINAL_REQUEST = "GET /index.html HTTP/1.1\r\n";

    static final HttpRequest PARSED_REQUEST = new HttpRequest(
            HttpMethod.GET, "/index.html", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);

    RequestParser requestParser = new RequestParser(new Configuration());

    @Test
    public void shouldParseRequest() throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(ORIGINAL_REQUEST.getBytes());
        assertEquals(requestParser.parse(bais), PARSED_REQUEST);
    }
}
