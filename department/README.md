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

![OAuth2 Server Architecture](https://github.com/rshtishi/payroll/blob/master/oauth2-server/src/main/resources/static/images/oauth2-server.jpeg)

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

Second, we need to register *Depertment* service to service discovery. The first thing to be done is adding the Spring Eureka Client dependency to the 
*Depertment* service’s pom.xml file like below:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
```

Then we need to add the eureka configuration information in the *Depertment* service to enable communication with Eureka. Below are the configuration information that are added:

```
#Eureka
cloud.config.enabled=true
eureka.instance.preferIpAddress=true
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
```

Every service registered with Eureka will have two components associated with it: the application ID and the instance ID.The application ID is used to represent a group service instance. In a Spring-Boot-based microservice, the application ID will always be the value set by the ```spring.application.name property```. The ```eureka.instance.preferIpAddress``` property tells Eureka that you want to register the service’s IP address to Eureka rather than its hostname. The ```eureka.client.registerWithEureka``` attribute is the trigger to tell the *Depertment* service to register itself with Eureka, and the ```eureka.client.fetchRegistry``` attribute is used to tell the Spring Eureka Client to fetch a local copy of the registry. Every 30 seconds, the *Depertment* service will re-contact the Eureka Server service for any changes to the registry. The last attribute, the ```eureka.serviceUrl.defaultZone``` attribute, holds a comma-separated list of Eureka Server services that the Gateway Server will use to resolve to service locations.

### Configuring communication with the *database*

Next, we add the dependencies needed for the *Depertment* service to communicate with the database. Below are the dependencies:

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
```

Below we have configured the *Department* service to communicate with H2 database:

```
#H2 DB 
spring.jpa.hibernate.ddl-auto=none
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:departmentdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password={cipher}AQApFhvGmUI3R8ekFYlYo0n/TmYAJMuYIqPpE65jRzdQjmjWwgyLMnevmjytmD2krEDIasLMt5UaQwUjA+TiF036Oilny2nHnCJeqkn/ZKpxavyrrmBG9EACBUt/5I7ztZRNQGAfHflB1OM4WamBJ+7aD1MFSjqervyXOzE547DvWsRYH++WF/380jY5oCCLwiPf1QBCDW6qYE6MgmbZYkq4Rk27c0xgMq4NdSJFrUSpd0D9cuG1eKRFS2ZXwUOAGb5ksiJrWAbliaYq0g5MKvbw1YwLXKhnHysdwMyIZ6faWRF2OYuagf40fB1N3T2sp8o9MZo7x1Kxt9mc1CeigYjH6OOK/pfLn6HqLuoKV9ZPNVCpwysBV8b/FBgC+wqHPuk=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### Configuring Liquibase for managing to manage database schema changes

We have chosen **Liquibase** to manage database schema changes. Below is the dependency needed for including Liquibase:

```
		<dependency>
			<groupId>org.liquibase</groupId>
			<artifactId>liquibase-core</artifactId>
		</dependency>
```

Below is the configuration information for Liquibase:

```
#Liquibase 
spring.liquibase.change-log=classpath:db/liquibase-changelog.xml
spring.liquibase.enabled=true
```

Liquibase uses a changelog to explicitly list database changes in order. The changelog acts as a ledger of changes and contains a list of changesets (units of change) that Liquibase can execute on a database. The property ```spring.liquibase.enabled``` enable Liquibase in our application and we determine the location of changelog
file in ```spring.liquibase.change-log``` property.

Below is the content of **liquibase-changelog.xml**:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">

	<include file="changelog/01-create-department-scheme.xml"
		relativeToChangelogFile="true" />
	<include file="changelog/02-data-insert-departments.xml"
		relativeToChangelogFile="true" />
	<include file="changelog/03-alter-departments.xml"
		relativeToChangelogFile="true" />

</databaseChangeLog>
```

As we can see above, we have grouped the changeset in three files that are included in liquibase-changelog.xml.

The **01-create-scheme.xml** file has the changeset for creating the schema of the database, as we can see below:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet id="01" author="rshtishi">

		<createTable tableName="department">
			<column name="id" type="int" autoIncrement="true">
				<constraints nullable="false" primaryKey="true" />
			</column>
			<column name="name" type="varchar(30)">
				<constraints nullable="false" />
			</column>
		</createTable>

	</changeSet>
</databaseChangeLog>
```

The **02-data-insert.xml** file has the changeset for populating the tables with information. Below is the content of the file:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet id="02" author="rshtishi">
		<insert tableName="department">
			<column name="id" valueNumeric="1" />
			<column name="name" value="Software Development" />
		</insert>
		<insert tableName="department">
			<column name="id" valueNumeric="2" />
			<column name="name" value="Human Resources" />
		</insert>
	</changeSet>
</databaseChangeLog>
```

The **03-alter-departments.xml** file has the changeset that modifies department table. Below is the content of the file:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet  id="03" author="rshtishi">
		<addColumn  tableName="department">
			<column name="no_of_employees" type="int" defaultValue="0" />
		</addColumn>
	</changeSet>
</databaseChangeLog>
```

### Configuring the security

The *Department* service is a **protected resource**. We need to ensure that only authenticated users who have the proper authorization can access it. Below are 
the dependencies for implementing security:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-oauth2</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-security</artifactId>
		</dependency>
```

Below are the configuration information needed to implement the security:

```
#OAuth2
security.oauth2.resource.userInfoUri=http://localhost:8901/user
security.jwt.public-key=classpath:public.txt
```
The ```security.oauth2.resource.userInfoUri``` property defines the callback URL that is going to be used by the application to get user information. The last attribute is ```security.jwt.public-key``` .This is property, that holds the public key for decrypting the JWT token.

Below we have created the class that is going to bootstrap the application:

```
@SpringBootApplication
@EnableResourceServer
public class DepartmentApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DepartmentApplication.class, args);
		LOGGER.info("Department Application Started");
	}
}
```

### Configuring distributed logging

Below we have added Spring Cloud Sleuth and Zipkin in our *Department* service:

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
		<file>${LOG_STASH_LOCATION}/department.log</file>
		<rollingPolicy
			class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<fileNamePattern>${LOG_STASH_LOCATION}/department.%d{yyyy-MM-dd}.log
			</fileNamePattern>
			<maxHistory>7</maxHistory>
		</rollingPolicy>
		<encoder class="net.logstash.logback.encoder.LogstashEncoder" />
	</appender>
	
	<!-- Application logs at trace level -->
	<logger name="com.github.rshtishi.department" level="info"
		additivity="false">
		<appender-ref ref="FILE" />
		<appender-ref ref="CONSOLE" />
		<appender-ref ref="STASH" />
	</logger>

</configuration>
```
Below we have configured Gateway Server to communicate with Zipkin:

```
#Zipkin
spring.zipkin.baseUrl=http://localhost:9411
```
Zipkin helps us to visualize complex transaction.

### Configuring the mechanism for invoking services

We’re going to see an example of how to use a ```RestTemplate``` that’s Ribbon-aware. Below we have declared the bean that is going to be used for invoking other
services:

```
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
```
The ```@LoadBalanced``` annotation tells Spring Cloud to create a Ribbon backed ```RestTemplate``` class.

Below we have used the Ribbon backed ```RestTemplate``` to call the *Employee* service:

```
@Component
public class EmployeeRestTemplate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRestTemplate.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public long countEmployeesByDepartmentId(int departmentId) {
		LOGGER.info("Called countEmployeesByDepartmentId, departmentId: "+departmentId);
		ResponseEntity<Long> response = restTemplate.exchange("http://employee/employees/{departmentId}/count", 
				HttpMethod.GET, null, Long.class, departmentId);
		employeeCount = response.getBody();
		return response.getBody();
	}
}
```
The Ribbon-enabled RestTemplate will parse the URL passed into it and use whatever is passed in as the server name as the key to query Ribbon for an instance of a service. The actual service location and port are completely abstracted from the developer.

### Configuring client-side resiliency patterns

Client resiliency software patterns are focused on protecting a remote resource’s(another microservice call or database lookup) client from crashing when the remote
the resource is failing because that remote service is throwing errors or performing poorly. We are going to use hystrix for implementing this patterns.
 
Below we activate the Hystrix Service:
 
```
@SpringBootApplication
@EnableCircuitBreaker
@EnableResourceServer
public class DepartmentApplication {
        ...
}
```
The ```@EnableCircuitBreaker``` tells Spring Cloud you’re going to use Hystrix for your service.

Below we have configured **Circuit breakers** and **Fallbacks** pattern when invoking the *Employee* Service:

```
@Component
public class EmployeeRestTemplate {
	...
	@HystrixCommand(fallbackMethod = "countEmployeesByDepartmentIdFallback")
	public long countEmployeesByDepartmentId(int departmentId) {
		...
		return response.getBody();
	}
	
	public long countEmployeesByDepartmentIdFallback(int departmentId) {
		return -1;
	}

}
```

The ```@HystrixCommand``` annotation to marks the ```countEmployeesByDepartmentId()``` method as being managed by a **Hystrix circuit breaker**.When the Spring framework sees the ```@HystrixCommand```, it will dynamically generate a proxy that will wrap the method and manage all calls to that method through a thread pool of threads specifically set aside to handle remote calls. The fallback method attribute defines a single function in your class that will be called if the call from Hystrix fails.

### Propagating *Trace ID* and *Access Token* 

We use the ```UserContextFilter``` class for parsing the HTTP header and retrieving data. Below is the implementation:

```
@Component
public class UserContextFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		UserContextHolder.getContext().setAuthorization(httpServletRequest.getHeader(UserContext.AUTHORIZATION));
		UserContextHolder.getContext().setTraceId(httpServletRequest.getHeader(UserContext.TRACE_ID));
		chain.doFilter(httpServletRequest, response);
	}

}

```

The values retrieved from the HTTP header of the call are set into a UserContext, which is stored in UserContextHolder.

Below is the ```UserContext``` class:

```
@Getter
@Setter
public class UserContext {

	public static final String AUTHORIZATION = "Authorization";
	public static final String TRACE_ID = "trace-id";

	private String authorization = new String();
	private String traceId = new String();

}

```

Below is the ```UserContextHolder``` class:

```
public class UserContextHolder {

	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

	public static final UserContext getContext() {
		UserContext context = userContext.get();

		if (context == null) {
			context = createEmptyContext();
			userContext.set(context);

		}
		return userContext.get();
	}

	public static final void setContext(UserContext context) {
		userContext.set(context);
	}

	public static final UserContext createEmptyContext() {
		return new UserContext();
	}

}
```

The ```UserContextHolder``` class is used to store the ```UserContext``` in a ```ThreadLocal``` class.

Below we have implemented and interceptor that will intercept the calls to other service and inject the *Trace ID* and the *Access Token*:

```
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
		request.getHeaders().remove(UserContext.AUTHORIZATION);
		headers.add(UserContext.AUTHORIZATION, UserContextHolder.getContext().getAuthorization());
		headers.add(UserContext.TRACE_ID, UserContextHolder.getContext().getTraceId());
		return execution.execute(request, body);
	}

}
```
The ```UserContextInterceptor``` class is used to inject the Trace ID and Access Token into any outgoing HTTP-based service requests being executed from a RestTemplate instance.

Below we add the ```UserContextInterceptor``` to the RestTemplate:

```
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (interceptors == null) {
			restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {
			interceptors.add(new UserContextInterceptor());
			restTemplate.setInterceptors(interceptors);
		}
		return restTemplate;
	}
```


### Propagating the parent thread’s context to threads managed by a Hystrix command

Hystrix, by default, will not propagate the parent thread’s context to threads managed by a Hystrix command. Any values set as **ThreadLocal** values in the parent thread will not be available by default to a method called by the parent thread and protected by the ```@HystrixCommand``` annotation. For example, we pass a
correlation ID or authentication token in the HTTP header of the REST call that can then be propagated to any downstream service calls.

## Setup
