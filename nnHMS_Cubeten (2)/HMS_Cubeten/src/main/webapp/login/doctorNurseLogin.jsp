<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Staff Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<style>
body {
	background: url('images/loginback.jpg') no-repeat;
	background-postition: center;
	background-size: cover;
	background-attachment: fixed;
	padding-top: 100px;
}

a {
	text-decoration: none;
}
</style>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<div class="card shadow col-md-12 col-lg-6 mx-lg-auto m-md-3">
				<div class="card-header py-5 fs-2">
					<i class="bi bi-person-circle"></i>Doctor / Nurse Login
				</div>
				<div class="card-body">
					<form action="../DoctorNurseLogin" method="post" class="was-validated">
						<div class="input-group mb-md-5 mb-lg-3">
							<span class="input-group-text bg-primary text-white" id="addon1"><i
								class="bi bi-people"></i></span> <input type="text"
								class="form-control" id="inputUsername" name="Username" required
								aria-describedby="addon1">
							<div class="invalid-feedback">Please enter a username.</div>
						</div>
						<div class="input-group mb-md-5 mb-lg-3">
							<span class="input-group-text bg-primary text-white" id="addon1"><i
								class="bi bi-key"></i></span> <input type="password"
								class="form-control" id="inputDoctorPassword" name="Password"
								required>
							<div class="invalid-feedback">Please enter a password.</div>
						</div>
						<button type="submit" class="btn btn-primary">Login</button>
					</form>
				</div>
			</div>
		</div>
	</div>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>