<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
            <%@page import="login.SQLConnect"%>
                    	<%@page import="java.sql.*"%>
            
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Acknowledgement</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
body {
	padding: 60px;
	padding-left: 70px;
}
.card {
	padding: 30px;
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
@media print {
	body {
		padding: 0;
		padding-top:20px;
		margin: 0;
	}
	.card{
		box-shadow: none;
	}
	.container-fluid {
		padding: 0;
		margin: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
		
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
						
							String age="";
							String name="";
							String doctorname="";
							String gender="";
							int app_id = Integer.parseInt( 
									request.getParameter("booked_id1"));
							int patientid =Integer.parseInt( 
									request.getParameter("patient_id1"));
							int doctorid =Integer.parseInt(
									request.getParameter("doctor_id1"));

							try {
								pstmt = con.prepareStatement(
										"select * from opd_patient where patientid =?");
								pstmt.setInt(1, patientid);
								rs = pstmt.executeQuery();
								while(rs.next()) {
									name = rs.getString("fullname");
									age = rs.getString("age");
									gender = rs.getString("gender");
								}
								pstmt = con.prepareStatement(
										"select * from doctorlist where sl=?");
								pstmt.setInt(1, doctorid);
								rs = pstmt.executeQuery();
								while(rs.next()) {
									doctorname = rs.getString("firstname")+
											" "+rs.getString("lastname");
								}
								
								
								
							}catch(Exception e) {
								e.printStackTrace();
								response.sendRedirect("opdpatients.jsp");
								
							}
						%>
<div class="container-fluid px-5">
	<div class="row">
		<div class="card">
			<div class="card-header">
				<button class="btn btn-primary d-print-none" onclick="window.location.reload()"><i class="bi bi-arrow-clockwise"></i></button>
				<button class="btn btn-primary d-print-none" name="print" onclick="window.print()">Print</button>
				<div class="row d-md-block d-lg-flex">
					<div class="col">
						<p>Patient Name: <%=name %><br>
						Patient ID: <%=patientid %><br>
						Age: <%=age %> / <%=gender %><br>
					</div>
					<div class="col">
						<p>Doctor Name: <span class="fw-bold"><%=doctorname%></span></p>
					</div>
				</div>
			</div>
			<div class="card-body">
			<form action="../NurseCheckup" method="post">
				<input type="hidden" name="pid" value="<%=patientid %>">
				<input type="hidden" name="app_id" value="<%=app_id %>">
				<div class="row">
					<div class="input-group mb-3">
						<span class="input-group-text" id="basic-addon2">BP</span>
						<input type="text" class="form-control" id="BP" name="BP" value="">
						<span class="input-group-text me-3" id="basic-addon2">mmHg</span>
						
						<span class="input-group-text" id="basic-addon2">Pulse</span>
						<input type="text" class="form-control" id="Pulse" name="pulse" value="">
						<span class="input-group-text me-3" id="basic-addon2">bpm</span>
						
						<span class="input-group-text" id="basic-addon2">Temperature</span>
						<input type="number" class="form-control" id="Temperature" name="temperature" value="">
						<span class="input-group-text" id="basic-addon2">°C</span>
					</div>
					<div class="input-group mb-3">
  						<span class="input-group-text" id="basic-addon2">Weight</span>
						<input type="number" class="form-control" id="weight" name="weight" value="" aria-describedby="basic-addon2">
  						<span class="input-group-text me-3" id="basic-addon2">kg</span>
  						
  						<span class="input-group-text" id="basic-addon2">Height</span>
						<input type="number" class="form-control" id="height" name="height" value="" aria-describedby="basic-addon2">
  						<span class="input-group-text me-3" id="basic-addon2">cm</span>
  						
						<span class="input-group-text" id="basic-addon2">BMI</span>
						<input type="text" class="form-control" id="bmi" name="bmi" value="" readonly aria-describedby="basic-addon2">
						<span class="input-group-text" id="basic-addon2">kg/m<sup>2</sup></span>
						<input type="text" class="form-control" id="status" name="Status" value="" readonly>
					</div>
					<div class="input-group mb-3">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</div>
<script>
	var height = document.getElementById("height");
	var weight = document.getElementById("weight");
	var bmi = document.getElementById("bmi");
	height.style.fontWeight = "bold";
	weight.style.fontWeight = "bold";
	bmi.style.fontWeight = "bold";
	document.getElementById("status").style.fontWeight = "bold";
	height.addEventListener("input", function() {
		status();
	});
	weight.addEventListener("input", function() {
		status();
	});
	function status(){
		bmi.value = (weight.value / (height.value * height.value)) * 10000 + "kg";
		bmi.value = parseFloat(bmi.value).toFixed(2);
		if(bmi.value < 16) {
			document.getElementById("status").value = "Severe Thinness";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		}else if(bmi.value >= 16 && bmi.value < 18.5) {
			document.getElementById("status").value = "Moderate Thinness";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		}else if(bmi.value < 18.5) {
			document.getElementById("status").value = "Mild Thinness";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		}else if(bmi.value >= 18.5 && bmi.value < 25) {
			document.getElementById("status").value = "Normal";
			document.getElementById("status").style.color = "green";
			bmi.style.color = "green";
		}else if(bmi.value >= 25 && bmi.value < 30) {
			document.getElementById("status").value = "Overweight";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		}else if(bmi.value >= 30) {
			document.getElementById("status").value = "Obese";
			document.getElementById("status").style.color = "red";
			bmi.style.color = "red";
		}	
	}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>