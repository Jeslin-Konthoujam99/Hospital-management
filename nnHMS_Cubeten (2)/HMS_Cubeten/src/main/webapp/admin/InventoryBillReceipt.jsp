<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="login.SQLConnect" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inventory and Inventory Bill</title>
</head>
<body>

    <h2>Inventory and Inventory Bill</h2>

    <% 
    SQLConnect model = new SQLConnect();
        Connection con = model.connect();

        try {
            // SQL query to join the inventory and inventorybill tables
            String query = "SELECT inventory.itemid, itemname, price, billid, amount, inventorybill.date AS bill_date, qty, transaction_type " +
                           "FROM inventory JOIN inventorybill ON inventory.itemid = inventorybill.itemid";

            try (PreparedStatement preparedStatement = con.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Display table headers
    %>
                <table border="1">
                    <tr>
                        <th>Item ID</th>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Bill ID</th>
                        <th>Amount</th>
                        <th>Bill Date</th>
                        <th>Quantity in Bill</th>
                        <th>Transaction Type</th>
                    </tr>

    <%
                // Iterate through the result set and display data
                while (resultSet.next()) {
    %>
                    <tr>
                        <td><%= resultSet.getInt("itemid") %></td>
                        <td><%= resultSet.getString("itemname") %></td>
                        <td><%= resultSet.getInt("price") %></td>
                        <td><%= resultSet.getInt("billid") %></td>
                        <td><%= resultSet.getInt("amount") %></td>
                        <td><%= resultSet.getTimestamp("bill_date") %></td>
                        <td><%= resultSet.getInt("qty") %></td>
                        <td><%= resultSet.getString("transaction_type") %></td>
                    </tr>
    <%
                }
    %>
                </table>
    <%
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    %>

</body>
</html>
