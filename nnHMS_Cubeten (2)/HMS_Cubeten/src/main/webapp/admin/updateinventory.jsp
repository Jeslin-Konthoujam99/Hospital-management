<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="login.SQLConnect" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<% Class.forName("com.mysql.cj.jdbc.Driver"); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Update Inventory</title>
</head>
<body>
    <%
    SQLConnect model = new SQLConnect();
        Connection con = null;
        con = model.connect();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("select * from inventory");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    %>

        <% while(res.next()){ %>
            <div>
                <h2>Update Inventory Item</h2>
                
                
                    <form method="post" action="updateinventory">
                
                Item ID: <input type="hidden" name="itemid" value="<%= res.getInt(1) %>"><%= res.getInt(1) %><br>
                Item Name: <input type="text" name="itemname" value="<%= res.getString(2) %>"><br>
                Department Id: <input type="text" name="departmentid" value="<%= res.getString(3) %>"><br>
                Quantity: <input type="number" name="quantity" value="<%= res.getInt(4) %>"><br>
                Price: <input type="number" name="price" value="<%= res.getInt(5) %>"><br>
                Low Stock Quantity: <input type="number" name="lowStockqty" value="<%= res.getInt(6) %>"><br>

                <%-- Format the date using SimpleDateFormat --%>
                Date: <input type="datetime-local" name="datetime" value="<%= dateFormat.format(res.getTimestamp(7))%>"><br>
                <input type="submit" value="Update">
                 </form>

            </div>
             
             
               <hr>
        <% } %>
       
  
</body>
</html>
