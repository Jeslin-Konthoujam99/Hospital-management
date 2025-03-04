Hospital Management System

Project Overview

The Hospital Management System is a Java-based web application designed to manage the various operations of a hospital, including patient management, appointments, staff records, and billing. This project was developed using Java and MySQL and is deployed on Apache Tomcat.

Features

Patient Management: Add, update, and delete patient records.

In-Patient Department (IPD) Management: Manage the admission, room selection, treatment, and discharge of in-patients.

Appointment Scheduling: Book and manage doctor appointments.

Staff Management: Add, update, and manage doctor and staff records.

Billing System: Generate and manage invoices and payment records.

Authentication: Secure login for admins and staff.

Medical Records Management: Doctors can update and access patient medical records.

Pharmacy Management: Pharmacists can update medicine prescriptions and stock details.

Prescription Management: Doctors and nurses can prescribe medicines and update treatment plans.

Lab Report Management: Lab Technicians upload test results and update records.

Refund Processing: Handles patient refund requests by verifying billing details and processing transactions.

OPD Billing: Manages outpatient department billing, generates acknowledgments, and updates payment records.

Multiple Payment Methods: Supports both cash and bank transactions for billing and refunds.

Inventory Management: Tracks hospital equipment and medical supplies.

Salary Management: Manages staff salary records and payment schedules.

Hospital Revenue Management: Tracks hospital income from various services and generates revenue reports.

Technologies Used

Java (JSP & Servlets)

MySQL (Database)

Apache Tomcat 10.1 (Server)

JDBC (Database Connectivity)

HTML, CSS, JavaScript (Frontend)

Installation

Clone the repository:

git clone https://github.com/Jeslin-Konthoujam99/Hospital-management.git

Open the project in Eclipse:

Go to File -> Import -> Existing Maven Projects.

Configure the database:

Create a MySQL database and import the provided SQL file.

Update database credentials in utils.java:

String url = "jdbc:mysql://localhost:3306/hospital_db";
String user = "root";
String password = "root";

Run the project:

Right-click the project → Run As -> Run on Server → Select Tomcat.

Usage

Admin Login: Access hospital admin dashboard to manage patients, doctors, and appointments.

Patient Registration: Patients can register and book appointments.

Billing: Generate and view bills for medical services.
Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

Author

Jeslin Konthoujam
GitHub: Jeslin-Konthoujam99

License

This project is licensed under the MIT License.
