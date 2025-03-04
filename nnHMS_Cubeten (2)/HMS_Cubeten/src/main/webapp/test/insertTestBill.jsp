<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insert Test Bill</title>
</head>
<body>
    <h2>Insert Test Bill</h2>
    <form action="../InsertTestBillServlet" method="post">
        Prescription ID: <input type="text" name="prescriptionId" required><br>
        Amount: <input type="text" name="amount" required><br>
        Date: <input type="date" name="date" required><br>
        Payment Status: <input type="text" name="paymentStatus" required><br>
        <input type="submit" value="Insert Test Bill">
    </form>
</body>
</html>
