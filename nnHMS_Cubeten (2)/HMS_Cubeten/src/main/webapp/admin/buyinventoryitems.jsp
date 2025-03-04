<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buy Inventory Items</title>
</head>
<body>

    <h2>Buy Inventory Items</h2>

    <form action="buyinventory" method="post">
        Item ID: <input type="text" name="itemid" required><br>
        Quantity: <input type="text" name="qty" required><br>
    
        <input type="submit" value="Buy">
    </form>

</body>
</html>
