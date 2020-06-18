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

### Configuration  Server

In microservice architecture our business logic is separated in  small services that have their own configuration files. 
As the services number continues to grow , the harder becomes to manage each configuration file. To solve this problem we use
Spring Cloud Configuration. We have centralized all the configuration files of the services in Git Repository. They are accessed by services  through the Spring Cloud Server. Each services of the payroll has the Spring Cloud Config Client to access the configuration files through Spring Cloud Config Server.

### Eureka Server

Microservices architecture is made of smaller services deployed in different machines. Therefore, we need to find the physical address of where the machine is located. We use Eureka Server for discovering the service which we need to communicate. Every instance of service that start, will register their ip in Eureka. When service is going to call a another services, it will call the the other service through Netflix Ribbon.


