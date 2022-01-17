# bcavus-invoice-app
Invoice Application that is responsible of saving and listing invoices.

This github repository holds 3 Java Spring Applications (microservices) and build configurations


Build configurations:

   - Build Pre-Requisite
     * Docker-engine should be up and running at target host/machine (Install & run - Docker or Docker Desktop - )
     * Create environment file for compose build under this directory (File Name: `.env.dev`, Directory: `../bcavus-invoice-app/`)
          Example Environment file for this build: (https://gist.github.com/bcavus/87d0ea9638b39a1d73101f6866af3edc#file-dev-env)

   - Build:
     *	Open project directory (this directory)
     *  Run `docker-compose --env-file .dev.env build --no-cache` . This will build all aplications that is attached to the compose file with given env variables
     *  When docker stopped building, Run `docker-compose --env-file .env.dev up` . This will start the docker containers that is builded successfully.
     *  If this process is successfull, you should be able to see following containers up & running.
	app-rabbitmq,
	user-service,
	user-mongo-db,
        expense-service,
	expense-mongo-db,
        invoice-service,
	invoice-mongo-db.
	    
      (Application Containers Example Image: https://pasteboard.co/Rg2TiuxtXAby.png )	
    

#	SERVICES

	user-service

* Responsible of creating/updating application-user

Dependencies : RabbitMQ Server(app-rabbitmq), MongoDB Database Server(user-mongo-db)

Swagger Support: SUPPORTED , URL : http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

Endpoints: You can see available Rest endpoints through swagger link shared above.

Curl commands: You can see available Curl commands on each representative swagger links when you make default request through swagger link.


	expense-service

* Responsible of creating/updating expense of given application-user

Dependencies : RabbitMQ Server(app-rabbitmq), MongoDB Database Server(expense-mongo-db) 

Swagger Support: SUPPORTED , URL : http://localhost:8082/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

Endpoints: You can see available Rest endpoints through swagger link shared above.

Curl commands: You can see available Curl commands on each representative swagger links when you make default request through swagger link.


	invoice-service
  
* Responsible of creating/updating invoice of given input data

Dependencies : RabbitMQ Server(app-rabbitmq), MongoDB Database Server(invoice-mongo-db) 

Swagger Support: SUPPORTED , URL : http://localhost:8083/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#

Endpoints: You can see available Rest endpoints through swagger link shared above.

Curl commands: You can see available Curl commands on each representative swagger links when you make default request through swagger link.

#     DATABASES

	user-service-db
	
Database server that is responsible of executing `user-service` storage instructions.

Type: MongoDB
Collections: `users`
Users: `admin`, `api_user_service`

	expense-service-db
	
Database server that is responsible of executing `expense-service` storage instructions.

Type: MongoDB
Collections: `expenses`
Users: `admin`, `api_expense_service`

	invoice-service-db

Database server that is responsible of executing `invoice-service` storage instructions.

Type: MongoDB
Collections: `invoices`
Users: `admin`, `api_invoice_service`

# 	MESSAGING

	app-rabbitmq
Messaging Queue Server that is responsible of distributing & persisting messages to the given queues.

Type: RabbitMQ
Active Queues: user-creation-queue, user-validation-queue, expense-validation-queue, invoice-validation-queue.

Image of RabbitMQ : (https://pasteboard.co/p7KFOzBwkFw7.png)
