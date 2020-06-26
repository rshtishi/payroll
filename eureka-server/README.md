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

## Setup

