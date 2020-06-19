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

In microservice architecture our business logic is separated in  small services that have their own configuration files. 
As the services number continues to grow , the harder becomes to manage each configuration file. To solve this problem we use
Spring Cloud Configuration. We have centralized all the configuration files of the services in Git Repository. They are accessed by services  through the Spring Cloud Server. Each services of the payroll has the Spring Cloud Config Client to access the configuration files through Spring Cloud Config Server.

### Eureka Server

Microservices architecture is made of smaller services deployed in different machines. Therefore, we need to find the physical address of where the machine is located. We use Eureka Server for discovering the ip address of the service that we need to call. Every instance of service that start, will register their ip in Eureka. Netflix Ribbon library to provide client-slide load balancing. Ribbon will contact the Eureka service to retrieve service location information and then cache it locally. Periodically, the Netflix Ribbon library will ping the Eureka service and refresh its local cache of service locations. When service will call another service it will use the Netflix Ribbon to retrieve the service location of the service that will be called.

### Gateway Server

In a distributed architecture like a microservices one, you need to implement the security, logging and tracking of the users across multiple service calls. To solve this problems, we need to abstract these cross-cutting concerns into a gateway server that can sit independently and act as a filter and router for all the microservice calls in your application. Your service clients no longer directly call a service. Instead, all calls are routed through the gateway server, which acts as a single Policy Enforcement Point (PEP), and then routed to a final destination.

### OAuth2 Server

In this example of microservice architecture we have used the OAUTH2 for securing our services. OAuth2 is a token-based security framework that allows a user to authenticate themselves with a third-party authentication server. If the user successfully authenticates, they will be presented a token that must be sent with every request. The token can then be validated back to the authentication server. The OAuth2 authentication server is the intermediary between the application and the services being consumed. 
The OAuth2 server allows the user to authenticate themselves without having to pass their user credentials down to every service the application is going to call on behalf of the user.

### Employee Service

### Department Service
