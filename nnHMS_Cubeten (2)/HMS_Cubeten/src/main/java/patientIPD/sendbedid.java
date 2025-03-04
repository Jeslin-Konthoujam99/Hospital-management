package patientIPD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class sendbedid
 */
@WebServlet("/sendbedid")
public class sendbedid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendbedid() {
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
		String bed_id = request.getParameter("bed_id");
		String patient_id = request.getParameter("patient_id");
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String appointment_date = myDateObj.format(myFormatObj);
		SQLConnect model = new SQLConnect();
		Connection connection = model.connect();
		PreparedStatement ps = null;
		int rs = 0;
		System.out.println(bed_id);
		System.out.println(patient_id);
		try {
			ps = connection.prepareStatement("UPDATE ward SET availability=? , patient_id=?, check_in=? WHERE b_id=?");
			ps.setString(1, "no");
			ps.setString(2, patient_id);
			ps.setString(3, appointment_date);
			ps.setString(4, bed_id);
			System.out.println(patient_id + " inside");
			System.out.println(bed_id);
			System.out.println(appointment_date);
			rs = ps.executeUpdate();
			if (rs > 0) {
				request.setAttribute("message", "Bed Booked");
				request.setAttribute("patient_id", patient_id);
				request.setAttribute("bed_id", bed_id);
				request.setAttribute("appointment_date", appointment_date);
				System.out.println("bed booked");
				request.getRequestDispatcher("patient/ipdappointment.jsp").forward(request, response);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		// TODO Auto-generated method stub
	}

}
