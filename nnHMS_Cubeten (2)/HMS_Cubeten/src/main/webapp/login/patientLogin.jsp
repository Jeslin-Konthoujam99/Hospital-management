<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Patient Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	height: 100vh;
	align-items: center;
	justify-content: center;
	background-color: #f8f9fa;
	padding: 50px;
}

a {
	text-decoration: none;
	color: #ff0000;
}

a:hover {
	text-decoration: underline;
	color: orange;
	transition: all 0.3s ease-in-out;
}

@media ( max-width : 768px) {
	body {
		padding: 20px;
	}
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row d-md-block d-lg-flex g-4">
			<div class="col-md-12 text-center text-primary">
				<span class="display-lg-1 fw-bold"> HOSPITAL MANAGEMENT
					SYSTEM</span>
			</div>
			<div class="col-md-12 col-lg-6 d-none d-lg-block">
				<img src="../images/login.jpg" class="img-fluid w-100">
			</div>
			<div class="col-md-12 col-lg-6 bg-success p-5">
				<h3 class="text-white mt-md-4">Login</h3>
				<hr>
				<p class="text-white">Please enter your email and password.</p>
				<form action="../PatientLogin" method="post" class="was-validated">
					<div class="form-group">
						<input type="email" class="form-control" id="username"
							name="username" placeholder="Enter username"
							title="Your Email as your username" required>
						<div class="invalid-feedback">Please enter a email.</div>
					</div>
					<br>
					<div class="form-group">
						<input type="password" class="form-control" id="password"
							name="password" placeholder="Enter password" required>
						<div class="invalid-feedback">Please enter a password.</div>
					</div>
					<br>
					<button type="submit" class="btn btn-primary">Login</button>
					<br> <br> <span class="text-white">Don't have an
						account? </span><a href="patientRegister.jsp">Register</a>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>