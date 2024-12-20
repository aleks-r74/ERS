# Expense Reimbursement System (BJP)

The Expense Reimbursement System manages the process of reimbursing employees for expenses incurred while on company time. Employees in the company can login, submit requests for reimbursements, and view their past tickets and pending tickets. Finance Managers can log in and view all reimbursement requests and history for all employees in the company. Finance managers are authorized to approve and deny requests for expense reimbursement, as well as view all employees and delete employees.

The React front end sends axios HTTP Requests to the Spring-based back end, which communicates with a Postgres Database to return HTTP Responses. An AWS RDS may optionally be spun up in order for teammates to share the same dataset.

## Roles / Responsibilities

- Created the back-end server using Spring Boot and Spring Data JPA with a simplified authentication mechanism for demonstration purposes.
- Developed a front-end application with React that sends and receives requests from the back-end and renders the data.
- Designed the database schema using Spring Data JPA entities.
- Implemented data persistence with PostgreSQL.
# ERS
