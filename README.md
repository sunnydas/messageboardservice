Simple Message Board Service  
===============================

## Assumptions

1. APIs do not use any security mechanism like Basic or OAuth for authentication/authorization of clients.
2. Gradle wrapper is being used as the build tool (tested on 4.10).
3. In memory database being used for persistence.

## Description
The message board service provides the following REST APIs:

1. Create client.
   This API provides functionality of basic client creation. A client is most commonly represented as a user.
   ```
       Resource: api/v1/messageboard/clients
	   HTTP Verb: POST
	   Request Body Example: 
	   {
		"clientName": "new_sunny",
		"clientDescription": "trial client",
		"clientType": "USER" 
	   }
	   Content Type: application/json
	   Response Example: 
	   {
		"clientId": 6,
		"clientName": "new_sunny",
		"clientDescription": "trial client",
		"clientType": "USER",
		"createdDate": "2020-01-06 17:50:01",
		"updateDate": "2020-01-06 17:50:01"
	   } 
	   Response Code: 201
   ```   
    
2. Create message.
   This API provides the functionality of creating a message on behalf of a client.
   ```
       Resource: /api/v1/messageboard/messages/{clientId}
	   HTTP Verb: POST
	   Request Body Example: 
	   {
		"messageText": "hello world",
		"messageTitle": "My first message"
	   }
	   Content Type: application/json
	   Response Example:
	   {
		"messageId": 1,
		"clientId": 1,
		"creationDate": "2020-01-06 17:57:06",
		"updateDate": "2020-01-06 17:57:06",
		"messageText": "hello world",
		"messageTitle": "My first message"
	   }
	   Response code: 201
   ```    
3. Modify message.
   This API provides the functionality of modifying a message on behalf of a client.
   ```
       Resource: /api/v1/messageboard/messages/{clientId}/{messageId}
	   HTTP Verb: PUT
	   Request Body Example: 
	   {
		"messageText": "hello world",
		"messageTitle": "My first message"
	   }
	   Content Type: application/json
	   Response Example:
	   {
		"messageId": 1,
		"clientId": 1,
		"creationDate": "2020-01-06 17:57:06",
		"updateDate": "2020-01-06 17:57:06",
		"messageText": "hello world",
		"messageTitle": "My first message"
	   }
	   Response code: 200
   ```    
4. Delete message.
   This API provides the functionality of deleting a message on behalf of a client.
   ```
       Resource: /api/v1/messageboard/messages/{clientId}/{messageId}
	   HTTP Verb: DELETE
	   Response code: 204
   ```   
5. View all messages.
   This API provides the functionality of viewing all messages in the service.
   ```Resource: /api/v1/messageboard/messages
	   HTTP Verb: GET
	   Content Type: application/json
	   Response Example:
	  [
		{
			"messageId": 2,
			"clientId": 1,
			"creationDate": "2020-01-06 18:05:29",
			"updateDate": "2020-01-06 18:05:29",
			"messageText": "hello world",
			"messageTitle": "My first message"
		},
		{
			"messageId": 3,
			"clientId": 1,
			"creationDate": "2020-01-06 18:05:35",
			"updateDate": "2020-01-06 18:05:35",
			"messageText": "hello world",
			"messageTitle": "My first message"
		}
	   ]
	   Response code: 200       
   ```    
## Build/Execute 

1. Build
   ```
      gradlew clean build
   ``` 

2. Run
   ```
      gradlew bootRun
   ```    

3. Build Docker image
   ```
      gradlew jibDockerBuild --image=sunny/message-board-docker 
   ```    

4. Run docker based container
   ```
      docker run -p 9099:9099 -t sunny/message-board-docker
   ```    
## Run Instructions

1. By default the application runs on port 9090, for example:
   ```
      localhost:9099/api/v1/messageboard/messages/1
   ```
   
## Technology stack

1. Java 
2. SpringBoot
3. H2   
