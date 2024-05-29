# SmartphoneApp

## Overview
The following repo contains a example of a microservice of spring boot.

## Guidelines
Run the microservice following next steps

1. Clone this repository in your workspace

2. Execute the next command for download dependencies and compile project:
 
 ```
    mvn clean install
 ```

3. For local environment, if you want to test the application,
   then you can start the mocks with the following command in the root of the project.

 ```
    docker-compose up -d mocks
 ```

4. We could now run the microservice with the next command:

 ```
    mvn spring-boot:run
 ```

### Swagger access in Local environment

- You can access to Swagger ui from:

   ```
     http://localhost:5000/swagger-ui/index.html
   ```