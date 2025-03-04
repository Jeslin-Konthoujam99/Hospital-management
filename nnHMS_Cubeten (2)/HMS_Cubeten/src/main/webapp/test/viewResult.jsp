<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="login.SQLConnect"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test Result</title>
</head>
<body>

	<h2>Blood Test Report</h2>

	<%
	Connection con = null;
	SQLConnect model = new SQLConnect();
	con = model.connect();
	Statement stmt = null;
	ResultSet res = null;

	try {
		String query =" SELECT tr.Employee_id, ba.patient_id, "
			+"ba.doctor_id,  tr.result_id, tr.Blood_id, tr.Blood_type "
			+"FROM  testresult tr "
			+"JOIN testbill tb ON tr.Blood_id = tb.Blood_id "
			+"JOIN prescriptions1 p1 ON tb.Prescription_id = p1.prescription_id "
			+"JOIN book_appointment ba ON p1.appointment_id = ba.booked_id";

		try (PreparedStatement preparedStatement = con.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery()) {

			// Display table headers
	%>
	<table border="1">
		<tr>
			<th>Employee ID</th>
			<th>Patient ID</th>
			<th>Doctor ID</th>
			<th>Result ID</th>
			<th>Blood ID</th>
			<th>Blood Type</th>

		</tr>

		<%
		// Iterate through the result set and display data
		while (resultSet.next()) {
		%>
		<tr>
			<td><%=resultSet.getInt("Employee_id")%></td>
			<td><%=resultSet.getInt("patient_id")%></td>
			<td><%=resultSet.getInt("doctor_id")%></td>
			<td><%=resultSet.getInt("result_id")%></td>
			<td><%=resultSet.getInt("Blood_id")%></td>
			<td><%=resultSet.getString("Blood_type")%></td>

		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	} catch (SQLException e) {
	e.printStackTrace();
	} finally {
	try {
	// Close the connection in the finally block
	if (con != null && !con.isClosed()) {
		con.close();
	}
	} catch (SQLException e) {
	e.printStackTrace();
	}
	}
	%>

</body>
</html>
