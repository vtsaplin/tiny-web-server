package com.tsaplin.webserver;

import com.google.common.base.MoreObjects;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Contains configuration properties.
 */
@XmlRootElement(name="webserver")
public class Configuration {

    private String documentRoot;
    private String welcomeFile = "index.html";
    private String address = "localhost";
    private int port = 8080;
    private int connectionTimeout = 5000;

    public String getDocumentRoot() {
        return documentRoot;
    }

    public void setDocumentRoot(String documentRoot) {
        this.documentRoot = documentRoot;
    }

    public String getWelcomeFile() {
        return welcomeFile;
    }

    public void setWelcomeFile(String welcomeFile) {
        this.welcomeFile = welcomeFile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public static Configuration load(InputStream is) {
        try {
            JAXBContext context = JAXBContext.newInstance(Configuration.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Configuration) unmarshaller.unmarshal(is);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading configuration", e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("documentRoot", documentRoot)
                .add("welcomeFile", welcomeFile)
                .add("address", address)
                .add("port", port)
                .add("connectionTimeout", connectionTimeout)
                .toString();
    }
}
