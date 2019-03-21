myRetail RESTful service Case Assessment

ProductService App is configured to run as a Spring Boot application in conjunction with a local MongoDB instance.

To run the application

1. Confirm Java 8 Version
Open your terminal/command prompt and enter the following command

java -version
If your java version is 1.8 then continue. Otherwise follow the instructions here to update.

2. Clone the project from Git.
If you do not have git installed, click here.

Once git is installed, clone the project from git

In terminal/command prompt, type

$ git clone https://github.com/smit5906/product-service.git

This will install the directory on your home folder

3. Install MongoDB
If you don't have mongoDB installed, refer here

Once installed, enter the following command in your terminal/command prompt

mongod
This will start the Mongo server.

Open up a new terminal/command prompt and type the following command

mongo
Once Mongo has started, create a new database with the following command

use test
And then enter the following command to add an entry in the database

db.getCollection('Product').save({ "pid": 13860428, "title": "The Big Lebowski (Blu-ray)", "price": "13.49", "currencyCode": "USD" })

4. Use Maven to build executable
If you do not have maven installed, you can install it here.

Once maven is installed, build the project. (Make sure you are in the folder (ex: /User/Folder/Target))

mvn clean install -U
5. Run the application
After maven has finished building and was succesful, you'll now have a target folder in your project folder. Navigate inside this folder using terminal/command prompt

cd target
Run the following command to start the application

java -jar ProductService-0.0.1-SNAPSHOT.jar
The application is running

6. Run the RESTful service
Use your favorite API Rest Client (SoapUI used during case study completion)


This Rest Service aggregates price information from MongoDB and product Title from external Target API and provides a JSON Response.
  GET   http://localhost:8080/products/13860428
  Content-Type: application/json
Response

{
   "id": 13860428,
   "name": "The Big Lebowski (Blu-ray)",
   "current_price":    {
      "value": 13.49,
      "currency_code": "USD"
   }
}
This Rest Service is used to update the price of an existing product in MongoDB
PUT http://localhost:8080/products/13860428
Content-Type: application/json
Request:
{
   "id": 13860428,
   "name": "The Big Lebowski (Blu-ray)",
   "current_price":    {
      "value": 9.49,
      "currency_code": "USD"
   }
}

Response

{
   "id": 13860428,
   "name": "The Big Lebowski (Blu-ray)",
   "current_price":    {
      "value": 9.49,
      "currency_code": "USD"
   }
}
