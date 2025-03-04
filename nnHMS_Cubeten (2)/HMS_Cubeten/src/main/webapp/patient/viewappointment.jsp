<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%
Connection con = null;
Statement stmt = null;
PreparedStatement pstmt = null;
ResultSet rs = null;

try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hmss?useSSL=false", "root", "root");
} catch (Exception e) {

}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Appointment history</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>
<body>

	<div class="container-fluid px-5 m-5">
		<div class="row">
			<div class="card shadow">
				<div class="card-header">Appointment History</div>
				<div class="card-body">
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">Appointment ID</th>
								<th scope="col">Doctor Name</th>
								<th scope="col">checkin_date </th>
								<th scope="col">bed_id </th>
							</tr>
						</thead>
						<tbody>
							<%
							int patientid = (Integer) session.getAttribute("patientid");
							try {

								pstmt = con.prepareStatement(
										"select * from ipd_appointment where patient_id=?");
								pstmt.setInt(1, patientid);
								rs = pstmt.executeQuery();
								while (rs.next()) {
							%>
							<tr>
								<td><%=rs.getInt("ipd_appointment_id")%></td>
								<td><%=rs.getString("doctor_name")%></td>
								<td><%=rs.getString("checkin_date")%></td>
								<td><%=rs.getString("bed_id")%></td>
							</tr>
							<%
							}
							} catch (Exception e) {
							e.printStackTrace();

							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>