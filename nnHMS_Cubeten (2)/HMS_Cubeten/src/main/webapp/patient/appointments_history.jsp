<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="login.SQLConnect"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	this is appointments history page
	<br>

	<%
	int patientID = (Integer) session.getAttribute("patientid");
	Connection con = null;
	SQLConnect model = new SQLConnect();
	con = model.connect();
	Statement stmt = null;
	ResultSet res = null;
	PreparedStatement preparedStatement = null;
	String SELECT_APPOINTMENTS = 
	"SELECT * FROM book_appointment WHERE patient_id = ? order by date desc";

	try {
		stmt = con.createStatement();
		preparedStatement = con.prepareStatement(SELECT_APPOINTMENTS);

		// Set the patient ID parameter in the SQL query
		preparedStatement.setInt(1, patientID);
		res = preparedStatement.executeQuery();
		System.out.println("QUERY EXECUTED SUCCESSFULLY");
	} catch (Exception e) {
		System.out.println("QUERY failed");
	}
	try {
		while (res.next()) {
	%>

	<hr>
	Appointment : with doctor :
	<%=res.getInt("doctor_id")%>
	<br> dept :
	<%=res.getInt("department_id")%>
	<br> date :
	<%=res.getString("date")%>
	<br> status :
	<%=res.getString("appointment_status")%>
	<br>
	<%-- Retrieve pay_status based on booked_id from opd_bill table --%>
	<%
	int bookedId = res.getInt("booked_id");
	String payStatus = "Not Found";

	// Query to retrieve pay_status based on booked_id
	String payStatusQuery = 
	"SELECT pay_status FROM opd_bill WHERE booked_id = ? order by opd_bill_id desc";

	try (PreparedStatement payStatusStatement = con.prepareStatement(payStatusQuery)) {
		payStatusStatement.setInt(1, bookedId);

		try (ResultSet payStatusResult = payStatusStatement.executeQuery()) {
			if (payStatusResult.next()) {
		payStatus = payStatusResult.getString("pay_status");
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}

	boolean disableSubmit1 = "Paid".equals(payStatus);
	boolean disableSubmit2 = "Refunded".equals(payStatus);
	%>

	<form action="../PayAppointment" method="post">
		<!-- booked_id ID -->
		<label for="booked_id">booked_id :</label> <input type="number"
			value=<%=res.getInt("booked_id")%> id="booked_id" name="booked_id"><br>


		<input type="submit" <%=disableSubmit1 ? "disabled" : ""%>
			value="Pay for Appointment">
	</form>


	<form action="../CancelOpdAppointment" method="post">
		<!-- booked_id ID -->
		<label for="booked_id">booked_id :</label> <input type="number"
			value=<%=res.getInt("booked_id")%> id="booked_id" name="booked_id"><br>

		<input type="submit" <%=disableSubmit2 ? "disabled" : ""%>
			value="Cancel Appointment">
	</form>
	<hr>


	<%
	}//while
	}//try
	catch (SQLException e) {
	System.out.println("Unable to fetch data from database");
	}
	%>


</body>
</html>