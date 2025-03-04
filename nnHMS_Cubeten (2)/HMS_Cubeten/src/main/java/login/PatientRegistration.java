package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PatientRegistration
 */
@WebServlet("/PatientRegistration")
public class PatientRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PatientRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SQLConnect obj = new SQLConnect();
		Connection con = obj.connect();
		PreparedStatement ps = null;
		String password = request.getParameter("password");
		String conpassword = request.getParameter("conPassword");
		try {
			ps = con.prepareStatement(
					"insert into opd_patient(fullname,age,gender,care_of,"
					+ "email,phone,address,password) values(?,?,?,?,?,?,?,?)");
			ps.setString(1, request.getParameter("name"));
			ps.setInt(2, Integer.parseInt(request.getParameter("age")));
			ps.setString(3, request.getParameter("gender"));
			ps.setString(4, request.getParameter("fname"));
			ps.setString(5, request.getParameter("email"));
			ps.setDouble(6, Double.parseDouble(request.getParameter("contact")));
			ps.setString(7, request.getParameter("address"));

			if (password.equals(conpassword)) {
				ps.setString(8, password);
				ps.executeUpdate();
				System.out.println("register executed");
				response.sendRedirect("index.html");
			} else {
				System.out.println("register not executed");
				response.sendRedirect("login/patientRegister.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Data not inserted...");
			response.sendRedirect("login/patientRegister.jsp");
		}

	}

}
