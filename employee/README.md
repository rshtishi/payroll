# Employee

## Business Case

The service need to be small and have narrowly defined responsibilities. It should build, deploy, and test independently of other services because their code, 
source control repository, and the infrastructure (app server and database) are now completely independent of the other services that made the microservice architecture.
The service need to:

- have well-defined boundaries of responsibility
- have responsibility for a single part of a business domain
- to be deployed completely independently of one another
- to communicate with a technology-neutral protocol for exchanging data between the service consumer and service provider

## Technology

*Employee* service is using the following technologies:

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
- Spring-Cloud-OAUTH2 [version: 2.1.2.RELEASE] (the dependency that provides needed for implementing the OAUTH2 standard)
- Spring-Cloud-Stream-Kafka [version: 2.1.2.RELEASE] (the dependency for implementing event-driven architecture with Kafka)
- Spring-Cloud-Sleuth [version: 2.2.2.RELEASE] (the dependency for distributed tracing)
- Spring-Cloud-Zipkin [version: 2.2.2.RELEASE] (the dependency for displaying the distributed tracing)
- Swagger-SpringMVC [version: 1.0.2.RELEASE] (the dependency for documenting the rest endpoints)

## Architecture

The architecture featuring *Employee* service in the center:

![Employee Service Architecture](https://github.com/rshtishi/payroll/blob/master/employee/src/main/resources/static/images/employee-service-architecture.jpeg)

## Implemenations Details

### Configuring Communication With Configuration Server

The configuration information for *Employee* service is located outside the service code. Below is the dependency needed to communicate with *Configuration Server*
for retrieving *Employee* service configuration information:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-client</artifactId>
		</dependency>
```

After the Maven dependencies have been defined, you need to tell the *Employee* service where to contact the *Configuration Server*. We have configured the 
application name for the service, the application profile, and the URI to connect to a *Configuration Server* in the bootstrap.properties file. Below it is the 
configuration information:

```
spring.application.name=department
spring.profiles.active=default
cloud.config.uri=http://localhost:8888
```

### Configuring Communication With Eureka Server

Second, we need to register *Employee* service to service discovery. The first thing to be done is adding the Spring Eureka Client dependency to the *Employee* 
service’s pom.xml file like below:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
```

Then we need to add the eureka configuration information in the *Employee* service to enable communication with Eureka. Below are the configuration information 
that are added:

```
#Eureka
cloud.config.enabled=true
eureka.instance.preferIpAddress=true
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
```

Every service registered with Eureka will have two components associated with it: **the application ID** and the **instance ID**. The application ID is used to 
represent a group service instance. In a Spring-Boot-based microservice, the application ID will always be the value set by the ```spring.application.name property```.
The ```eureka.instance.preferIpAddress``` property tells Eureka that you want to register the service’s IP address to Eureka rather than its hostname. 
The ```eureka.client.registerWithEureka``` attribute is the trigger to tell the Depertment service to register itself with Eureka, and the 
```eureka.client.fetchRegistry``` attribute is used to tell the Spring Eureka Client to fetch a local copy of the registry. Every 30 seconds, the *Employee* service 
will re-contact the *Eureka Server* service for any changes to the registry. The last attribute, the *eureka.serviceUrl.defaultZone* attribute, holds a 
comma-separated list of *Eureka Server* services that the *Employee* Server will use to resolve to service locations.

### Configuring Communication With the *Database*

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

Below we have configured the *Employee* service to communicate with H2 database:

```
#H2 DB 
spring.jpa.hibernate.ddl-auto=none
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:employeedb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password={cipher}AQApFhvGmUI3R8ekFYlYo0n/TmYAJMuYIqPpE65jRzdQjmjWwgyLMnevmjytmD2krEDIasLMt5UaQwUjA+TiF036Oilny2nHnCJeqkn/ZKpxavyrrmBG9EACBUt/5I7ztZRNQGAfHflB1OM4WamBJ+7aD1MFSjqervyXOzE547DvWsRYH++WF/380jY5oCCLwiPf1QBCDW6qYE6MgmbZYkq4Rk27c0xgMq4NdSJFrUSpd0D9cuG1eKRFS2ZXwUOAGb5ksiJrWAbliaYq0g5MKvbw1YwLXKhnHysdwMyIZ6faWRF2OYuagf40fB1N3T2sp8o9MZo7x1Kxt9mc1CeigYjH6OOK/pfLn6HqLuoKV9ZPNVCpwysBV8b/FBgC+wqHPuk=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### Configuring Liquibase to Manage Database Schema Changes

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

Liquibase uses a changelog to explicitly list database changes in order. The changelog acts as a ledger of changes and contains a list of changesets (units of change) 
that Liquibase can execute on a database. The property ```spring.liquibase.enabled``` enable Liquibase in our application and we determine the location of changelog
file in ```spring.liquibase.change-log``` property.

Below is the content of **liquibase-changelog.xml**:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">

	<include file="changelog/01-create-employee-scheme.xml"
		relativeToChangelogFile="true" />
	<include file="changelog/02-data-insert-employees.xml"
		relativeToChangelogFile="true" />
	<include file="changelog/03-alter-employees.xml"
		relativeToChangelogFile="true" />

</databaseChangeLog>
```

As we can see above, we have grouped the changeset in three files that are included in liquibase-changelog.xml.

The **01-create-employee-scheme.xml** file has the changeset for creating the schema of the database, as we can see below:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet id="01" author="rshtishi">

		<createTable tableName="employee">
			<column name="id" autoIncrement="true" type="int">
				<constraints nullable="false" primaryKey="true" />
			</column>
			<column name="firstname" type="varchar(25)">
				<constraints nullable="false" />
			</column>
			<column name="lastname" type="varchar(25)">
				<constraints nullable="false" />
			</column>
			<column name="address" type="varchar(100)">
				<constraints nullable="false" />
			</column>
			<column name="phone" type="varchar(15)">
				<constraints nullable="false" />
			</column>
		</createTable>
            
	</changeSet>
</databaseChangeLog>
```

The **02-data-insert-employees.xml** file has the changeset for populating the tables with information. Below is the content of the file:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet id="02" author="rshtishi">
		<insert tableName="employee">
			<column name="firstname" value="Rando" />
			<column name="lastname" value="Shtishi" />
			<column name="address"
				value="Rr. Arkitekt Kasemi, pa. 71, shk. 2" />
			<column name="phone" value="0691253212" />
		</insert>
		<insert tableName="employee">
			<column name="firstname" value="Vjola" />
			<column name="lastname" value="Shtishi" />
			<column name="address"
				value="Rr. Arkitekt Kasemi, pa. 71, shk. 2" />
			<column name="phone" value="0691253212" />
		</insert> 
	</changeSet>
</databaseChangeLog>
```

The **03-alter-employees.xml** file has the changeset that modifies department table. Below is the content of the file:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet  id="03" author="rshtishi">
		<addColumn  tableName="employee">
			<column name="department_id" type="int" defaultValue="1" />
		</addColumn>
	</changeSet>
</databaseChangeLog>
```

### Configuring the Security

The *Employee* service is a **protected resource**. We need to ensure that only authenticated users who have the proper authorization can access it. Below are 
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

		<dependency>
			<groupId>org.springframework.security.oauth.boot</groupId>
			<artifactId>spring-security-oauth2-autoconfigure</artifactId>
			<version>2.1.2.RELEASE</version>
		</dependency>
```

Below are the configuration information needed to implement the security:

```
#OAuth2
security.oauth2.resource.userInfoUri=http://localhost:8901/user
security.jwt.public-key=classpath:public.txt
```

The ```security.oauth2.resource.userInfoUri``` property defines the callback URL that is going to be used by the application to get user information. 
The last attribute is ```security.jwt.public-key``` .This is property, that holds the public key for decrypting the JWT token.

Below we have created the class that is going to bootstrap the application:

```
@SpringBootApplication
@EnableResourceServer
public class EmployeeApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Employee Application Started");
		SpringApplication.run(EmployeeApplication.class, args);
	}
}
```

### Configuring Distributed Logging

Below we have added Spring Cloud Sleuth and Zipkin in our *Employee* service:

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
<property name="LOG_PATTERN"
             value="%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"/>

	<appender name="STASH"
		class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${LOG_STASH_LOCATION}/employee.log</file>
		<rollingPolicy
			class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<fileNamePattern>${LOG_STASH_LOCATION}/employee.%d{yyyy-MM-dd}.log
			</fileNamePattern>
			<maxHistory>7</maxHistory>
		</rollingPolicy>
		<encoder class="net.logstash.logback.encoder.LogstashEncoder" />
	</appender>

	<root level="error">
		<appender-ref ref="CONSOLE" />
		<appender-ref ref="FILE" />
		<appender-ref ref="STASH" />
	</root>

	<!-- Application logs at trace level -->
	<logger name="com.github.rshtishi.payroll.employee" level="info"
		additivity="false">
		<appender-ref ref="FILE" />
		<appender-ref ref="CONSOLE" />
		<appender-ref ref="STASH" />
	</logger>

</configuration>
```

Below we have configured *Employee* service to communicate with Zipkin:

```
#Zipkin
spring.zipkin.baseUrl=http://localhost:9411
```
Zipkin helps us to visualize complex transaction.

### Configuring Kafka 

We are using Kafka to notify the *Department* service, every time that the *Employee* service data is added or deleted. The *Employee* service will publish and message to a Kafka topic indicating that an event has occurred.

Below are the dependencies needed to start implementing our message producer:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-stream</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-stream-kafka</artifactId>
		</dependency>
```

Next, we add the configuration information for publishing a message, like below:

```
#Kafka
spring.cloud.stream.bindings.output.destination=employeeCountChangeTopic
spring.cloud.stream.bindings.output.content-type= application/json
```
The configuration property ```spring.stream.bindings.output```  maps the ```source.output()``` channel to the employeeCountChangeTopic on the message
broker you’re going to communicate with. The ```spring.cloud.stream.bindings.output.content-type``` tells Spring Cloud Stream that messages being sent to this topic should be serialized as JSON.

Then , we need to tell your application that it’s going to bind to a Spring Cloud Stream message broker. We do this by annotating the organization service’s bootstrap class with an ```@EnableBinding annotation``` annotation, as you can see below:

```
@SpringBootApplication
@EnableResourceServer
@EnableBinding(Source.class)
public class EmployeeApplication {
  ...
}
```

Below is the implementation of publish an image to the message broker:

```
@Component
public class EmployeeSource {

	@Autowired
	private Source source;

	public void publishEmployeeCountChange(int departmentId, String action) {
		EmployeeCountChangeModel change = new EmployeeCountChangeModel(departmentId, action,
				EmployeeCountChangeModel.class.getTypeName());
		source.output().send(MessageBuilder.withPayload(change).build());
	}

}
```

The Source interface is a convenient interface to use when your service only needs to publish to a single channel. The output() method returns a class of type ```MessageChannel```. The **MessageChannel** is how you’ll send messages to the message broker.


## Setup

Prerequisite needed before setup:

- Zipkin should be started and running

- Kafka should be started and running

- Redis should be started and running

- Git

- GitHub

- Configuration Server should be started and running

- Eureka Server should be started and running

- Gateway Server should be started and running

- OAuth2 Server shhould be started and running

Execute the following commands:

- ```mvn clean install``` (to build the project)
- ```mvn spring-boot:run``` (to run the project)
