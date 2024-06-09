Kitchen Story:
E-Commerce Portal for Food Items
Project 1: Project Description
Project Objective
The objective of the Kitchen Story project is to develop an e-commerce portal that enables users to shop for basic food items online. The website will provide a seamless shopping experience for customers and an efficient management system for administrators. Below is a detailed description of the features required for both the customer-facing website and the admin backend.
Customer-Facing Features
1.
Search Form on Home Page:

The home page will feature a search form where customers can enter the names of food items they wish to purchase.

As customers type, a dynamic search functionality will suggest available items matching the query.
2.
Display Available Food Items with Prices:

Based on the search query, the website will display a list of available food items along with their prices.

Each item will be accompanied by an image and a brief description to help customers make informed decisions.
3.
Item Selection and Detailed Order Breakdown:

Customers can select items to purchase, which will redirect them to a detailed list of their selected items.

On the next page, customers will see a complete breakdown of their order, including item quantities, individual prices, and the total amount.
4.
Payment Gateway Integration:

The website will integrate a secure payment gateway to handle transactions.

Customers will be able to enter their payment details and complete their purchase.
5.
Order Confirmation Page:

After successfully completing the payment, customers will be redirected to a confirmation page.

The confirmation page will display the details of the order, including items purchased, total amount paid, and expected delivery date.
Admin Backend Features
1.
Admin Login Page:

The admin backend will have a secure login page where administrators can enter their credentials to access the dashboard.

Post-login, admins will have the option to change their password if needed.
2.
Master List of Food Items:

The admin dashboard will display a master list of all food items available for purchase.

This list will include item names, descriptions, prices, and stock quantities.
3.
Add or Remove Food Items:

Administrators will have the functionality to add new food items to the inventory.

They can also remove items that are no longer available or update existing items with new information.

A writeup or description can be created for each item to provide detailed information to customers.
Technical Implementation
The project will be developed using Java Spring Boot for the backend and Thymeleaf for templating. The front end will utilize HTML, CSS (with Bootstrap for styling), and JavaScript for dynamic functionality. The database will be managed using MySQL, where all food items, user details, and orders will be stored.
Key Components
1.
Controllers:

HomeController.java: Manages the search functionality and displays available items.

OrderController.java: Handles item selection, order breakdown, and payment processing.

AdminController.java: Manages admin login, password changes, and inventory management.
2.
Templates:

home.html: Contains the search form and displays search results.

order.html: Displays the detailed order breakdown and payment gateway.

confirmation.html: Shows the order confirmation details.

admin.html: Admin dashboard for managing food items.
3.
Database Schema:

Users: Stores user information including credentials and order history.

Items: Stores details of available food items.

Orders: Records customer orders and payment details.
At the end
The Kitchen Story project aims to create an efficient and user-friendly e-commerce portal for food items. By providing robust features for both customers and administrators, the website will streamline the shopping experience and ensure efficient inventory management. The integration of secure payment processing and dynamic search functionality will further enhance the usability and reliability of the portal.
Thanks
Priyanshu Ranjan (Java Developer).
