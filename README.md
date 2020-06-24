# payroll

Payroll is application that is being build using the micro-service architecture.

## Business Case

A company needs web application for managing employees and the deparments in which they are organized. The application must be 
horizontally scalable.

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


## Services

Payroll is composed of services:
- Configuration Server
- Eureka Server
- Gateway Server
- OAuth2 Server
- Department Service
- Employee Service

### Configuration Server

In microservice architecture our business logic is separated in small services. Each service has its configuration files. As the services number continues to grow, the harder becomes to manage each configuration information. We have solved this problem by centralizing the configuration information of all services in a single repository. We use the *Configuration Server* service to access the configuration information of the centralized repository. Thus, we are relieved from the chore to write code for accessing the configuration information from the repository. Below is the link where you can find more detail about the *Configuration Server* service:

[Configuration Server Service](https://github.com/rshtishi/payroll/tree/master/config-server)

### Eureka Server

Microservices architecture is made of smaller services deployed in different machines. Therefore, we need to find the physical address of where the machine is located. We use Eureka Server for discovering the ip address of the service that we need to call. Every instance of service that start, will register their ip in Eureka. Netflix Ribbon library to provide client-slide load balancing. Ribbon will contact the Eureka service to retrieve service location information and then cache it locally. Periodically, the Netflix Ribbon library will ping the Eureka service and refresh its local cache of service locations. When service will call another service it will use the Netflix Ribbon to retrieve the service location of the service that will be called.

### Gateway Server

In a distributed architecture like a microservices one, you need to implement the security, logging and tracking of the users across multiple service calls. To solve this problems, we need to abstract these cross-cutting concerns into a gateway server that can sit independently and act as a filter and router for all the microservice calls in your application. Your service clients no longer directly call a service. Instead, all calls are routed through the gateway server, which acts as a single Policy Enforcement Point (PEP), and then routed to a final destination.

### OAuth2 Server

In this example of microservice architecture we have used the OAUTH2 for securing our services. OAuth2 is a token-based security framework that allows a user to authenticate themselves with a third-party authentication server. If the user successfully authenticates, they will be presented a token that must be sent with every request. The token can then be validated back to the authentication server. The OAuth2 authentication server is the intermediary between the application and the services being consumed. 
The OAuth2 server allows the user to authenticate themselves without having to pass their user credentials down to every service the application is going to call on behalf of the user.

### Employee Service

Employee Service implements the business logic for managing employees. Environment configuration properties are separated from the service code and runtime code through the use configuration server. Whenever a new instance of the employee service is started it automatically register itself in Eureka Server. Thus making the service discoverable by other application clients. Employee Service is protected resource, a user needs to include in authentication HTTP header containing OAUTH2 access token in order to access the service.  To enable distributed tracing we have used spring cloud sleuth to link together transaction across multiple services. ELK(Elasticsearch Logstash Kibana) is used to aggregate logs from multiple services into a single searchable source. We have used zipkin to understand the flow of transaction. We have used Zipkin for visualization of the flow of a transaction across multiple services.

### Department Service

Department Service implements the business logic for managing the departments. It implements the same technology as the employee service for separation of configuration from service code, service discovery, security and distributed tracing. Department Service makes calls to employee service to retrieve number of employee for each department. We have used client resiliency software patterns (circuit breakers, fallbacks and bulkheads) on protecting department service from crashing because the employee service is throwing error or behaving poorly. We have used Redis cache to cache the value of employee number returned from employee service. Also we have implemented event driven architecture with kafka. Everytime a new employee is created in employee service it publish a message to the queue. Department service monitors the queue for any messages published and updates the cache for each message published from employee service.

## Setup

Prerequisite needed before setup:

- Elasticsearch [version: 7.6.2]
- Logstash [version: 7.6.2]
- Kibana [version: 7.6.2]
- Redis [version: 2.4.5]
- Kafka [version:]
- Zipkin Server [version: 2.21.1]





