Tiny Web Server
===============

A simple multithreaded web server implemented purely Java.
 
Features
--------
* simple
* multithreaded
* embeddable
* configurable
* extensible
* supports persisted connections

Getting started
---------------
1. Clone the project
2. Execute ./gradlew distZip
3. Unpack zip file {project}/build/distributions/tiny-web-server-{version}.zip
4. Set environment variable WEBSERVER_HOME to point to the location where you just extracted files
5. Edit server's configuration file located in {server_home}/config.xml
6. Run {server_home}/bin/tiny-web-server

Tests
=====
To execute tests run ./gradlew test or ./gradlew integrationTest   
