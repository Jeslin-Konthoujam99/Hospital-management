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
	con = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/hmss?useSSL=false", "root", "root");
} catch (Exception e) {
	e.printStackTrace();
}
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Appointment</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
body {
	padding: 100px;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="card shadow">
				<div class="card-header">
					Book Appointment
					<%
				String message = "";
				if (request.getAttribute("message") == null) {
					message = "";
				} else {
					message = (String) request.getAttribute("message");
				}
				%>

				</div>
				<div class="card-body">
					<form action="IpdAppointment1" method="post">
						<div class="mb-3">
							<label for="inputPatientID" class="form-label">Patient ID</label>
							<input type="text" class="form-control" id="inputPatientID"
								value="<%=session.getAttribute("patientid")%>" name="patientID">
						</div>
						<div class="mb-3">
							<label for="inputPatientName" class="form-label">Patient
								Name</label> <input type="text" class="form-control"
								id="inputPatientName"
								value="<%=session.getAttribute("fullname")%>"
								name="patientName">
						</div>
						<div class="mb-3">
							<label for="inputDoctorName" class="form-label">Doctor
								Name</label> 
								<select id="inputDoctorName" class="form-select"
								aria-describedby="doctorName" name="doctorName">
								<%
								String bed_id = "";
								String ward_id = "";
								String check_in = "";
								try {
									pstmt = con.prepareStatement("select * from doctorlist");
									rs = pstmt.executeQuery();
									while (rs.next()) {
								%>
								<option
									value=" <%=rs.getInt("sl")%>"><%=rs.getString("firstname")%>
									<%=rs.getString("lastname")%></option>
								<%
								}
								Statement st = con.createStatement();
								ResultSet rs1 = null;

								String sql1 = "select* from ward where patient_id='" 
								+ session.getAttribute("patientid") + "' and availability='no'";
								rs1 = st.executeQuery(sql1);

								while (rs1.next()) {
								bed_id = rs1.getString("b_id");
								ward_id = rs1.getString("ward_id");
								check_in = rs1.getString("check_in");
								}
								} catch (Exception e) {
								e.printStackTrace();
								}
								%>
							</select>
						</div>
						<button type="button" class="btn btn-primary"
							data-bs-toggle="modal" data-bs-target="#exampleModal">Choose
							Bed</button>
						<div class="mb-3">
							<label for="inputBed" class="form-label">Bed No.:</label>
							 <input
								type="text" id="bed_id" class="form-control" value="<%=bed_id%>"
								name="bed_id"> 
								<label for="inputWard" class="form-label">Ward
								No.:</label> 
								<input type="text" id="ward_id" class="form-control"
								value="<%=ward_id%>" name="ward_id"> 
								<label
								for="check_in" class="form-label">Check In:</label> 
								<input
								type="text" id="check_in" class="form-control"
								value="<%=check_in%>" name="check_in">
						</div>

						<button type="submit" class="btn btn-primary">Save &amp;
							Continue</button>
					</form>
					<div class="modal fade" id="exampleModal" data-bs-backdrop="static"
						data-bs-keyboard="false" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog model-dialog-centered">
							<div class="modal-content">
								<div class="modal-header">
									<h1 class="modal-title fs-5" id="exampleModalLabel">Bed
										Availability</h1>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">
									<div class="dropdown">
										<button>Choose a ward first</button>
										<div class="dropdown-content">
											<br> <a href="normal.jsp">Normal ward(Rs 500/day)</a> <br>
											<a href="normal.jsp">Special ward(Rs 1000/day)</a><br>
											<a href="normal.jsp">Delux Ward(Rs 3000/day)</a>

										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>