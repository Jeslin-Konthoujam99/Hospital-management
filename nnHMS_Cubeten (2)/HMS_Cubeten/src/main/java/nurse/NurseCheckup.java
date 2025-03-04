package nurse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;


/**
 * Servlet implementation class NurseCheckup
 */
@WebServlet("/NurseCheckup")
public class NurseCheckup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NurseCheckup() {
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

		int app_id =Integer.parseInt( request.getParameter("app_id"));
		//int patient_id =Integer.parseInt( request.getParameter("pid"));
		String BP = request.getParameter("BP");
		String weight = request.getParameter("weight");
		String height = request.getParameter("height");
		String temperature = request.getParameter("temperature");
		String pulse = request.getParameter("pulse");
		String bmi = request.getParameter("bmi");

		try {
			ps = con.prepareStatement(
					"UPDATE book_appointment SET appointment_status="
					+ "'seen by nurse' where booked_id =?");
			ps.setInt(1, app_id);
			ps.executeUpdate();
			ps = con.prepareStatement(
					"INSERT INTO prescriptions1 (bp,weight,height,temperature,"
					+"pulse,bmi,appointment_id) VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, BP);
			ps.setString(2, weight);
			ps.setString(3, height);
			ps.setString(4, temperature);
			ps.setString(5, pulse);
			ps.setString(6, bmi);
			ps.setInt(7, app_id);

			ps.executeUpdate();
			System.out.println("Inserted checkup data");
			response.sendRedirect("nurse/nursedashboard.jsp");
		} catch (Exception e) {
			System.out.println("some exception . vitals data not inserted : "+e);
			response.sendRedirect("nurse/nursedashboard.jsp");
		}
	}
}
