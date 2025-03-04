<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="login.SQLConnect"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Equipment and Equipment Bill</title>
</head>
<body>

    <h2>Equipment and Equipment Bill</h2>

    <% 
    SQLConnect model = new SQLConnect();
        Connection con = model.connect();

        try {
            // SQL query to join the equipment and equipmentbill tables
            String query = "SELECT e.equipmentid, e.equipmentname, e.price, eb.amount, eb.equipmentbillid, eb.date AS bill_date, eb.qty, eb.transaction_type " +
                           "FROM equipment e JOIN equipmentbill eb ON e.equipmentid = eb.equipmentid";

            try (PreparedStatement preparedStatement = con.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Display table headers
    %>
                <table border="1">
                    <tr>
                        <th>Equipment ID</th>
                        <th>Equipment Name</th>
                        <th>Price</th>
                        <th>Equipment Bill ID</th>
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
                        <td><%= resultSet.getInt("equipmentid") %></td>
                        <td><%= resultSet.getString("equipmentname") %></td>
                        <td><%= resultSet.getInt("price") %></td>
                        <td><%= resultSet.getInt("equipmentbillid") %></td>
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
