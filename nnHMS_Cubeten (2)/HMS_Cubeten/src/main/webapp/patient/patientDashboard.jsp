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
int patientid = (Integer)session.getAttribute("patientid");
%>

	welcome to patient dashboard
	<br>
	<a href="patient/ipdappointment.jsp">ipd  appointment 
		</a>
	<br>
	
	<a href="patient/book_appointments.jsp">Book opd Appointment </a>
	<br>

	<a href="patient/appointments_history.jsp">opd Appointments history
		page</a>
	<br>
	
		<a href="patient/choose_ward.jsp">ipd mine appointment . dewlete this
		</a>
	<br>
	
	

	<form action="Logout" method="post">
		<button class="btn btn-danger" type="submit">Logout</button>
	</form>
	<br>

	<h2>ipd appointments</h2>
	<form action="../IpdAppointment" method="post">
		<input type="hidden" id="patient_id" name="patient_id" 
			value = <%= patientid %>>
		<input type="submit" value="view ipd Appointments">
	</form>


</body>
</html>