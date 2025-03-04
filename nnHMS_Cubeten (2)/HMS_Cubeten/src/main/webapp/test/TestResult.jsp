<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insert Test Result</title>
</head>
<body>
    <h2>Insert Test Result</h2>
    <form action="../TestResult" method="post">
        Blood ID: <input type="text" name="bloodId" required><br>
        Blood Type: <input type="text" name="bloodType" required><br>
        Employee ID (Technician): <input type="text" name="technicianId" required><br>

        <input type="submit" value="Insert Test Result">
    </form>
</body>
</html>
