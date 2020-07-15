# payroll

*payroll* is an application that manages employees built with microservice architecture.

## Business Case

A company needs web application for managing employees and the departments in which they are organized. The application must be are geared toward reusability and needs to be highly resilient and scalable. 

## Technology

Payroll is using the following technologies:
- Spring Boot
- Spring Data
- H2 Database(for development environment)
- Liquibase(for managing the database changes)
- Lombok(for generating the POJO boilerplate code)
- Spring Cloud Config (for centralizing the storing and accessing of the configuration files for services)
- Spring Cloud Eureka (for registering the services, discovering services, load balancing )
- Spring Cloud Zuul Gateway (for routing, centralizing cross cutting concerns, hiding internal micro service structure )
- Spring Cloud OAuth2 (for implementing the authentication and authorization according the OAuth2 standard)
- Spring Cloud Sleuth (for managing distributed tracing)
- Spring Cloud Zipkin (for visualizing the tracing of transaction)


## Services

Payroll is composed of services:
- Configuration Server
- Eureka Server
- Gateway Server
- OAuth2 Server
- Department
- Employee

### Configuration Server

In microservice architecture our business logic is separated in small services. Each service has its configuration files. As the services number continues to grow, the harder becomes to manage each service configuration information. We have solved this problem by centralizing the configuration information of all services in a single repository. We use the *Configuration Server* service to access the configuration information of the centralized repository. Thus, we are relieved from the chore to write code for accessing the configuration information from the repository. Below is the link where you can find more detailed information about:

[Configuration Server Service](https://github.com/rshtishi/payroll/blob/master/config-server/README.md)

### Eureka Server

Microservices architecture is made of smaller independent services deployed in different machines. *Eureka Server*  is used from services consumers for discovering the IP address of the service. Every instance of service that starts, will register their IP in *Eureka Server*. Netflix Ribbon library provides client-slide load balancing. Periodically, the Netflix Ribbon library will ping the Eureka service and refresh its local cache of service locations. When service will call another service it will use the Netflix Ribbon to retrieve the service location of the service that will be called. In the link below you can find more detailed information
about:

[Eureka Server Service](https://github.com/rshtishi/payroll/blob/master/eureka-server/README.md)

### Gateway Server

In a distributed architecture like a microservices one, you need to implement the security, logging and tracking of the users across multiple service calls. To solve this problems, we need to abstract these cross-cutting concerns into a gateway server that can sit independently and act as a filter and router for all the microservice calls in your application. Your service clients no longer directly call a service. Instead, all calls are routed through the gateway server, which acts as a single Policy Enforcement Point (PEP), and then routed to a final destination. In the link below you can find more detailed information about:

[Gateway Server Service](https://github.com/rshtishi/payroll/blob/master/gateway-server/readme.md)

### OAuth2 Server

In this example of microservice architecture, we have used the **OAUTH2** specification for securing our services. OAuth2 is a token-based security framework that allows a user to authenticate themselves with a third-party authentication server. If the user successfully authenticates, they will be presented with a token that must be sent with every request. The token can then be validated back to the *OAuth2 Server*. The *OAuth2 Server* is the intermediary between the application and the services being consumed. The *OAuth2 Server* allows the user to authenticate themselves without having to pass their user credentials down to every service the application is going to call on behalf of the user. In the link below you can find more detailed information about:

[OAuth2 Server Service](https://github.com/rshtishi/payroll/blob/master/oauth2-server/README.md)


### Employee 

*Employee* service implements the business logic for managing employees. The configuration information for *Employee* is accessed through *Configuration Server*. Whenever a new instance of the *Employee* service is started it automatically registers itself in *Eureka Server*. Thus making the service discoverable by other applications. *Employee* service is a protected resource. A user needs to include in authentication HTTP header containing **OAUTH2 access token** to access the service. To enable distributed tracing we have used spring cloud sleuth to link together transactions across multiple services. We have used Zipkin to understand the flow of transactions. We have used Zipkin for visualization of the flow of a transaction across multiple services.

[Employee Service](https://github.com/rshtishi/payroll/blob/master/employee/README.md)

### Department

*Department* service implements the business logic for managing the departments. It implements the same technology as the employee service for separation of configuration from service code, service discovery, security, and distributed tracing. Department Service makes calls to *Employee* service to retrieve the number of employees for each department. We have used client resiliency software patterns (circuit breakers, fallbacks, and bulkheads) on protecting department service from crashing because the employee service is throwing an error or behaving poorly. We have used **Redis** to cache the number of employees returned from *Employee* service. Also, we have implemented event-driven architecture with **Kafka**. Every time a new employee is created or deleted in employee service it publishes a message to the queue. Department service monitors the queue for any messages published and updates the cache for each message published from *Employee* service.

[Department Service](https://github.com/rshtishi/payroll/blob/master/department/README.md)

## Setup

Prerequisite needed before setup:

- Elasticsearch [version: 7.6.2] should be started and running
- Logstash [version: 7.6.2] should be started and running
- Kibana [version: 7.6.2] should be started and running
- Redis [version: 2.4.5] should be started and running
- Kafka [version:] should be started and running
- Zipkin Server [version: 2.21.1] should be started and running

Build the project:

-```mvn clean install``` (execute the command in payroll directory)

Start the applications as below:

- Configuration Server - ```mvn spring-boot:run``` (execute the command in **config-server directory** to start the Configuration Server)
- Eureka Server - ```mvn spring-boot:run```  (execute the command in **eureka-server directory** to start the Eureka Server)
- Gateway Server - ```mvn spring-boot:run``` (execute the command in **gateway-server directory** to start Gateway Server)
- OAuth2 Server - ```mvn spring-boot:run```  (execute the command in **oauth2-server directory** to start OAuth2 Server)
- Employee - ```mvn spring-boot:run``` (execute the command in **employee directory** to start Employee Service)
- Department - ```mvn spring-boot:run``` (execute the command in **department directory** to start Department Service)

To access configuration information for a specific service:

**Link:** http://localhost:8888/{service}/{environment}

**Example:** ```localhost:8888/employee/default``` [Http method: GET] (retrieving the configuration information for the employee service)

To see the information about service instances that are up and where are they deployed:

**Link:** http://localhost:8761/eureka/apps

To access the routes mapped in Gateway Server:

**Link:** https://localhost:5555/actuator/routes [Http method: GET] (retrieves the routing information)

To authenticate with postman:

- **url:** http://localhost:8901/oauth/token [Http Method: POST]
- **Authentication Type:** Basic [username:payroll, password: test]
- **Body:** Form Data [grant_type:password, scope:webclient, username:rando, password:test]




