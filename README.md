# Project setup

### SDK
#### You need to configure the project sdk, you need to open settings  - file -> project structure -> project sdk
#### You need to download and set adopted-openjdk-14 version 14.0.2
### For project language level you need to set  - 11

### Configure the properties and profiles:
#### You can configure your own properties or override some of them. In directory src/main/resources you can find default property file application.yml
#### You can add your own , e.g  - application-dev.yml and after that i can apply my config by adding a profile when start the project

### Docker
#### In root directory tou can find docker-compose.yml, and you need to start rabbitmq and db services

### Starting 
#### After previous steps you can start the project