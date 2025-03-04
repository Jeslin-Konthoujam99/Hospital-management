<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buy Equipment Items</title>
</head>
<body>

    <h2>Buy Inventory Items</h2>

    <form action="buyEquipment" method="post">
        Equipment ID: <input type="text" name="equipmentid" required><br>
        Quantity: <input type="text" name="qty" required><br>
    
        <input type="submit" value="Buy">
    </form>

</body>
</html>
