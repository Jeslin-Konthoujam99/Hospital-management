<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
//retrieve booked_id
int booked_id = (Integer)request.getAttribute("booked_id");

// Retrieve the discountedAmount from the request
float discountedAmount = (Float)request.getAttribute("discountedAmount");

// Format the BigDecimal as a String for display
NumberFormat currencyFormatter = new DecimalFormat("#,##0.00");
String formattedDiscountedAmount = currencyFormatter.format(discountedAmount);
%>
<h1>this is GPAY PORTAL . plz pay the Amount:</h1>

	<p>unformatted : <%=discountedAmount %></p>

    <p>formated : <%= formattedDiscountedAmount %></p>
    
   	<form action="OpdBillPaid" method="post">
  	 	<label for="booked_id">booked_id :</label>
    	<input type="number" value=<%=booked_id %>
    			 id="booked_id" name="booked_id"><br>
   		<!-- booked_id ID -->
   		<label for="pay">Pay Here :</label>
   		<input type="number" value=<%=discountedAmount %>
   			 id="pay" name="pay"><br>
   		<input type="submit" value="Pay Up">
	</form>

</body>
</html>