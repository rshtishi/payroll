# Gateway Server

*Gateway Server* is the service abstract cross-cutting concerns as security, logging, and tracking of users across multiple services by acting as an intermediary 
between the service client and service being invoked.

## Business Case

In a distributed architecture like a microservice, when each service is developed, maintain, and deployed independently to other services, it becomes difficult implementing behaviors as security, logging, and tracking of users across multiple services. We should implement these functionalities without the need for each
team to build their solutions. We need to:

- put all service behind a single URL and map those calls using service discovery to their actuals services instances
- inject correlation IDs into every service call flowing through the service gateway.
- inject correlation IDs into response send back to the client
- build a dynamic routing mechanism that will route the request to service instances endpoints

## Technology

*Gateway Server* is using the following technologies:

- Java [version: 11] (the language used to write the application)
- Maven [version: 3.6] (the tool for managing dependencies and building the project)
- Spring-Boot [version: 2.2.5.RELEASE] (the framework for creating spring application that just runs)
- Spring-Boot-Actuator [version: 2.2.5.RELEASE] (the dependency for monitoring and managing the application)
- Spring-Cloud [version: Hoxton.SR3] (the framework for building some of the common patterns in distributed systems)
- Spring-Cloud-Netflix-Zuul [version: 2.2.2.RELEASE] ( for implementing dynamic routing, monitoring, resiliency, security)
- Spring-Cloud-Config-Client [version: 2.2.2.RELEASE] (the dependency for binding the client to the Configuration Server Service)
- Spring-Cloud-Netflix-Client [version: 2.2.2.RELEASE] (the dependency for register the client to the Eureka Server Service)
- Spring-Cloud-Sleuth [version: 2.2.2.RELEASE] (the dependency for distributed tracing)
- Spring-Cloud-Zipkin [version: 2.2.2.RELEASE]  (the dependency for displaying the distributed tracing)

## Architecture

## Implementation Details

The configuration information for *Gateway Server* is located outside the service code. Below is the dependency needed to communicate with *Configuration Server* 
for retrieving *Gateway Server* configuration information:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-client</artifactId>
		</dependency>
```

After the Maven dependencies have been defined, you need to tell the *Gateway Server* service where to contact the *Configuration Server*. We have configured the 
application name for the service, the application profile, and the URI to connect to a *Configuration Server* in the ```bootstrap.properties``` file. 
Below it is the configuration information:

```
spring.application.name=gateway
spring.profiles.active=default
cloud.config.uri=http://localhost:8888

zuul.sensitiveHeaders=Cookie, Set-Cookie
```


```zuul.sensitiveHeaders=Cookie, Set-Cookie``` allows the *Gateway Server* to forward the cookies to the services.

Next, we need to register *Gateway Server* to service discovery. The first thing to be done is adding the Spring Eureka Client dependency to the *Gateway Server* 
service’s pom.xml file like below:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
```

Then we need to add the configuration in the application.properties file of the *Gateway Server* to enable communication with Eureka. Below are the configuration 
information that are added:

```
#Eureka
eureka.instance.preferIpAddress=true
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
ribbon.ReadTimeout=60000
```

Every service registered with Eureka will have two components associated with it: the application ID and the instance ID.The application ID is used to represent 
a group service instance. In a Spring-Boot-based microservice, the application ID will always be the value set by the ```spring.application.name property```. The 
```eureka.instance.preferIpAddress``` property tells Eureka that you want to register the service’s IP address to Eureka rather than its hostname.
The ```eureka.client.registerWithEureka``` attribute is the trigger to tell the *Gateway Server* service to register itself with Eureka, and the 
```eureka.client.fetchRegistry``` attribute is used to tell the Spring Eureka Client to fetch a local copy of the registry. Every 30 seconds, the *Gateway Server*
will re-contact the *Eureka Server* service for any changes to the registry.
The last attribute, the ```eureka.serviceUrl.defaultZone``` attribute, holds a comma-separated list of *Eureka Server* services that the *Gateway Server*
will use to resolve to service locations.

Now, we are going to set up the *Gateway Server* by using the Netflix Zuul. First, we add the maven dependency in the *Gateway Server* pom.xml file. Below is the
dependency we added:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-zuul</artifactId>
		</dependency>
```
This dependency tells the Spring Cloud framework that this service will be running Zuul and initialize Zuul appropriately.

Next, we continue by setting up the *Gateway Server* bootstrap class. Below is the class that bootstrap *Gateway Server* application:

```
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class GatewayServerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServerApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Gateway Application Started");
		SpringApplication.run(GatewayServerApplication.class, args);
	}

}
```

The ```@EnableDiscoveryClient``` annotation is the trigger for Spring Cloud to enable the application to use the DiscoveryClient and Ribbon libraries.
To create the *Gateway Server* we annotate the class with ```@EnableZuulProxy```. This annotation creates Zuul Server, loads the Zuul Reverse Proxy Filters and
automatically use *Eureka Server* to lookup services by their service IDs and then use Netflix Ribbon to do client-side load balancing of requests from within Zuul.

If you want to build your routing service and not use any Zuul pre-built capabilities, you can use the ```@EnableZuulServer```.

The last step of setting up the *Gateway Server* is to configure the *Gateway Server*. Below are the configuration that you need to add to your application.properties file(remember our configuration files are located in GitHub):

```
#Gateway
management.endpoints.web.exposure.include=*
management.security.enabled=false

zuul.prefix=/payroll
```
We haven't specified any routes. So, Zuul will automatically use the Eureka service ID of the service being called and map it to a downstream service instance.

The beauty of using Zuul with Eureka is that not only do you now have a single endpoint that you can make calls through, but with Eureka, you can also add and
remove instances of a service without ever having to modify Zuul.

The property ```zuul.prefix``` add a the payroll prefix to paths. We have configured the Spring Boot Actuator to expose all endpoints with the property 
```management.endpoints.web.exposure.include``` and disabled security by using the property ```management.security.enabled```

