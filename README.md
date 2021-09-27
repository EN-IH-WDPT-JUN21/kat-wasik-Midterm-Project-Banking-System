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

## Functionality

On the application startup the Admin account gets created. The Admin can then create new Addresses, Account Holders and Accounts.

The system have 3 types of accounts: Checking Accounts, Student Checking Accounts and Savings Account. While a new account is created by the Admin, the Primary (Account) Owner's age is evaluated. If the Primary Owner is less than 24, a Student Checking Account is created. Otherwise a regular Checking Account is created. Savings Account are created using different endpoint.

In contrast to Student Checking Accounts, regular Checking Accounts have a Monthly Maintenance Fee (default value 12) and a Minimum Balance (default value 250). A Monthly Maintenance Fee gets calculated and applied if it has passed at least 1 month since a Checking Account was last accessed. A Penalty Fee gets applied if a Checking Account's balance drops below a Minimum Balance.

Savings Accounts do not have a Monthly Maintenance Fee, but instead have an Interest Rate. An Interest gets calculated and added if it has passed at least 1 year since a Savings Account was last accessed. Default value for Interest Rate is 0.0025, but may be set to any value between 0 and 0.5 during the Savings Account creation. Default value for Savings Account Minimum Balance is 1000, but may be set to any value between 100 and 1000 during Savings Account creation.

The default status for newly created Account is ACTIVE, but can be later changed to FROZEN by the Admin.

Account Holders that are Primary or Secondary Owners of the Accounts can transfer money to other Accounts. Transactions are successfully added if there are sufficient funds and the Sender Account is not FROZEN. If, after the transaction, the Sender Account's balance drops below the minimum balance (default value 250), penalty fee gets deducted (not applicable to Student Checking Accounts).

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
All values to be passed as strings.

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
All values to be passed as strings.

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
|POST|/account/savings|Add a new savings account|ADMIN
|GET|/account|Get all accounts|ADMIN
|GET|/account/{id}|Get an account by ID|ADMIN / ACCOUNTHOLDER*
|GET|/account/{id}/balance|Get an account's balance|ADMIN / ACCOUNTHOLDER*
|PUT|/account/{id}|Update an existing account|ADMIN
|PUT|/account/savings/{id}|Update an existing savings account|ADMIN
|PATCH|/account/{id}/status|Change an account's status|ADMIN
|PATCH|/account/{id}/balance|Change an account's balance|ADMIN
|DELETE|/account/{id}|Delete an existing account|ADMIN

*Account Holder needs to be the primary or secondary owner of the account to access the endpoint.

#### Request Body parameters for POST and PUT methods
All values to be passed as strings.

```balance``` - cannot be empty or null, max 12 digits in integral part and max 2 digits in fraction part of the number

```secretKey``` - cannot be empty or null, must contain at least one uppercase letter, at least one lowercase letter, at least one digit, at least one special characters and must consist of at least 8 characters

```primaryOwnerId``` - cannot be empty or null, must consist of at least one digit and only digits

```secondaryOwnerId``` - can be null, if present then must consist of at least one digit and only digits

```minimumBalance``` (only for Savings Accounts) - can be null, if present must consist of only digits, max 12 digits in integral part and max 2 digits in fraction part of the number; values between 100 and 1000 accepted

```interestRate``` (only for Savings Accounts) - can be null, if present must consist of only digits, max 1 digit in integral part and max 4 digits in fraction part of the number; values between 0 and 0.5 accepted


### Transaction
|METHOD|ROUTE|ACTION|ACCESS
|---|----|----|---|
|POST|/transaction|Add a new transaction|ACCOUNTHOLDER*
|GET|/transaction|Get all transactions|ADMIN
|GET|/transaction/{id}|Get a transaction by ID|ADMIN / ACCOUNTHOLDER*

*Account Holder needs to be the primary or secondary owner of the sender account to post a transaction and the primary or secondary owner of the sender or receiver account to access the transaction information.

#### Request Body parameters for POST method
All values to be passed as strings.

```senderAccountId``` - cannot be empty or null, must consist of at least one digit and only digits

```receiverAccountId``` - cannot be empty or null, must consist of at least one digit and only digits

```receiverAccountOwnerName``` - cannot be empty or null

```amount``` - cannot be empty or null, max 12 digits in integral part and max 2 digits in fraction part of the number

## EER Diagram

![eerDiagram.png](https://github.com/EN-IH-WDPT-JUN21/kat-wasik-Midterm-Project-Banking-System/blob/main/src/main/resources/eerDiagram.png)
