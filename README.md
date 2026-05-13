# Banking Management System
A high-performance Banking Management System built using Java, Spring Boot, PostgreSQL, Liquibase, Docker, and Docker Compose.
The application provides secure wallet/account operations such as:
- Wallet Creation
- Deposit Amount
- Withdraw Amount
- Get Wallet Balance
The system is designed to handle concurrent transactions safely using optimistic locking and retry mechanisms.

# Technologies Used
- Java 11
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Liquibase
- MapStruct
- Maven
- Docker
- Docker Compose
- 
# Features

## 1. Create Wallet
- Create a new wallet/account
- Initial deposit supported
- Duplicate wallet validation
- Positive amount validation

## 2. Deposit Amount
- Add money to wallet
- Concurrent transaction handling
- Transaction validations

## 3. Withdraw Amount
- Withdraw money from wallet
- Prevents negative balance
- Insufficient balance validation

## 4. Get Wallet Details
- Fetch wallet details by wallet ID
- Retrieve current balance

# Concurrency Handling

Implemented:
- Optimistic Locking using `@Version`
- Retry mechanism for concurrent requests
- Thread-safe wallet updates

This ensures:
- no balance inconsistency
- no race conditions
- safe high-volume transactions


# API Endpoints

## Create Wallet

### Request
POST - /api/v1/wallets/save

--json
{
  "amount": 100
}

--response:
{
  "data": {
    "walletId": 1,
    "balance": 100,
    "operationType": 1,
    "createdOn": "2026-05-13T21:34:00.5748527",
    "createdBy": 1,
    "modifiedBy": null,
    "modifiedOn": null
  },
  "status": "CREATED",
  "message": "Wallet created successfully",
  "error": false,
  "timestamp": "2026-05-13T21:34:00.6363702"
}


PUT - /api/v1/wallets/update

--json
{
  "balance": 200,
  "operationType": 1,
  "walletId": 1
}

--response
{
  "data": {
    "walletId": 1,
    "balance": 300,
    "operationType": 1,
    "createdOn": "2026-05-13T21:34:00.574853",
    "createdBy": 1,
    "modifiedBy": 1,
    "modifiedOn": null
  },
  "status": "OK",
  "message": "Wallet updated successfully",
  "error": false,
  "timestamp": "2026-05-13T21:50:26.8934843"
}


GetById - /api/v1/wallets/1

--response:
{
  "data": {
    "walletId": 1,
    "balance": 500,
    "operationType": 1,
    "createdOn": "2026-05-13T21:34:00.574853",
    "createdBy": 1,
    "modifiedBy": 1,
    "modifiedOn": "2026-05-13T21:52:56.469874"
  },
  "status": "OK",
  "message": "Wallet fetched successfully",
  "error": false,
  "timestamp": "2026-05-13T22:00:05.7613457"
}
