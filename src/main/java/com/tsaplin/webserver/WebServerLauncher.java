package com.tsaplin.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts web server with default behaviour.
 */
public class WebServerLauncher {

    private static Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);

    private static final String HOME_DIR_ENV = "WEBSERVER_HOME";
    private static final String CONFIG_FILE = "config.xml";

    public static void main(String[] args) {
        try {
            String homeDirEnv = System.getenv(HOME_DIR_ENV);
            if (homeDirEnv == null) {
                logger.error("Environment variable " + HOME_DIR_ENV + " is not set");
                return;
            }
            Path homeDir = Paths.get(homeDirEnv);
            logger.info("Home location: " + homeDir);

            File configFile = homeDir.resolve(CONFIG_FILE).toFile();
            Configuration configuration = Configuration.load(new FileInputStream(configFile));
            if (!configuration.getDocumentRoot().startsWith("/")) {
                configuration.setDocumentRoot(homeDir.resolve(configuration.getDocumentRoot()).toString());
            }

            RequestHandlerFactory requestHandlerRegistry = new RequestHandlerFactoryImpl(configuration);
            RequestDispatcher requestDispatcher = new RequestDispatcherImpl(requestHandlerRegistry);
            new WebServer(requestDispatcher, configuration).serve();

        } catch (Exception e) {
            logger.error("Error", e);
        }
    }
}
