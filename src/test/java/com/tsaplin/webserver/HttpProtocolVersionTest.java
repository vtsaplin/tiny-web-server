package com.tsaplin.webserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpProtocolVersionTest {

    @Test
    public void shouldReturnStringRepresentation() throws Exception {
        assertEquals(HttpProtocolVersion.HTTP_1_1.asVersionString(), "HTTP/1.1");
    }

    @Test
    public void shouldCompareVersions() throws Exception {
        assertTrue(HttpProtocolVersion.HTTP_1_1.compareTo(HttpProtocolVersion.HTTP_1_0) > 0);
    }

    @Test
    public void shouldParseFromString() throws Exception {
        assertEquals(HttpProtocolVersion.parse("HTTP/1.1"), HttpProtocolVersion.HTTP_1_1);
    }
}
