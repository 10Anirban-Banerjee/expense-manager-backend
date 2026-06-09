# Expense Manager

A full-stack Expense Manager application built using Spring Boot, React, PostgreSQL, Redis, JWT Authentication, and Docker.

## Features

- User Registration & Login
- JWT Authentication
- Protected Routes
- Create Expense
- Update Expense
- Delete Expense
- Search Expenses
- Sort Expenses
- Date Filtering
- Pagination
- Redis Caching
- Responsive UI
- Dockerized Setup

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Redis
- Maven

### Frontend
- React
- React Router
- Axios
- Vite

### DevOps
- Docker
- Docker Compose

## Project Structure

Backend:
expensemanager/
├── controller
├── service
├── repository
├── entity
├── dto
├── security
└── exception

Frontend:
expense-manager-frontend/
├── components
├── pages
├── services
└── App.jsx

## Running Locally

Backend:

```bash
docker compose up --build
```

Frontend:

```bash
npm install
npm run dev
```

## API Endpoints

### Authentication

POST /auth/register

POST /auth/login

### Expenses

GET /expenses

POST /expenses

PUT /expenses/{id}

DELETE /expenses/{id}

## Future Enhancements

- Expense Categories
- Monthly Reports
- Expense Charts
- Export to Excel/PDF
- Dark Mode

## Author

Anirban Banerjee