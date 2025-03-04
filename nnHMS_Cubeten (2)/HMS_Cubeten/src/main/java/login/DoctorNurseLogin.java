package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DoctorNurseLogin
 */
@WebServlet("/DoctorNurseLogin")
public class DoctorNurseLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DoctorNurseLogin() {
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
		ResultSet rs = null;
		PreparedStatement ps = null;

		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		HttpSession session = request.getSession();

		try {
			ps = con.prepareStatement(
					"select * from doctorlist where username=? and password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				session.setAttribute("id", rs.getInt("sl"));
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("name", rs.getString("firstname")
						+ " " + rs.getString("lastname"));
				request.getRequestDispatcher("doctor/doctordashboard.jsp")
				.forward(request, response);
			} else {
				ps = con.prepareStatement(
						"select * from nurselist where username=? and password=?");
				ps.setString(1, username);
				ps.setString(2, password);
				rs = ps.executeQuery();
				if (rs.next()) {
					session.setAttribute("id", rs.getString("empid"));
					session.setAttribute("username", username);
					session.setAttribute("password", password);
					session.setAttribute("nursename", rs.getString("firstname")
							+ " " + rs.getString("lastname"));
					request.getRequestDispatcher("nurse/nursedashboard.jsp")
					.forward(request, response);
				} else {
					response.sendRedirect("login/doctorNurseLogin.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login/doctorNurseLogin.jsp");
		}

	}

}
