<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="login.SQLConnect"%>
<%@page import="java.sql.*"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Patients History</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">
<link rel="stylesheet"
	href="https://cdn.datatables.net/datetime/1.5.1/css/dataTables.dateTime.min.css">
<style>

body {
	padding: 60px;
	padding-left: 70px;
}

.card {
	padding: 30px;
}
</style>
</head>
<body>

	<div class="container-fluid px-5">
		<div class="row">
			<div class="card shadow">
				<div class="card-header">Appointment History</div>
				<div class="card-body">
					<table class="table table-striped" id="example">
						<thead>
							<tr>
								<th scope="col">Appointment ID</th>
								<th scope="col">Patient ID</th>
								<th scope="col">department id</th>
								<th scope="col">Appointment Date</th>
								<th scope="col">Prescription</th>
							</tr>
						</thead>
						<tbody>
<%
int doctor_id=(Integer)session.getAttribute("id");
try {
	SQLConnect model = new SQLConnect();
	Connection con = model.connect();
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	LocalDateTime myDateObj = LocalDateTime.now();
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	String appointment_date = myDateObj.format(myFormatObj);
	String doctorname = (String) session.getAttribute("name");
	pstmt = con.prepareStatement(
		 "select * from book_appointment where doctor_id=? and "
		+"appointment_status=? order by booked_id desc");
	pstmt.setInt(1, doctor_id);
	pstmt.setString(2, "seen by nurse");
	rs = pstmt.executeQuery();
	while (rs.next()) {
%>
<tr>
	<td><%=rs.getInt("booked_id")%></td>
	<td><%=rs.getInt("patient_id")%></td>
	<td><%=rs.getInt("department_id")%></td>
	<td><%=rs.getDate("date")%></td>
	<td><form action="acknowledge.jsp" method="post">
			<input type="hidden" name="id"
			 value="<%=rs.getString("booked_id")%>">
				<input type="hidden" name="pid"
				value="<%=rs.getInt("patient_id")%>"> 
				
				<input
				type="hidden" name="date"
				value="<%=rs.getDate("date")%>">
			<button type="submit" class="btn btn-primary">Acknowledge</button>
		</form></td>
</tr>
<%
}
} catch (Exception e) {
	System.out.println("exception in doctor/ patienthistory   ");

	e.printStackTrace();

}
%>
						</tbody>
					</table>
					<!-- Button trigger modal -->
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.2/moment.min.js"></script>
	<script
		src="https://cdn.datatables.net/datetime/1.5.1/js/dataTables.dateTime.min.js"></script>
	<script>
let minDate, maxDate;

//Custom filtering function which will search data in column four between two values
DataTable.ext.search.push(function (settings, data, dataIndex) {
 let min = minDate.val();
 let max = maxDate.val();
 let date = new Date(data[3]);

 if (
     (min === null && max === null) ||
     (min === null && date <= max) ||
     (min <= date && max === null) ||
     (min <= date && date <= max)
 ) {
     return true;
 }
 return false;
});

//Create date inputs
minDate = new DateTime('#min', {
 format: 'MMMM Do YYYY'
});
maxDate = new DateTime('#max', {
 format: 'MMMM Do YYYY'
});

//DataTables initialisation
let table = new DataTable('#example');

//Refilter the table
document.querySelectorAll('#min, #max').forEach((el) => {
 el.addEventListener('change', () => table.draw());
});
</script>
</body>
</html>