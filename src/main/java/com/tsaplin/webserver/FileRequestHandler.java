package com.tsaplin.webserver;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles static file requests.
 */
public class FileRequestHandler implements RequestHandler {

    private static Logger logger = LoggerFactory.getLogger(FileRequestHandler.class);

    private final Configuration configuration;

    public FileRequestHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) throws Exception {
        File file = Paths.get(configuration.getDocumentRoot() + request.getUrl()).normalize().toAbsolutePath().toFile();
        logger.info("Serving file " + file);
        if (file.exists()) {

            ImmutableMap.Builder headers = ImmutableMap.builder();
            headers.put("Content-Type", Files.probeContentType(Paths.get(file.getPath())));
            headers.put("Content-Length", Long.toString(file.length()));

            return new HttpResponse(HttpResponseStatus.OK, headers.build()) {
                @Override
                public void writeContent(OutputStream os) throws IOException {
                    writeFileContent(file, os);
                }
            };
        }
        logger.info("File not found " + file);
        return HttpResponse.NOT_FOUND;
    }

    protected void writeFileContent(File file, OutputStream os) throws IOException {
        ByteStreams.copy(new FileInputStream(file), os);
    }
}
