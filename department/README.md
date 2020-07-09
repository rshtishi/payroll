# Department

*Department* is the service for managing the departments.

## Business Case

The service need to be small and have narrowly defined responsibilities. It should build, deploy, and test independently of other services because their code, 
source control repository, and the infrastructure (app server and database) are now completely independent of the other services that made the microservice architecture.
The service need to:

- have well-defined boundaries of responsibility
- have responsibility for a single part of a business domain
- to be deployed completely independently of one another
- to communicate with a technology-neutral protocol for exchanging data between the service consumer and service provider

## Technology

*Department* service is using the following technologies:

- Java [version: 11] (the language used to write the application)
- Maven [version: 3.6] (the tool for managing dependencies and building the project)
- Lombok [version: 1.18.8] (for generating the POJO boilerplate code)
- Liquibase [version: 3.8.7] (for managing the database changes)
- H2 Database [version: 1.4.200] (for providing a relational database)
- Spring-Boot [version: 2.2.5.RELEASE] (the framework for creating spring application that just run)
- Spring-Boot-Data-JPA [version: 2.2.5.RELEASE] (the dependency for easier access and manipulation of relational database)
- Spring-Data-Redis [version: 2.2.5.RELEASE] (the dependency that provides easy configuration and access to Redis )
- Spring-Cloud [version: Hoxton.SR3] (the framework for building some of the common patterns in distributed systems)
- Spring-Cloud-Config-Client [version: 2.2.2.RELEASE] (the dependency for binding the client to the Configuration Server Service)
- Spring-Cloud-Netflix-Client [version: 2.2.2.RELEASE] (the dependency for register the client to the Eureka Server Service)
- Spring-Cloud-Neflix-Hystrix [version: 2.2.2.RELEASE] (the dependency for implementing the circuit breaker, fallback, and bulkhead patterns)
- Spring-Cloud-Security [version: 2.2.1.RELEASE] (the dependency that provides features related to token-based security )
- Spring-Cloud-OAUTH2 [version: 2.1.2.RELEASE] (the dependency that provides needed for implementing the OAUTH2 standard)
- Spring-Cloud-Stream-Kafka [version: 2.1.2.RELEASE] (the dependency for implementing event-driven architecture with Kafka)
- Spring-Cloud-Sleuth [version: 2.2.2.RELEASE] (the dependency for distributed tracing)
- Spring-Cloud-Zipkin [version: 2.2.2.RELEASE] (the dependency for displaying the distributed tracing)
- Swagger-SpringMVC [version: 1.0.2.RELEASE] (the dependency for documenting the rest endpoints)

## Architecture

## Implemenations Details

### Configuring communication with *Configuration Server*

The configuration information for *Depertment* service is located outside the service code. Below is the dependency needed to communicate with *Configuration Server* 
for retrieving *Depertment* service  configuration information:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-client</artifactId>
		</dependency>
```

After the Maven dependencies have been defined, you need to tell the *Depertment* service where to contact the *Configuration Server*. We have configured the 
application name for the service, the application profile, and the URI to connect to a *Configuration Server* in the bootstrap.properties file. Below it is 
the configuration information:

```
spring.application.name=department
spring.profiles.active=default
cloud.config.uri=http://localhost:8888
```

### Configuring communication with *Eureka Server*

Second, we need to register *Depertment* serviceto service discovery. The first thing to be done is adding the Spring Eureka Client dependency to the 
*Depertment* service’s pom.xml file like below:




