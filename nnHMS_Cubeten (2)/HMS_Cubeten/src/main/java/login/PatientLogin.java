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
 * Servlet implementation class PatientLogin
 */
@WebServlet("/PatientLogin")
public class PatientLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SQLConnect obj = new SQLConnect();
		Connection con = obj.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String email = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		try {
			ps = con.prepareStatement(
				"select * from opd_patient where email=? and password=?");
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				session.setAttribute("patientid", rs.getInt("patientid"));
				session.setAttribute("username", email);
				session.setAttribute("password", password);
				session.setAttribute("fullname", rs.getString("fullname"));
				session.setAttribute("age", rs.getInt("age"));
				session.setAttribute("gender", rs.getString("gender"));
				session.setAttribute("care_of", rs.getString("care_of"));
				session.setAttribute("phone", rs.getDouble("phone"));
				session.setAttribute("address", rs.getString("address"));
				request.getRequestDispatcher("patient/patientDashboard.jsp")
				.forward(request, response);
			} else {
				response.sendRedirect("login/patientLogin.jsp");
				System.out.println("failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("login/patientLogin.jsp");
			System.out.println(e);
		}
	}

}
