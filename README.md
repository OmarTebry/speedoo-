# Speedo Transfer Service
===============================

## Introduction

The Money Transfer Service is a basic Service for creating and managing transactions. The service provides a simple and intuitive way to transfer funds between accounts.

## API Endpoints

### Create Transaction

* **POST /transactions**
* Request Body: `Transaction` object
* Response: `201 Created` with the created transaction ID

### Get Transaction by ID

* **GET /transactions/{id}**
* Path Parameter: `id` (unique identifier for the transaction)
* Response: `200 OK` with the transaction details

### Get List of Transactions

* **GET /transactions**
* Response: `200 OK` with a list of transaction objects

### Update Transaction

* **PUT /transactions/{id}**
* Path Parameter: `id` (unique identifier for the transaction)
* Request Body: `Transaction` object
* Response: `200 OK` with the updated transaction details

### Delete Transaction

* **DELETE /transactions/{id}**
* Path Parameter: `id` (unique identifier for the transaction)
* Response: `204 No Content`

## Request and Response Bodies

The API endpoints expect and return JSON data in the following formats:

### Transaction Object

* `id`: Unique identifier for the transaction
* `amount`: The amount of the transaction
* `sender`: The sender's account information
* `receiver`: The receiver's account information
* `status`: The status of the transaction (e.g., pending, completed, failed)

## HTTP Status Codes

The API endpoints return standard HTTP status codes to indicate the outcome of each request:

* `201 Created`: Transaction created successfully
* `200 OK`: Transaction retrieved or updated successfully
* `404 Not Found`: Transaction not found
* `500 Internal Server Error`: Error occurred while processing the request

## Getting Started

To use the Money Transfer Service API, 
simply send HTTP requests to the API endpoints using your preferred programming language or tool. 
Make sure to include the necessary headers and request bodies as described above.