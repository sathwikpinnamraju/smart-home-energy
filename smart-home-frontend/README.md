
I am Sathwik Pinnamraju, a final-year B.Tech Computer Science student at PES University, Bangalore.My goal is to contribute to the advancement of AI and IoT technologies.

Smart Home Energy Monitoring System
Overview
Smart Home Energy Monitoring System is a full-stack web application designed to track and analyze energy consumption across various smart home devices.
The system integrates an IoT-powered backend (Spring Boot, PostgreSQL) with a React.js frontend to provide real-time insights into energy usage.
Users can add, update, and monitor device statuses, visualize consumption trends through interactive graphs, and optimize energy efficiency.
This project is a Smart Home Energy Monitoring System that helps users track and manage energy consumption efficiently. The system includes:

Backend: A Spring Boot REST API with PostgreSQL.
Frontend: A React.js dashboard for data visualization.
Database: PostgreSQL for storing device energy usage data.
Authentication: Implemented with Spring Security using Basic Authentication.

smart-home-energy-monitoring/
│── smart-home-backend/     # Backend (Spring Boot + PostgreSQL)
│── smart-home-frontend/    # Frontend (React.js)
│── database/               # PostgreSQL database setup
│── README.md               # Project Documentation

dataset
cleaned_energy_data.xlsx (Final dataset)
energy_data.xlsx (Raw dataset)
smart_home_energy_consumption_large.xlsx (Unfiltered dataset)

Backend (Spring Boot)
Tech Stack
Java 17
Spring Boot (Web, Security, Data JPA)
PostgreSQL
Hibernate ORM
Lombok

Frontend (React.js)
Tech Stack
React.js (useState, useEffect, axios)
Recharts (for graphs)
Axios (for API calls)
CSS (for styling)
Key Features
✔ Device Management - Add, update, and delete smart devices.
✔ Energy Tracking - View energy usage of devices in real-time.
✔ Data Visualization - Display usage data as tables and bar charts.

Challenges Faced
1. Backend Authentication (Spring Security)
Initially, API requests were failing with 401 Unauthorized.
Solved by:
Allowing public access for testing (permitAll()).
Adding authentication headers in Axios requests.
2. Data Fetching Issues
React frontend wasn't fetching data properly due to CORS and authentication.
Fixed by:
Enabling CORS in SecurityConfig.java.
Using axios.get() with authentication credentials.
3. Graph Rendering Issues
Initially, the graphs weren't updating dynamically.
Solved by:
Using useEffect() to fetch data when the component mounts.

How to Run the Project
Step 1: Start the Backend
bash
Copy
Edit
cd smart-home-backend
mvn spring-boot:run

Step 2: Start the Frontend
bash
Copy
Edit
cd smart-home-frontend
npm install   # Install dependencies
npm start     # Start the frontend
Then open http://localhost:3000 in your browser.s

Apart from this i added implementation images for your clarity.


