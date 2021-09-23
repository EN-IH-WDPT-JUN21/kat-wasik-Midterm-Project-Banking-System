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

##Endpoints

### Address
|METHOD|ROUTE|ACTION|PERMISSION
|---|----|----|---|
|POST|/address|Add a new address|ADMIN
|GET|/address|Get all addresses|ADMIN
|GET|/address/{id}|Get an address by ID|ADMIN
|PUT|/address/{id}|Update an existing address|ADMIN
|DELETE|/address/{id}|Delete an address|ADMIN

### AccountHolder
|METHOD|ROUTE|ACTION|PERMISSION
|---|----|----|---|
|POST|/accountholder|Add a new account holder|ADMIN
|GET|/accountholder|Get all account holders|ADMIN
|GET|/accountholder/{id}|Get an account holder by ID|ADMIN
|UPDATE|/accountholder/{id}|Update an existing account holder|ADMIN
|DELETE|/accountholder/{id}|Delete an existing account holder|ADMIN

### Account
|METHOD|ROUTE|ACTION|PERMISSION
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

### Transaction
|METHOD|ROUTE|ACTION|PERMISSION
|---|----|----|---|
|POST|/transaction|Add a new transaction|ACCOUNTHOLDER*
|GET|/transaction|Get all transactions|ADMIN
|GET|/transaction/{id}|Get a transaction by ID|ADMIN / ACCOUNTHOLDER*

*Account Holder needs to be the primary or secondary owner of the sender account to post a transaction and the primary or secondary owner of the sender or receiver accounts to access the transaction information.