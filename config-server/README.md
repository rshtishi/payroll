# Configuration Server

*Configuration Server* is the service that we utilize to access the configuration information from the centralized configuration 
information repository. 

## Business Case

Each microservice in a microservice application can be compiled and deployed independently of the other services used in the application. 
Thus, each microservice has its configuration information. We will need to go to the service code to tweak the 
configuration information. This becomes troublesome with the increase of microservice in the microservice application. We would have to remember
for each microservice where the configuration information is located. We need to:

- segregate the services configuration information from the actual physical deployment of a service.
- abstract the access to the service configuration information behind the configuration server. Rather than writing code that directly 
accesses the configuration information repository
- centralize the configuration information into a single repository. Rather than have it scattered in hundreds of services.
- have a reliable highly available and redundant repository because the application configuration information is going to be completely
segregated from your deployed service and centralized.

## Technology

*Configuration Server* is using the following technologies:

- Java [version: 11] (the language used to write the application)
- Maven [version: 3.6] (the tool for managing dependencies and building the project)
- Spring-Boot [version: 2.2.5.RELEASE] (the framework for creating spring application that just run)
- Spring-Cloud [version: Hoxton.SR3] (the framework for building some of the common patterns in distributed systems)
- Spring-Cloud-Config [version: 2.2.2.RELEASE] (the framework for server-side and client-side support for externalized configuration 
in a distributed system)
- Git [version: 2.25.1] (the version controll used managing the configuration information)
- GitHub (the hosting service for the remote repository, for centralizing the the configuration information)


## Architecture

Configuration management conceptual architecture is like below:

![Configuration Management Architecture](https://github.com/rshtishi/payroll/blob/master/config-server/src/main/resources/static/images/configuration-server-architecture.png)

## Implementation Details

We have added the dependency in pom.xml for the Spring Cloud Configuration Server like below:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
```

The configuration repository that holds configuration information is in the link below:

[GitHub Configuration Repository](https://github.com/rshtishi/config-repo)

The bootstrap class for Spring Cloud Config Server is annotated with ```@EnableConfigServer``` to enable the service as Spring Cloud 
Configuration Server. Below it is the code:
```
@SpringBootApplication
@EnableConfigServer
public class ConfigurationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationServerApplication.class, args);
	}

}
```

We use application.properties file to hold the configuration for Spring Cloud Config Server. We have configured the communication of
Spring Cloud Config Server with all other services of payroll. Below it is the configuration information:
```
spring.application.name=config-server
server.port = 8888
spring.cloud.config.server.git.uri=https://github.com/rshtishi/config-repo
spring.cloud.config.server.git.searchPaths=department, employee, eureka, gateway, oauth2
```

By default, the Spring Cloud configuration server stores all properties in plain text within the application’s configuration files.
This includes sensitive information such as database credentials. We have configured the Spring Cloud Config Server to encrypt sensitive
information using the encryption key. The bootstrap.properties file holds the configuration for encryption of configuration information.
Below it configuration information in bootstrap.properties file:
```
encrypt.key-store.location=classpath:keystore/server.jks
encrypt.key-store.password=payroll-security
encrypt.key-store.alias=serverConfigKey
```

## Setup

Prerequisite needed before setup:

- Git
- GitHub

Execute the commands:

  - ```mvn clean install``` (to build the project)
  - ```mvn  spring-boot:run``` (to run the project)
  
To access the configuration of service follow the convetion is like below:

http://localhost:8888/{service}/{environment}

Examples:

- ```localhost:8888/employee/default``` [Http method: GET] (retrieving the configuration information for the employee service) 
- ```localhost:8888/department/default``` [Http method: GET] (retrieving the configuration information for the department service)


To encrypt sensitive information:

-  ```localhost:8888/encrypt``` [Http method: POST, Body: {value to be encrypted}] (encrypts the values that are sensitive, than the 
encrypted value is stored in configuration information repository)

To decrypt encrypted information:

- ```localhost:8888/decrypt``` [Http method: POST, Body: {encrypted value}] (decrypts the value)


  




