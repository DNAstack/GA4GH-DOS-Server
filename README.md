# GA4GH-DOS-Server

Global Alliance for Genomics and Health (GA4GH) is an international, nonprofit alliance formed to accelerate the potential of research and medicine to advance human health. They have developed the Data Object Service (DOS), which is an emerging standard for specifying location of data across different cloud environments. This is an implementation of a DOS Server, which hosts and allows the discovery of data objects. The GA4GH specification of the DOS Server API is found [here](https://ga4gh.github.io/data-object-service-schemas/#/).

## Table of Contents
* [Self-Contained Build With Docker](#self-contained-build-with-docker)
* [Developer Setup](#developer-setup)
  * [Dependency Checklist](#dependency-checklist)
  * [PostgreSQL Set Up](#postgresql-set-up)
  * [Unit/Integration Tests](#unitintegration-tests)
* [Usage](#usage)
  * [Command Line](#command-line)
  * [Environment Variables](#environment-variables)
* [Applications of the DOS Server](#applications-of-the-dos-server)

## Self-Contained Build With Docker

If you just want to compile and run this service, you can bypass most local setup by using the same script
we use for building the app in our CI/CD system.

```
$ ci/build-docker-image dos-server:$(git describe) dos-server $(git describe)
``` 

The one requirement is that you have a PostgreSQL database available on `localhost:5432`. See
[setup instructions](#postgresql-setup) below.

Then run the DOS server with:

```
docker run -ti --rm \
  --env 'SPRING_DATASOURCE_URL=jdbc:postgresql://localhost/dos' \
  -p 8101:8101 \
  dos-server:$(git describe)
```

## Developer Setup

### Dependency Checklist 

* Maven 3.x (or use included Maven Wrapper)
* Java 11
* PostgreSQL 9.x running on localhost:5432

### PostgreSQL Setup

* Make a user named "dos" with a password "dos"
* Make a database called "dos"
* grant all privileges


**Step by Step**

In your shell (eg. bash, zsh) execute:

```
$ createuser -d -U postgres dos
$ createdb -U dos dos
```

### Use stronger username or password

As shipped, the server has one user called `dosadmin` with the `admin` role.

To set a different username, set the `SECURITY_USER_NAME` environment variable.
To set a different password, set the `SECURITY_USER_PASSWORD` environment variable.


### Unit/Integration Tests

First, make sure you are running Java 11
```
$ java -version
openjdk version "11.0.1" 2018-10-16
OpenJDK Runtime Environment 18.9 (build 11.0.1+13)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.1+13, mixed mode)
```

To run the unit/integration tests
```

$ ./mvnw test

...

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

## Usage

### Command Line
Once the above has been completed, simply execute:

```

$ mvn clean package
$ mvn spring-boot:run

```

The DOS Server should be running on http://localhost:8101/

For details on the api topology and how to use to DOS Server, refer to the [GA4GH swaggerhub specification](https://ga4gh.github.io/data-object-service-schemas/#/).

### Environment Variables

The server can be configured at runtime using environment variables. The following lists some of the available
variables and their defaults:

```
SERVER_CONTEXTPATH=/
SERVER_PORT=8101
DB_DATABASE=dos
DB_USERNAME=dos
DB_PASSWORD=dos
SECURITY_USER_NAME=dosadmin
SECURITY_USER_PASSWORD=dosadmin
SECURITY_USER_ROLE=admin
```

All values specified in [application.yml](src/main/resources/application.yml) can be set via environment variables.
See the [Spring Boot External Configuration Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html) for details.

Below is an example of how to run the dos server with the environment variables specified

```

$ CONTEXT_PATH=/user1 SERVER_PORT=9090 ./mvnw spring-boot:run

```

## Applications of the DOS Server

Eric Keilty created 2 applications that use this implementaiton of a Dos Server.

1. [PGP Wrapper](https://github.com/ekeilty17/DOS-Server-PGP-Wrapper) loads data from [PGP Canada](https://personalgenomes.ca/data) into a DOS Server database.
2. [GCP Wrapper](https://github.com/ekeilty17/DOS-Server-GCP-Wrapper) loads data from a public [GCP Bucket](https://cloud.google.com/storage/docs/json_api/v1/buckets) into a DOS Server database.
