package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestTransformerImplTest {

    static final String WELCOME_FILE = "index.html";

    static final HttpRequest REQUEST = new HttpRequest(
            HttpMethod.GET, "/", "", HttpProtocolVersion.HTTP_1_1, ImmutableMap.of(), new byte[0]);

    Configuration configuration = new Configuration();
    RequestTransformerImpl requestTransformer;

    @Before
    public void setUp() throws Exception {
        configuration = new Configuration();
        configuration.setWelcomeFile(WELCOME_FILE);
        requestTransformer = new RequestTransformerImpl(configuration);
    }

    @Test
    public void shouldReplaceEmptyPathWithWelcomeFile() throws Exception {
        assertEquals(requestTransformer.transform(REQUEST).getUrl(), REQUEST.getUrl() + WELCOME_FILE);
    }
}
