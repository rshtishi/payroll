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


## Architecture

## Implementation Details

## Setup

