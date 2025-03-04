<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="login.SQLConnect"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acknowledgement</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
body {
	padding: 60px;
	padding-left: 70px;
}

.card {
	padding: 30px;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0
		rgba(0, 0, 0, 0.19);
}

.input-group-text {
	background: #007bff;
	border: none;
	color: white;
}

.container-fluid {
	padding-left: 50px;
	padding-right: 50px;
}

input[type=number]::-webkit-inner-spin-button, input[type=number]::-webkit-outer-spin-button
	{
	-webkit-appearance: none;
	margin: 0;
}

input[type=number] {
	-moz-appearance: textfield;
}

input, textarea {
	border: 1px solid #007bff;
	box-shadow: inset 0 1px 2px rgba(0, 0, 0, .075), inset 0 5px 8px
		rgba(0, 123, 255, 0.6);
}

@media print {
	body {
		padding: 0;
		padding-top: 20px;
		margin: 0;
	}
	.card {
		box-shadow: none;
	}
	.container-fluid {
		padding-left: 0;
		padding-right: 0;
		margin: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
	}
	input, textarea {
		border: none;
		box-shadow: none;
	}
}
</style>
</head>
<body>
	<%
	SQLConnect model = new SQLConnect();
	Connection con = model.connect();
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String age = "";
	String gender = "";
	String bp = "";
	float temp = 0;
	float weight = 0;
	float height = 0;
	float bmi = 0;
	float pulse = 0;
	String medication = "";
	String test = "";
	String recommendation = "";

	int appointmentid = Integer.parseInt(request.getParameter("id"));
	String patientid = request.getParameter("pid");
	String apointment_date = request.getParameter("date");
	String doctorname = session.getAttribute("name").toString().toUpperCase();
	try {
		pstmt = con.prepareStatement("select * from opd_patient where patientid=?");
		pstmt.setString(1, patientid);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			age = rs.getString("age");
			gender = rs.getString("gender");
		}

		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		pstmt2 = con.prepareStatement("select * from prescriptions1 where appointment_id=?");
		pstmt2.setInt(1, appointmentid);
		rs2 = pstmt2.executeQuery();
		while (rs2.next()) {
			bp = rs2.getString("bp");
			pulse = Float.parseFloat(rs2.getString("pulse"));
			weight = Float.parseFloat(rs2.getString("weight"));
			height = Float.parseFloat(rs2.getString("height"));
			bmi = Float.parseFloat(rs2.getString("bmi"));
			temp = Float.parseFloat(rs2.getString("temperature"));
		}
	} catch (Exception e) {
		System.out.println("exception in doctor/ acknowledge    ");

		e.printStackTrace();

		response.sendRedirect("patienthistory.jsp");

	}
	%>
	<div class="container-fluid">
		<div class="row">
			<div class="card">
				<div class="card-header">
					<button class="btn btn-primary d-print-none"
						onclick="window.location.reload()">
						<i class="bi bi-arrow-clockwise"></i>
					</button>
					<button class="btn btn-primary d-print-none" name="print"
						onclick="window.print()">Print</button>
					<div class="row d-md-block d-lg-flex">
						<div class="col">
							<p>
								Patient ID:
								<%=patientid%><br> Patient ID:
								<%=patientid%><br> Age:
								<%=age%>
								/
								<%=gender%><br> Appointment Date:
								<%=apointment_date%>
								-
							</p>
						</div>
						<div class="col">
							<p>
								Doctor Name: <span class="fw-bold"> <%=doctorname%></span>
							</p>
						</div>
					</div>
				</div>
				<div class="card-body">
					<form action="../Prescriptions" method="post">
						<input type="hidden" name="pid" value="<%=appointmentid%>">
						<div class="row">
							<div class="input-group mb-3">
								<span class="input-group-text" id="basic-addon2">BP</span> <input
									type="text" class="form-control shadow" id="BP" name="BP"
									value="<%=bp%>" readonly> <span
									class="input-group-text me-3" id="basic-addon2">mmHg</span> <span
									class="input-group-text" id="basic-addon2">Pulse</span> <input
									type="text" class="form-control shadow" id="Pulse" name="pulse"
									value="<%=pulse%>" readonly> <span
									class="input-group-text me-3" id="basic-addon2">bpm</span> <span
									class="input-group-text" id="basic-addon2">Temperature</span> <input
									type="number" class="form-control shadow" id="Temperature"
									name="temperature" value="<%=temp%>" readonly> <span
									class="input-group-text" id="basic-addon2">°C</span>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text" id="basic-addon2">Weight</span> <input
									type="number" class="form-control shadow" id="weight"
									name="weight" value="<%=weight%>" readonly
									aria-describedby="basic-addon2"> <span
									class="input-group-text me-3" id="basic-addon2">kg</span> <span
									class="input-group-text" id="basic-addon2">Height</span> <input
									type="number" class="form-control shadow" id="height"
									name="height" value="<%=height%>" readonly
									aria-describedby="basic-addon2"> <span
									class="input-group-text me-3" id="basic-addon2">cm</span> <span
									class="input-group-text" id="basic-addon2">BMI</span> <input
									type="text" class="form-control shadow" id="bmi" name="bmi"
									value="<%=bmi%>" readonly aria-describedby="basic-addon2">
								<span class="input-group-text" id="basic-addon2">kg/m<sup>2</sup></span>
								<input type="text" class="form-control shadow" id="status"
									name="Status" value="" readonly>
							</div>
						</div>
						<div class="row border-top border-3 mb-3">
							<div class="form-group">
								<label for="medication">Medication</label>
								<div id="medication" class="d-flex">
									<span class="input-group-text" id="basic-addon2">Medicine
										1</span> <input type="text" class="form-control" id="medication"
										name="medication" value="" placeholder="Name"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="dose" name="dose" value=""
										placeholder="Dose" aria-describedby="basic-addon2"> <input
										type="text" class="form-control" id="frequency"
										name="frequency" value="" placeholder="Frequency"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="duration" name="duration" value=""
										placeholder="Duration" aria-describedby="basic-addon2">
								</div>
								<div id="medication" class="d-flex">
									<span class="input-group-text" id="basic-addon2">Medicine
										2</span> <input type="text" class="form-control" id="medication"
										name="medication" value="" placeholder="Name"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="dose" name="dose" value=""
										placeholder="Dose" aria-describedby="basic-addon2"> <input
										type="text" class="form-control" id="frequency"
										name="frequency" value="" placeholder="Frequency"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="duration" name="duration" value=""
										placeholder="Duration" aria-describedby="basic-addon2">
								</div>
								<div id="medication" class="d-flex">
									<span class="input-group-text" id="basic-addon2">Medicine
										3</span> <input type="text" class="form-control" id="medication"
										name="medication" value="" placeholder="Name"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="dose" name="dose" value=""
										placeholder="Dose" aria-describedby="basic-addon2"> <input
										type="text" class="form-control" id="frequency"
										name="frequency" value="" placeholder="Frequency"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="duration" name="duration" value=""
										placeholder="Duration" aria-describedby="basic-addon2">
								</div>
								<div id="medication" class="d-flex">
									<span class="input-group-text" id="basic-addon2">Medicine
										4</span> <input type="text" class="form-control" id="medication"
										name="medication" value="" placeholder="Name"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="dose" name="dose" value=""
										placeholder="Dose" aria-describedby="basic-addon2"> <input
										type="text" class="form-control" id="frequency"
										name="frequency" value="" placeholder="Frequency"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="duration" name="duration" value=""
										placeholder="Duration" aria-describedby="basic-addon2">
								</div>
								<div id="medication" class="d-flex">
									<span class="input-group-text" id="basic-addon2">Medicine
										5</span> <input type="text" class="form-control" id="medication"
										name="medication" value="" placeholder="Name"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="dose" name="dose" value=""
										placeholder="Dose" aria-describedby="basic-addon2"> <input
										type="text" class="form-control" id="frequency"
										name="frequency" value="" placeholder="Frequency"
										aria-describedby="basic-addon2"> <input type="text"
										class="form-control" id="duration" name="duration" value=""
										placeholder="Duration" aria-describedby="basic-addon2">
								</div>
							</div>
						</div>
						<div class="row mb-3">
							<div class="form-group">
								<label for="test">Test</label> <input type="text"
									class="form-control" id="test" name="test" value="">
							</div>
						</div>
						<div class="row mb-3">
							<div class="form-group">
								<label for="recommendation">Recommendation</label>
								<textarea class="form-control" id="recommendation"
									name="recommendation" rows="4"></textarea>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<button type="submit" class="btn btn-primary d-print-none">Submit</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		var weight = document.getElementById("weight");
		var height = document.getElementById("height");
		var bmi = document.getElementById("bmi");
		height.style.fontWeight = "bold";
		weight.style.fontWeight = "bold";
		bmi.style.fontWeight = "bold";
		document.getElementById("status").style.fontWeight = "bold";

		if (parseFloat(bmi.value) < 16) {
			document.getElementById("status").value = "Severely Thinness";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		} else if (parseFloat(bmi.value) >= 16 && parseFloat(bmi.value) < 18.5) {
			document.getElementById("status").value = "Moderate Thinness";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		} else if (parseFloat(bmi.value) < 18.5) {
			document.getElementById("status").value = "Mild Thinness";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		} else if (parseFloat(bmi.value) >= 18.5 && parseFloat(bmi.value) < 25) {
			document.getElementById("status").value = "Normal";
			document.getElementById("status").style.color = "green";
			bmi.style.color = "green";
		} else if (parseFloat(bmi.value) >= 25 && parseFloat(bmi.value) < 30) {
			document.getElementById("status").value = "Overweight";
			document.getElementById("status").style.color = "orange";
			bmi.style.color = "orange";
		} else if (parseFloat(bmi.value) >= 30) {
			document.getElementById("status").value = "Obese";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		}
	</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>