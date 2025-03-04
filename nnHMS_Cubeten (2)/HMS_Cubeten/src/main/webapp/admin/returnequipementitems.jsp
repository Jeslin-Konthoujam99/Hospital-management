<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Return Equipment Items</title>
</head>
<body>

    <h2>Return Equipment Items</h2>

    <form action="returnEquipment" method="post">
        Equipment ID: <input type="text" name="equipmentid" required><br>
        Quantity Returned: <input type="text" name="quantityReturned" required><br>
        <input type="submit" value="Return">
    </form>

</body>
</html>
