package com.tsaplin.webserver;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.io.ByteStreams;
import net.lingala.zip4j.core.ZipFile;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executors;

public class WebServerIT {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void shouldServeStaticContent() throws Exception {

        Configuration configuration = new Configuration();
        configuration.setDocumentRoot(prepareContentAndReturnDocumentRoot());

        RequestTransformer requestTransformer = new RequestTransformerImpl(configuration);
        FileRequestHandler defaultRequestHandler = new FileRequestHandler(configuration);
        RequestHandlerFactory requestHandlerRegistry = new RequestHandlerFactoryImpl(defaultRequestHandler, configuration);
        RequestDispatcher requestDispatcher = new RequestDispatcherImpl(requestTransformer, requestHandlerRegistry);
        WebServer server = new WebServer(requestDispatcher, configuration);

        Executors.newSingleThreadExecutor().execute(() -> server.serve());
        Thread.sleep(500);

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://" + configuration.getAddress() + ":" + configuration.getPort());
            Assert.assertEquals("Test", page.getTitleText());
        }

        server.shutdown();
    }

    private String prepareContentAndReturnDocumentRoot() throws Exception {
        File zipFile = tempFolder.newFile("test1.zip");
        ByteStreams.copy(WebServerIT.class.getResourceAsStream("/test1.zip"), new FileOutputStream(zipFile));

        File rootDir = tempFolder.newFolder("webapp");
        new ZipFile(zipFile).extractAll(rootDir.getPath());

        return rootDir.getPath();
    }
}
