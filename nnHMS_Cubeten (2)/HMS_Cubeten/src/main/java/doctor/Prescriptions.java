package doctor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;


/**
 * Servlet implementation class Prescriptions
 */
@WebServlet("/Prescriptions")
public class Prescriptions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Prescriptions() {
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

		int appointmentid = Integer.parseInt(	request.getParameter("pid") );
		String medication = request.getParameter("medication");
		String test = request.getParameter("test");
		String recommendation = request.getParameter("recommendation");

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String prescription_date = dateFormat.format(date);

		try {
			ps = con.prepareStatement(
				"UPDATE prescriptions1 set medicine = ? , test = ? ,"
			+ "recommendation = ? , prescription_date = ? where appointment_id=?");
			ps.setString(1, medication);
			ps.setString(2, test);
			ps.setString(3, recommendation);
			ps.setString(4, prescription_date);
			ps.setInt(5, appointmentid);
			ps.executeUpdate();
			System.out.println("updated");
			request.getRequestDispatcher("doctor/patienthistory.jsp")
			.forward(request, response);
		} catch (Exception e) {
			System.out.println("exception in prescription.java in doctor pk . ");
			e.printStackTrace();
			request.getRequestDispatcher("doctor/patienthistory.jsp")
			.forward(request, response);		
		}
	}
}