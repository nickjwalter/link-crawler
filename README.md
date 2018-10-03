# Web Link Scraper

## Author
Nick Walter

## Scenario
The goal of the task is to build a simple web link scraper. The expected deliverable is a Java-based rest service with one endpoint (/links).

The endpoint must take as input a single http url and locate all the hyperlinks present on the page identified by the input url.

### Version
1.0.0

### Tech
* [Java 8] - Core language
* [Spring] - An application framework and inversion of control container for the Java platform.
* [Spring-Boot] - Deployment mechanism, we create fat jars bundled with all its dependencies
* [Jetty] - HTTP web server and servlet container
* [JSoup] - Open Source Java HTML Parser

### Installation
You will need to have Java 8 set up along with Gradle for this project

* ```gradle clean build``` - clean the environment, compile the code, runs the unit tests and build the Fat Jar for Deployment
* ```gradle bootRun``` - starts the Spring Boot app
* ```gradle clean build bootrun``` - runs all build targets and starts the application

### Usage
The service runs on port ```8080``` and exposes a single API ```/links```
http://localhost:8080/links

*Example Usage:*
http://localhost:8080/links?inputURL=http://www.bbc.co.uk/sport