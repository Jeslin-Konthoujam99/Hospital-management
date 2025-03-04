<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	align-items: center;
	justify-content: center;
	background: linear-gradient(to bottom, #f8f9fa, palegreen);
	height: 100vh;
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
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row g-4">
			<div class="col-md-12 text-center text-primary">
				<span class="display-lg-2 fw-bold"> Patient Registration Form</span>
			</div>
			<div class="col-md-12 col-lg-10 mx-lg-auto">
				<form action="../PatientRegistration" method="post">
					<table class="table table-striped">
						<tr>
							<td><label for="inputName" class="form-label">Name</label></td>
							<td><input type="text" class="form-control" id="inputName"
								name="name" required></td>
						</tr>
						<tr>
							<td><label for="inputFname" class="form-label">Care of
								</label></td>
							<td><input type="text" class="form-control" id="inputFname"
								name="fname" required></td>
						</tr>
						<tr>
							<td><label for="inputAge" class="form-label">Age</label></td>
							<td><input type="number" class="form-control" id="inputAge"
								name="age" required></td>
						</tr>
						<tr>
							<td><label for="inputGender" class="form-label">Gender</label></td>
							<td><select class="form-select" id="inputGender"
								name="gender" required>
									<option value="">Select Gender...</option>
									<option value="male">Male</option>
									<option value="female">Female</option>
							</select></td>
						</tr>
						<tr>
							<td><label for="inputAddress" class="form-label">Address</label></td>
							<td><input type="text" class="form-control"
								id="inputAddress" name="address" required></td>
						</tr>
						<tr>
							<td><label for="inputEmail" class="form-label">Email</label></td>
							<td><input type="email" class="form-control" id="inputEmail"
								name="email" required></td>
						</tr>
						<tr>
							<td><label for="inputContact" class="form-label">Contact</label></td>
							<td><input type="text" class="form-control"
								id="inputContact" name="contact" required></td>
						</tr>
						<tr>
							<td><label for="inputPassword" class="form-label">Password</label></td>
							<td><input type="password" class="form-control"
								id="inputPassword" name="password" required></td>
						</tr>
						<tr>
							<td><label for="inputConfirmPassword" class="form-label">Confirm
									Password</label></td>
							<td><input type="password" class="form-control"
								id="inputConfirmPassword" name="conPassword" required></td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="submit" class="btn btn-primary">Register</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript">
		var password = document.getElementById("inputPassword");
		var confirmPassword = document.getElementById("inputConfirmPassword");

		function validatePassword() {
			if (password.value != confirmPassword.value) {
				confirmPassword.setCustomValidity("Passwords Don't Match");
				confirmPassword.style.border = "1px solid red";
				confirmPassword.focus();
			} else {
				confirmPassword.setCustomValidity('');
				confirmPassword.style.border = "1px solid green";
			}
		}
		var contact = document.getElementById("inputContact");
		var email = document.getElementById("inputEmail");

		function validateContact() {
			if (contact.value.length != 10) {
				contact.setCustomValidity("Contact Number must be 10 digits");
				contact.style.border = "1px solid red";
				contact.focus();
			} else if (isNaN(contact.value)) {
				contact.setCustomValidity("Contact Number must be digits");
				contact.style.border = "1px solid red";
				contact.focus();
			} else {
				contact.setCustomValidity('');
				contact.style.border = "1px solid green";
			}
		}
		function validateEmail() {
			if (email.value.indexOf("@") == -1
					|| email.value.indexOf(".") == -1) {
				email.setCustomValidity("Email must contain @ and .");
				email.style.border = "1px solid red";
				email.focus();
			} else {
				email.setCustomValidity('');
				email.style.border = "1px solid green";
			}
		}
	</script>
</body>
</html>