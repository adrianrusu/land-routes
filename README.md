# LandRoutes Service
[![Build Status](https://app.travis-ci.com/adrianrusu/land-routes.svg?branch=master)](https://app.travis-ci.com/adrianrusu/land-routes)
[![CircleCI](https://circleci.com/gh/adrianrusu/land-routes.svg?style=shield)](https://circleci.com/gh/adrianrusu/land-routes)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=adrianrusu_land-routes&metric=alert_status)](https://sonarcloud.io/dashboard?id=adrianrusu_land-routes)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=adrianrusu_land-routes&metric=coverage)](https://sonarcloud.io/dashboard?id=adrianrusu_land-routes)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=adrianrusu_land-routes&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=adrianrusu_land-routes)

This is a simple Spring Boot project that is able to calculate any possible land route from one country to another
using JSON parsed data from [mledoze/countries](https://github.com/mledoze/countries) loaded at start-time. For every
valid request Breadth First Search is used to find the shortest path (the lowest number of other countries) needed to
cross when travelling from source to the destination country.

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method
in the `com/adrianr/landroutes/LandRoutesApplication.java` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Building application artefact

The easiest way to build this application is to use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn clean install
```

This will create:

* An JAR called "land-routes-0.0.1-SNAPSHOT"

If you want to access run the artefact resulted from the build process, you can use the following command:

```shell
java -jar land-routes-0.0.1-SNAPSHOT.jar
```

## Usage

The application starts up and uses port 8080, in order to use it to check the distance between two different countries
it exposes an endpoint at:

```
http://localhost:8080/routing/{source}/{destination}
```

Replace the following parameters with country codes in [ISO 3166-1 alpha-3](https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3) format.
- `source`
- `destination`

Example:
```
http://localhost:8080/routing/CZE/ITA
```

Will respond with

<table>
<tr><th>Status Code</th><th>Description</th><th>Message</th></tr>
<tr>
<td>200</td>
<td>OK</td>
<td>

```json
{
  "route": [ "CZE", "AUT", "ITA" ]
}
```
</td>
</tr>
</table>


### Exception handling

If any of the country codes are invalid or there is no land path between the two countries then the response status for
the request will:.

<table>
<tr><th>Status Code</th><th>Description</th><th>Message</th></tr>
<tr>
<td>404</td>
<td>NOT FOUND</td>
<td>

```json
{
  "timestamp": "2021-11-06T10:11:32.114+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/routing/ROU/CZEC"
}
```
</td>
</tr>
</table>