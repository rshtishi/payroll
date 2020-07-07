# OAuth2 Server

*OAuth2 Server* is the service we utilize for validating the tokens of services requests without the client having to present their credentials to each service 
processing their request.

## Business Case

In microservice, we need to implement security in multiple services. We need an authentication service that will authenticate the user credentials and issue a token.
Every time the user tries to access a service protected by the authentication service, the authentication service will validate that the OAuth2 token was issued by it 
and that it hasn’t expired. The authentication service needs to:

- authenticate users (validate if the user is who they say they are)
- check their authorities (validate if the user have has permission to do what they are trying to do)
- keep the service running on patched and up-to-date to minimize the risk of vulnerabilities

## Technology

*OAuth2 Server* is using the following technologies:

- Java [version: 11] (the language used to write the application)
- Maven [version: 3.6] (the tool for managing dependencies and building the project)
- Lombok [version: 1.18.8] (for generating the POJO boilerplate code)
- Liquibase [version: 3.8.7] (for managing the database changes)
- H2 Database [version: 1.4.200] (for providing a relational database)
- Spring-Boot [version: 2.2.5.RELEASE] (the framework for creating spring application that just run)
- Spring-Boot-Data-JPA [version: 2.2.5.RELEASE] (the dependency for easier access and manipulation of relational database)
- Spring-Cloud [version: Hoxton.SR3] (the framework for building some of the common patterns in distributed systems)
- Spring-Cloud-Config-Client [version: 2.2.2.RELEASE] (the dependency for binding the client to the Configuration Server Service)
- Spring-Cloud-Netflix-Client [version: 2.2.2.RELEASE] (the dependency for register the client to the Eureka Server Service)
- Spring-Cloud-Security [version: 2.2.1.RELEASE] (the dependency that provides features related to token-based security )
- Spring-Cloud-OAUTH2 [version: 2.1.2.RELEASE] ()
