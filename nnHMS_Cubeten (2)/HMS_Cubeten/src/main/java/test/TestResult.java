package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;


/**
 * Servlet implementation class TestResult
 */
@WebServlet("/TestResult")
public class TestResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestResult() {
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
		// TODO Auto-generated method stub
		  String bloodIdStr = request.getParameter("bloodId");
	        String bloodType = request.getParameter("bloodType");
	        String technicianIdStr = request.getParameter("technicianId");

	        int bloodId = Integer.parseInt(bloodIdStr);
	        int technicianId = Integer.parseInt(technicianIdStr);

	        // Database operation
	        SQLConnect model = new SQLConnect();
	        try (Connection connection = model.connect()) {
	            String sql = "INSERT INTO testresult (Blood_id, Blood_type, Employee_id) VALUES (?, ?, ?)";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setInt(1, bloodId);
	                statement.setString(2, bloodType);
	                statement.setInt(3, technicianId); // Assuming Employee_id is used for Technician
	                statement.executeUpdate();

	                response.setContentType("text/html;charset=UTF-8");
	                response.getWriter().println("<p class='fs-1 fc-success text-center'>Test result inserted.</p>");
	                RequestDispatcher rd = request.getRequestDispatcher("test/TestResult.jsp");
	                rd.include(request, response);
	            }
	        } catch (SQLException e) {
	            // Handle database errors
	            throw new ServletException("Database error", e);
	        }
	}
}
