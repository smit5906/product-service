# myRetail RESTful service Case Assessment

The ProductService is a RESTful Spring Boot service that runs in conjunction with a local MongoDB instance. It's main functions are to accept a product id and to either return a product's name and pricing information, or update a product's information.

## To run the application

### 1. Confirm Java 8 Version
Open a command prompt and enter the following command
```
java -version
```
If your java version is 1.8 then continue. Otherwise follow the instructions [here](https://java.com/en/download/win10.jsp) to update.

### 2. Clone the project from Git.
To install git, [click here.](https://git-scm.com/downloads)

Once git is installed, clone the repository by typing the following in command prompt
```
$ git clone https://github.com/smit5906/product-service.git
```
This will install the repository in your current home folder

### 3. Install MongoDB
To intall MongoDB, [click here.](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)

Once MongoDB is installed, traverse to the \bin folder where mongodb is installed

```
cd mongodb\bin
```
Then type the following
```
mongod
```
Once the server has started, type the following to create a new database
```
use test
```
Type the following command to add a document in the database
```
db.getCollection('Product').save({ "pid": 13860428, "title": "The Big Lebowski (Blu-ray)", "price": "13.49", "currencyCode": "USD" })
```
### 4. Use Maven to build executable
To install Maven, [click here.](https://maven.apache.org/download.cgi)

Once maven is installed, build the ProductService application. (Make sure you are in the application folder such as \Users\(name)\product-service)
```
mvn clean install -U
```
### 5. Run the application
Once built, navigate to the newly created /target folder using command prompt
```
cd target
```
Type the following command to run the application
```
java -jar ProductService-0.0.1-SNAPSHOT.jar
```
The application will start to run

### 6. Call the ProductService resources
Use your favorite API Rest Client (SoapUI used during case study completion, to install, [click here](https://www.soapui.org/downloads/latest-release.html))

The service is configured to run locally at http://localhost:8080/products

A GET request to the /products/{id} resource gets price information from MongoDB and the product name from the redsky Target API, and then returns a JSON Response.
```
  GET   http://localhost:8080/products/13860428
  Content-Type: application/json
```
Response
```
{
   "id": 13860428,
   "name": "The Big Lebowski (Blu-ray)",
   "current_price":    {
      "value": 13.49,
      "currency_code": "USD"
   }
}
```
A PUT request to the /products/{id} resource updates the price of an existing product in MongoDB
```
PUT http://localhost:8080/products/13860428
Content-Type: application/json
```
Request:
```
{
   "id": 13860428,
   "name": "The Big Lebowski (Blu-ray)",
   "current_price":    {
      "value": 9.49,
      "currency_code": "USD"
   }
}
```
Response (with updated price value)
```
{
   "id": 13860428,
   "name": "The Big Lebowski (Blu-ray)",
   "current_price":    {
      "value": 9.49,
      "currency_code": "USD"
   }
}
```
