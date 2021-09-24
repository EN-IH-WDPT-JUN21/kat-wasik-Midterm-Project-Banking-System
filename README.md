# Banking System API

## Getting Started
1. Set up database by running following script in your MySQL Workbench:

```
CREATE DATABASE IF NOT EXISTS bankingSystem;
USE bankingSystem;

CREATE USER IF NOT EXISTS 'ironhacker'@'localhost' IDENTIFIED BY '1r0nh4ck3r';
GRANT ALL PRIVILEGES ON *.* TO 'ironhacker'@'localhost';
FLUSH PRIVILEGES;
```
Aternatively edit application.properties file later to match your existing user.

2. Download or clone repository and open it in IntelliJ IDEA.
3. Run the application by typing command ```mvn spring-boot:run``` in the IntelliJ IDEA terminal
OR run ```main()``` method of the ```BankingSystemApplication.java``` file.
4. Use Basic Auth to test the application in Postman. Use username ```admin``` and password ```password``` to get access as the admin.
5. If you'd like to test the application with a few objects already created uncomment Initializing Bean ```populateDatabase()``` in ```BankingSystemApplication.java``` file and restart the application.

## Endpoints

### Address
|METHOD|ROUTE|ACTION|ACCESS
|---|----|----|---|
|POST|/address|Add a new address|ADMIN
|GET|/address|Get all addresses|ADMIN
|GET|/address/{id}|Get an address by ID|ADMIN
|PUT|/address/{id}|Update an existing address|ADMIN
|DELETE|/address/{id}|Delete an address|ADMIN

#### Request Body parameters for POST and PUT methods
```street``` - cannot be empty or null

```city``` - cannot be empty or null

```postalCode``` - cannot be empty or null

```country``` - cannot be empty or null

### AccountHolder
|METHOD|ROUTE|ACTION|ACCESS
|---|----|----|---|
|POST|/accountholder|Add a new account holder|ADMIN
|GET|/accountholder|Get all account holders|ADMIN
|GET|/accountholder/{id}|Get an account holder by ID|ADMIN
|PUT|/accountholder/{id}|Update an existing account holder|ADMIN
|DELETE|/accountholder/{id}|Delete an existing account holder|ADMIN

#### Request Body parameters for POST and PUT methods
```name``` - cannot be empty or null

```username``` - cannot be empty or null

```password``` - cannot be empty or null, must contain at least one uppercase letter, at least one lowercase letter, at least one digit, at least one special characters and must consist of at least 8 characters

```dateOfBirth``` - cannot be empty or null, date in format: YYYY-MM-DD, date cannot be in the future

```primaryAddressId``` - cannot be empty or null, must consist of at least one digit and only digits

```mailingAddressId``` - can be null, if present then must consist of at least one digit and only digits

### Account
|METHOD|ROUTE|ACTION|ACCESS
|---|----|----|---|
|POST|/account|Add a new account|ADMIN
|GET|/account|Get all accounts|ADMIN
|GET|/account/{id}|Get an account by ID|ADMIN / ACCOUNTHOLDER*
|GET|/account/{id}/balance|Get an account's balance|ADMIN / ACCOUNTHOLDER*
|PUT|/account/{id}|Update an existing account|ADMIN
|PATCH|/account/{id}/status|Change an account's status|ADMIN
|PATCH|/account/{id}/balance|Change an account's balance|ADMIN
|DELETE|/account/{id}|Delete an existing account|ADMIN

*Account Holder needs to be the primary or secondary owner of the account to access the endpoint.

#### Request Body parameters for POST and PUT methods
```balance``` - cannot be empty or null, max 12 digits in integral part and max 2 digits in fraction part of the number

```secretKey``` - cannot be empty or null, must contain at least one uppercase letter, at least one lowercase letter, at least one digit, at least one special characters and must consist of at least 8 characters

```primaryOwnerId``` - cannot be empty or null, must consist of at least one digit and only digits

```secondaryOwnerId``` - can be null, if present then must consist of at least one digit and only digits

### Transaction
|METHOD|ROUTE|ACTION|ACCESS
|---|----|----|---|
|POST|/transaction|Add a new transaction|ACCOUNTHOLDER*
|GET|/transaction|Get all transactions|ADMIN
|GET|/transaction/{id}|Get a transaction by ID|ADMIN / ACCOUNTHOLDER*

*Account Holder needs to be the primary or secondary owner of the sender account to post a transaction and the primary or secondary owner of the sender or receiver account to access the transaction information.

#### Request Body parameters for POST method
```senderAccountId``` - cannot be empty or null, must consist of at least one digit and only digits

```receiverAccountId``` - cannot be empty or null, must consist of at least one digit and only digits

```receiverAccountOwnerName``` - cannot be empty or null

```amount``` - cannot be empty or null, max 12 digits in integral part and max 2 digits in fraction part of the number

## EER Diagram

![eerDiagram.png](https://github.com/EN-IH-WDPT-JUN21/kat-wasik-Midterm-Project-Banking-System/blob/main/src/main/resources/eerDiagram.png)
