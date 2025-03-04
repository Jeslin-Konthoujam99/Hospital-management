<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% 
int patientID = (Integer) session.getAttribute("patientid");
%>

<h2>Book Appointment</h2>
<form action="../BookOpdAppointment" method="post">
    <!-- Patient ID -->
     <label for="patient_id">Patient ID:</label>
    <input type="hidden" value=<%=patientID %> id="patient_id"
     name="patient_id"><br>
    <!-- Replace with actual patient ID from your system -->

    <!-- Department ID -->
    <label for="department_id">Select Department:</label>
    <select id="department_id" name="department_id" required>
        <option value="1">Cardiology</option>
        <option value="2">Orthopedics</option>
        <option value="3">Dermatology</option>
        <!-- Add more departments as needed -->
    </select><br>

    <!-- Doctor ID -->
    <label for="doctor_id">Select Doctor:</label>
    <select id="doctor_id" name="doctor_id" required>
        <option value="1">Dr. Smith</option>
        <option value="2">Dr. Johnson</option>
        <option value="3">Dr. Williams</option>
        <!-- Add more doctors as needed -->
    </select><br>

    <!-- Appointment Date -->
    <label for="date">Appointment Date:</label>
    <input type="date" id="date" name="date" required><br>

    <input type="submit" value="Book Appointment">
</form>

</body>
</html>