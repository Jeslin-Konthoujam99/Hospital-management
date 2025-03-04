<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.sql.*"%>
<%@page import="login.SQLConnect"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Normal</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<style>
body {
	padding: 60px;
	padding-left: 70px;
}
</style>
<body>

	<%
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null ;
	SQLConnect model = new SQLConnect();
	try {
		con = model.connect();
	} catch (Exception e) {
		e.printStackTrace();
	}
	String username = (String) session.getAttribute("username");
	String password = (String) session.getAttribute("password");
	String patientid = null;
	String patientname = null;
	try {
		pstmt = con.prepareStatement(
				"select * from opd_patient where email=? and password=?");
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			patientid = rs.getString("patientid");
			patientname = rs.getString("fullname");
		}
	} catch (Exception e) {

	}
	%>

	<h2 align="center">
		BED LIST
		<button onclick="window.location.reload();">
			<i class="bi bi-arrow-clockwise"></i>
		</button>
	</h2>

	<table class="table mx-auto" style="width: 300px;">

		<tr>
			<th>BED NO</th>
			<th>Ward Name</th>
			<th>AVAILABILITY</th>
		</tr>


		<%
		try {
			//String avail = null;
			String avail1 = "yes";

			Statement st = con.createStatement();
			ResultSet rs1 = null;

			String sql1 = "select* from ward";
			rs1 = st.executeQuery(sql1);

			while (rs1.next()) {
		%>

		<tr>
			<td><%=rs1.getInt("b_id")%></td>
			<td><%=rs1.getString("ward_name")%></td>
			<%
			//avail = rs1.getString("availability");
			%>
			<td><%=rs1.getString("availability")%> <%
 if (avail1.equals(rs1.getString("availability"))) {
 %>

				<form action="../sendbedid" method="post">
					<input type="hidden" value="<%=patientid%>" name="patient_id">
					<input type="hidden" value="<%=rs1.getInt("b_id")%>" name="bed_id">
					<input type="submit" value="Choose">
				</form> <%
 } else {

 }
 %></td>

		</tr>
		<%
		}

		} catch (Exception e) {

		}
		%>
	</table>

</body>
</html>