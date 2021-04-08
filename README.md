# DigIO
##### This application aims to parse a log file containing HTTP Requests to extract useful insights and answer the following questions - 
- The number of unique IP addresses
- The top 3 most visited URLs
- The top 3 most active IP addresses

## Tech
Application uses the following tech stack

- Java
- Gradle
- Docker
- JUnit
- Mockito

## Features

- Application is composed of two software components _digio-log-insights_ and _component-test_ where each component is represented by a separate Gradle build.
- Application uses check style during build phase to ensure code is readable and production standard.
- Can be packaged as a docker container.



## Solution

 - It uses Regex to parse the log lines and divides it into 12 groups to gather the log insights. Some important 
	groups include - 		
	 - Group 1 - IP Address
	 - Group 3 - User accessing the resource
	 - Group 4 - Date
	 - Group 6 - Url
	 - Group 8 - Response Code
 - Application exposes an interface to build Ip Address and Url Maps in the form - 
	 Map<String, ApacheLogAttributes> where key is Ip address or the Url.
 - The maps are sorted by count and then by the accessed date.
 - Task questions are answered by accessing the key and values in the Map.

## Building and Running

##### Build the application

 
```sh
./gradlew clean digio-log-insights:build
```
This will - 

	 - Perform the checkstyle
	 - Run the unit tests
	 - Generate a Jar file of the application

##### Run the Component tests

```sh
./gradlew clean component-test:build
```
This will - Run the whole application as a component

##### Run the Application using Jar file
```sh
java -jar digio-log-insights/build/libs/digio-log-insights-1.0-SNAPSHOT.jar
```

##### Building the Docker image
```sh
Build the application - ./gradlew digio-log-insights:build
docker build -t digi .
docker run digi
```


