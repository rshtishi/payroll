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

The architecture of Gateway Server is like below:

![Gateway Server Architecture](https://github.com/rshtishi/payroll/blob/master/gateway-server/src/main/resources/static/images/gateway-server.jpeg)

## Implementation Details

### Configuring Communication With *Configuration Server*

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

### Configuring Communication With *Eureka Server*

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

### Configuring the *Gateway Server*

Now, we are going to set up the *Gateway Server* by using the **Netflix Zuul**. First, we add the maven dependency in the *Gateway Server* pom.xml file. Below is the
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
```management.endpoints.web.exposure.include``` and disabled security by using the property ```management.security.enabled```.

### Configuring Distributed Logging

A **correlation ID** is a randomly generated, unique number or string that’s assigned to a transaction when a transaction is initiated. As the transaction flows across multiple services, the correlation ID is propagated from one service call to another. We are using Spring Cloud Sleuth to:
- create and inject a correlation ID into your service calls if one doesn’t exist
- manage the propagation of the correlation ID to outbound service calls so that the correlation ID for a transaction is automatically added to outbound calls
- add the correlation information to Spring’s MDC logging so that the generated correlation ID is automatically logged by Spring Boots default SL4J and Logback implementation.

We are using Spring cloud Sleuth together with Zipkin to publish the tracking information in the service call to the Zipkin-distributed tracing platform. In the Spring 
Cloud Sleuth the correlation ID is referred as **Trace Id**.

Below we have added Spring Cloud Sleuth and Zipkin in our *Gateway Server* service:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

```
We are using ELK (Elasticsearch, Logstash, Kibana) to aggregate logs and the unique **Trace ID** across service log entries makes debugging distributed transactions more manageable. We have modified the Logback to sent all logs of *Gateway Server* to the central location. Next, we will load the logs from that central location to ElastiSearch using Logstash. Finally, we would be able to track a transaction through multiple services by the Trace ID.

We have added the dependency below for encoding the logs to format that is acceptable from Logstash:

```
		<dependency>
			<groupId>net.logstash.logback</groupId>
			<artifactId>logstash-logback-encoder</artifactId>
			<version>4.9</version>
		</dependency>
```

Below are the Logback configuration file for pushing the logs file to the central location:

```
<configuration>

	<appender name="STASH"
		class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${LOG_STASH_LOCATION}/gateway.log</file>
		<rollingPolicy
			class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<fileNamePattern>${LOG_STASH_LOCATION}/gateway.%d{yyyy-MM-dd}.log
			</fileNamePattern>
			<maxHistory>7</maxHistory>
		</rollingPolicy>
		<encoder class="net.logstash.logback.encoder.LogstashEncoder" />
	</appender>
	
	<!-- Application logs at trace level -->
	<logger name="com.github.rshtishi.gatewayserver" level="info"
		additivity="false">
		<appender-ref ref="FILE" />
		<appender-ref ref="CONSOLE" />
		<appender-ref ref="STASH" />
	</logger>

<configuration>
```

After inspecting the HTTP response back from any service call made to any service you’ll see that the trace ID used in the call is never returned in the HTTP
response headers. The Spring Cloud Sleuth team believes that returning any of the tracing data can be a potential security issue but it allows to “decorate” the HTTP response information with its tracing and span IDs. We will build a **Zuul “ POST ” response filter** to add the trace ID you generated for use in your services to the HTTP response returned by the caller. Below is the Zuul Post filter:

```
@Component
public class ResponseFilter extends ZuulFilter {

	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final String FILTER_TYPE = "post";
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);

	@Autowired
	private Tracer tracer;

	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.getResponse().addHeader("trace-id", tracer.currentSpan().context().traceIdString());
		return null;
	}

	@Override
	public String filterType() {
		return FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

}
```

Below we have configured *Gateway Server* to communicate with Zipkin:

```
#Zipkin
spring.zipkin.baseUrl=http://localhost:9411
```
Zipkin helps us to visualize complex transaction.

### Securing the Communication With *Gateway Server*

The Zuul **Gateway Server** is the only service that will be open for the public. Here will be directed all requests for accessing the services. For security reasons,
we are going to encrypt the traffic between the *Gateway Server* and the client.  We are going to use the https protocol. We created a self-signed certificate and
then added the configuration below:

```
#SSL configuration
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:keystore/gateway.p12
server.ssl.key-store-password={cipher}AQBQRE3yg3Lutixk9iKYH+NdKTKp1vSILPPKXlZ+qvpWKaZL5fIGoO7ldRvWLD84FjoGo9LFfiRHWj7w5wvlRuPAcA8JSnRveO0iaur2dDjBlz9WmPCvR/17AtViDb7mYwLZM01MdxwN2tzMCLhey02yzfNjshnJLs7ZJGickkEKI471Zo1jDMvrFoNuiwiTSxImEJBcjle1ZPuOGHGXEIwdGagDs6coJtxp4qoBCj3iIaRnnqGkbhvYhzOMToSfF4QuPn1vx8a2PWZGilR4w1IY9yqFqZrcTXVNyg+AnwukfJ3/8mp2rDUNqjiN2XjpAeEee9CZXSFUYd/xwKusAB8fUG8wa5+RSTU6+0q1FIQ5UrjrCv/YmCPWTNI+mwNAG9I=
server.ssl.key-alias=gateway-server
```

## Setup

Prerequisite needed before setup:

- Git
- GitHub
- *Configuration Server* should be started and running
- *Eureka Server* should be started and running

Execute the following commands:

- ```mvn clean install``` (to build the project)
- ```mvn spring-boot:run``` (to run the project)

Use the link below to access the routes mapped in *Gateway Server*:

- ```https://localhost:5555/actuator/routes``` [Http method: GET] (retrieves the routing information)
