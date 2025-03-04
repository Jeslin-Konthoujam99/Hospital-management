package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class InsertTestBillServlet
 */
@WebServlet("/InsertTestBillServlet")
public class InsertTestBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertTestBillServlet() {
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
		// TODO Auto-generated method stub
		String prescriptionIdStr = request.getParameter("prescriptionId");
		String amountStr = request.getParameter("amount");
		// String dateStr = request.getParameter("date");
		String paymentStatus = request.getParameter("paymentStatus");

		// Convert prescriptionId to int
		int prescriptionId = Integer.parseInt(prescriptionIdStr);

		// Convert amount to BigDecimal
		BigDecimal amount = new BigDecimal(amountStr);

		// Convert date to java.sql.Date
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		// Database operation
		SQLConnect model = new SQLConnect();
		Connection connection = model.connect();
		try {
			String sql = "INSERT INTO testbill (prescription_id, amount, date, payment_status) VALUES (?, ?, ?, ?)";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, prescriptionId);
				statement.setBigDecimal(2, amount);
				statement.setTimestamp(3, currentTimestamp);

				statement.setString(4, paymentStatus);
				statement.executeUpdate();

				PrintWriter out = response.getWriter();
				response.setContentType("text/html");
				out.println("<p class='fs-1 fc-success text-center'>Test bill generated.</p>");
				RequestDispatcher rd = request.getRequestDispatcher("test/insertTestBill.jsp");
				rd.include(request, response);
			}
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<p class='fs-1 fc-success text-center'>Failed to generate Test bill.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("test/insertTestBill.jsp");
			rd.include(request, response);
		} catch (SQLException e) {
			// Handle database errors
			throw new ServletException("Database error", e);

		}

		// Redirect back to the form or a success page
		response.sendRedirect("test/insertTestBill.jsp");
	}

}
