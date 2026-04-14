This project is a full-stack E-Commerce web application developed using a microservices architecture. It allows users to browse products, place orders, and complete secure payments, while providing administrators with full control over product management. The system is designed to be scalable, modular, and secure by separating functionalities into independent services.

The backend is built using Java and Spring Boot, with services such as Product Service, Order Service, Payment Service, and Authentication Service. These services communicate via REST APIs and Feign Clients, ensuring smooth inter-service communication. Spring Security with JWT authentication is implemented to provide secure access and role-based authorization for Admin and User roles.

The frontend is developed using Angular with Bootstrap, providing a responsive and user-friendly interface. Users can view products, place orders, track order details, and make payments. Admin users can perform full CRUD operations on products, including adding, updating, deleting, and viewing product data.

A key highlight of this project is the integration of the Razorpay payment gateway, enabling real-time payment processing. The system supports complete payment flow, including order creation, payment initiation, and payment verification, along with handling error scenarios.

The application uses MySQL for data persistence and follows best practices like layered architecture, DTO usage, and proper exception handling. API testing is done using Postman, and version control is managed with Git and GitHub.

This project demonstrates strong understanding of full-stack development, microservices architecture, REST APIs, secure authentication, and payment gateway integration, making it a practical and industry-relevant implementation.
