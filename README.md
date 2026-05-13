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
- Retry mechanism for concurrent requests
- Thread-safe wallet updates

This ensures:
- no balance inconsistency
- no race conditions
- safe high-volume transactions


# API Endpoints

---

# 1. Create Wallet

Creates a new wallet with an initial balance.

## Endpoint

```http
POST /api/v1/wallets/save
```

## Request Body

```json
{
  "amount": 100
}
```

## Success Response

```json
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
```

---

# 2. Update Wallet Balance

Updates wallet balance using deposit or withdraw operation.

## Operation Types

| Value | Operation |
|---|---|
| 1 | Deposit |
| 2 | Withdraw |

---

## Deposit Amount

### Endpoint

```http
PUT /api/v1/wallets/update
```

### Request Body

```json
{
  "walletId": 1,
  "balance": 200,
  "operationType": 1
}
```

### Success Response

```json
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
```

---

## Withdraw Amount

### Request Body

```json
{
  "walletId": 1,
  "balance": 100,
  "operationType": 2
}
```

### Success Response

```json
{
  "data": {
    "walletId": 1,
    "balance": 200,
    "operationType": 2,
    "createdOn": "2026-05-13T21:34:00.574853",
    "createdBy": 1,
    "modifiedBy": 1,
    "modifiedOn": "2026-05-13T21:55:12.123456"
  },
  "status": "OK",
  "message": "Wallet updated successfully",
  "error": false,
  "timestamp": "2026-05-13T21:55:12.123456"
}
```

---

# 3. Get Wallet By ID

Fetch wallet details and current balance using wallet ID.

## Endpoint

```http
GET /api/v1/wallets/{walletId}
```

## Example

```http
GET /api/v1/wallets/1
```

## Success Response

```json
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
```

---

# Validations Implemented

- Wallet ID validation
- Positive balance validation
- Insufficient balance validation
- Invalid operation type validation
- Concurrent transaction handling
- Exception handling with proper API responses

```
