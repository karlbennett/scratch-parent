scratch-spring-mustache
==============

A simple Spring Boot webapp that uses [Mustache](https://github.com/spullara/mustache.java) as it's templating language.

#### Usage

Before running this webapp you must make sure that one of the `scratch-*-rest` webapps are running on `localhost`. A
good example is [`scratch-spring-rest`](https://github.com/karlbennett/scratch-spring-rest).

Then you can either start the webapp with:

    mvn spring-boot:run

Or build the project:

    mvn clean verify

Then run the executable WAR:

    java -jar target/scratch-spring-mustache-1.0-SNAPSHOT.war