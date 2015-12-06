package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestBuilderTest {

    @Test
    public void shouldBuildRequest() throws Exception {

        HttpRequest originalRequest = new HttpRequest(
                HttpMethod.GET,
                "/index.html",
                "value=1&value=2",
                HttpProtocolVersion.HTTP_1_1,
                ImmutableMap.of("Accept", "text/plain"),
                new byte[0]);

        HttpRequest request = new RequestBuilder()
                .setMethod(HttpMethod.GET)
                .setUrl("/index.html")
                .setQuery("value=1&value=2")
                .setVersion(HttpProtocolVersion.HTTP_1_1)
                .setHeaders(ImmutableMap.of("Accept", "text/plain"))
                .setContent(new byte[0])
                .build();

        assertEquals(originalRequest, request);
    }
}
