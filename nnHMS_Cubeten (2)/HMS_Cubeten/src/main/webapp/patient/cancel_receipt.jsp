<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.text.SimpleDateFormat" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>OPD Cancel Receipt</title>
</head>
<body>
 <h1>OPD Cancel Receipt</h1>
 	<p><strong>Date today:</strong> 
    <%= request.getAttribute("currentDate") %></p>

    <p><strong>Booked ID:</strong> 
    <%= request.getAttribute("booked_id") %></p>
    <p><strong>Patient Name:</strong> 
    <%= request.getAttribute("patient_name") %></p>
    <p><strong>Department Name:</strong> 
    <%= request.getAttribute("department_name") %></p>
    <p><strong>Doctor Name:</strong> 
    <%= request.getAttribute("doctor_name") %></p>
    <p><strong>Date of appointment:</strong>
    <%= new SimpleDateFormat("yyyy-MM-dd")
    .format(request.getAttribute("date")) %></p>
    <p><strong>Refunded Amount:</strong> 
    <%= request.getAttribute("amount") %></p>

</body>
</html>