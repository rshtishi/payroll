# Eureka Server

*Eureka Server* is service we use to manage the addresses of all remote services that are part of the microservice application and providing each service with service 
location of all other services that are part of the microservice application.

## Business Case

Each service in microservice architecture can be deployed in different machines. We might have more than one instance of the same service up and running in two different
machines. In many cases the services need to communicate with each other to complete a transaction. This becomes difficult due to the fact we might more than one instance up
for a given service, services may change address or services might be down. We need to:

- find service location where the service is deployed for the service consumers.
- abstract away service consumers from the physical locations of the machines where the services are deployed. Thus, we can scale up or down the number of services instances
running in an environment.
- to increase the service resiliency. When we have applications that are called from other applications, we can say that we are developing a platform.When developing a 
platform we should focus on that service to be reliable, redundant, and resilient.

## Technology

*Eureka Server* is using the following technologies:

- Java [version: 11] (the language used to write the application)
- Maven [version: 3.6] (the tool for managing dependencies and building the project)
- Spring-Boot [version: 2.2.5.RELEASE] (the framework for creating spring application that just runs)
- Spring-Cloud [version: Hoxton.SR3] (the framework for building some of the common patterns in distributed systems)
- Spring-Cloud-Config-Client [version: 2.2.2.RELEASE] (the dependency for binding the client to the Configuration Server Service)
- Spring-Cloud-Netflix [version: 2.2.2.RELEASE] (the framework that provides the common pattern for building large distributed systems)

## Architecture

The service discovery conceptual architecture is like below:

![Eureka Server Architecture Image](https://github.com/rshtishi/payroll/blob/master/eureka-server/src/main/resources/static/images/eureka-server-architecture.png)

## Implementation Details

The configuration information for *Eureka Server* is located outside the service code. Below is the dependency needed to communicate with *Configuration Server* for retrieving *eureka server* configuration information:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-client</artifactId>
		</dependency>
```

After the Maven dependencies have been defined, you need to tell the *Eureka Server* service where to contact the *Configuration Server*. We have configured the application name for the service, the application profile, and the URI to connect to a *Configuration Server* in the ```bootstrap.properties``` file. Below it is the configuration information:

```
spring.application.name=eureka
spring.profiles.active=default
cloud.config.uri=http://localhost:8888
```

Setting up the *Eureka Server* service starts by adding the following dependency:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
		</dependency>
```

After that we configure the eureka configuration information. The configuration information is added in ```application.properties``` file located in the central repository. 
Below is the configuration information:

```
server.port=8761

#Eureka Server Configuration
eureka.client.registerWithEureka=false
eureka.client.fetchRegistry=false
eureka.client.waitTimeInMsWhenSyncEmpty=5
```

The ```eureka.client.registerWithEureka``` attribute tells the service not to register with a *eureka server* service when the Spring Boot *Eureka Server*  application starts because this is the *Eureka Server* service. The ```eureka.client.fetchRegistry``` attribute is set to false so that when the *Eureka Server* service starts, it doesn’t try to cache its registry information locally. The last attribute, ```eureka.server.waitTimeInMsWhenSync```, speeds up the amount of time it will take for the Eureka service to start and show services registered with it. By default, *Eureka server* won’t immediately advertise any services that register with it. It will wait five minutes to give all of the services a chance to register with it before advertising them.

Individual services registering will take up to 30 seconds to show up in the *Eureka Server* service because Eureka requires three consecutive heartbeat pings from the service spaced 10 seconds apart before it will say the service is ready for use. 

Finally, we add the ```@EnableEurekaServer``` annotation to the application bootstrap class we’re using to start your *Eureka Server* service.

```
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
```

You use only one new annotation to tell your service to be a *Eureka Server* service, that’s ```@EnableEurekaServer```. 

## Setup

Prerequisite needed before setup:

- Git
- GitHub
- *Configuration Server* should be started and running

Execute the commands:

- ```mvn clean install``` (to build the project)
- ```mvn spring-boot:run``` (to run the project)

To see the information for all services, acess the following GET endpoint:

- http://localhost:8761/eureka/apps

To access particular service information, use the convetion below:

- http://localhost:8761/eureka/apps/{APPID}
	
*APPID* is application name of the service that we want to retrieve service information.

Examples:

- http://localhost:8761/eureka/apps/department [Http method: GET] (we retrieve the location information and the number of instances for the deparment service)
- http://localhost:8761/eureka/apps/employee [Http method: GET] (we retrieve the location information and the number of instances for the employee service)

