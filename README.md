# Switchfully

www.switchfully.com

## Örder technical implementation

-  Large exercise to conclude the following modules:
    - Dependency Management
    - Spring (boot)
    - REST
    - ORM & JPA
        - No use of Spring Data Repositories
- JDK 15
- Contains a full working solution of Örder.

## Usage

### Running Locally
- Multiple ways of running:
    - Run `Application.java` inside the IDE
    - Or, Inside the target folder of module `war`, run the `war-1.0-SNAPSHOT.jar` using the `java -jar` command.
        - But first, you will have to run command `mvn clean package` to generate the `.jar` file.
    - Or, execute command `mvn spring-boot:run` from inside module (folder) `war`
- Surf to `http://localhost:9000/customers` to verify the backend is running.

## OpenAPI / Swagger Documentation
- Read the generated documentation of our Web API on `http://localhost:9000/swagger-ui.html`

## H2 Database
The application will start an H2 in-memory Database. 
- As long as the application is running, the data is persisted.
- The moment the application is shutdown, the data and database are wiped

You can access the H2 Database by surfing to:
 - `http://localhost:9000/h2-console`
 
 As the JDBC-url, enter `jdbc:h2:mem:testdb`

## Some example REST Endpoints

### Customers
- **Get Customer for ID**
    - Path: `/customers/{id}`
    - Methods: [GET]
    - Produces: [application/json]
- **Create a new Customer**
    - Path: `/customers`
    - Methods: [POST]
    - consumes: [application/json]
    - Produces: [application/json]
    - Example
        - Call: `http://localhost:9000/customers/`
        - Request Body:
            ```json
            {
                "firstname": "Jucky",
                "lastname": "Chun",
                "email": {
                    "localPart": "jucky.chun",
                    "domain": "kangfa.com",
                    "complete": "jucky.chun@kangfa.com"
                },
                "address": {
                    "streetName": "Livablestreet",
                    "houseNumber": "85D",
                    "postalCode": "3052TK",
                    "country": "China"
                },
                "phoneNumber": {
                    "number": "488522210",
                    "countryCallingCode": "+32"
                }
            }
            ```
- **Get all Customers**
    - Path: `/customers`
    - Methods: [GET]
    - Produces: [application/json]

### Items
- **Create a new Item**
    - Path: `/items`
    - Methods: [POST]
    - consumes: [application/json]
    - Produces: [application/json]
    - Example 
        - Call: `http://localhost:9000/items/`
        - Request Body:
            ```json
            {
                "name": "Headphone",
                "description": "This is a cool headphone",
                "price": 49.95,
                "amountOfStock": 10
            }
            ```
- **Update the specified Item**
    - Path: `/items/{id}`
    - Methods: [PUT]
    - consumes: [application/json]
    - Produces: [application/json]
    - Example
        - Call: `http://localhost:9000/items/7287faca-y0ur-0wn1d-891f-1f102b14ce3f`
        - Request Body:
            ```json
            {
                "name": "Headphone",
                "description": "Coolest headphone on the market!",
                "price": 35.45,
                "amountOfStock": 10
            }
            ```

### Orders
- **Create a new Order**
    - Path: `/orders`
    - Methods: [POST]
    - consumes: [application/json]
    - Produces: [application/json]
        - Call: `http://localhost:9000/orders`
        - Request Body:
            ```json
            {
                "customerId": "e34d90ca-ecc3-49c3-ab6b-c9b353f5fef3",
                "itemGroups": [
                    {
                        "itemId": "de2f9283-1f82-44ad-a869-3b2f80f73724",
                        "orderedAmount": 1
                    },
                    {
                        "itemId": "7287faca-25c9-43f8-891f-1f102b14ce3f",
                        "orderedAmount": 2
                    }
                ]
            }
            ```
- **get all Orders**
    - Path: `/orders`
    - Methods: [GET]
    - Produces: [application/json]
- **Get all orders for the specified customer**
    - Path: `/orders/customers/{customerId}`
    - Methods: [GET]
    - Produces: [application/json]
