<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="login.SQLConnect" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Return Inventory Items</title>
</head>
<body>

    <h2>Return Inventory Items</h2>

    <form action="returnitem" method="post">
        Item ID: <input type="text" name="itemid" required><br>
        Quantity Returned: <input type="text" name="quantityReturned" required><br>
        <input type="submit" value="Return">
    </form>

</body>
</html>
