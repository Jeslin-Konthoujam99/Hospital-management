<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" %>
<%@ page import="login.SQLConnect"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<% Class.forName("com.mysql.cj.jdbc.Driver"); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Delete Inventory Items</title>
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

    <form method="post" action="deleteinventory">
        <table border="1">
            <thead>
                <tr>
                    <th>Select</th>
                    <th>Item ID</th>
                    <th>Item Name</th>
                    <th>Department Id</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Low Stock Quantity</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <% while(res.next()){ %>
                    <tr>
                        <td><input type="checkbox" name="selectedItems" value="<%= res.getInt(1) %>"></td>
                        <td><%= res.getInt(1) %></td>
                        <td><%= res.getString(2) %></td>
                        <td><%= res.getString(3) %></td>
                        <td><%= res.getInt(4) %></td>
                        <td><%= res.getInt(5) %></td>
                        <td><%= res.getInt(6) %></td>
                        <td><%= res.getTimestamp(7) %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <input type="submit" value="Delete Selected Items">
    </form>
    <hr>
</body>
</html>