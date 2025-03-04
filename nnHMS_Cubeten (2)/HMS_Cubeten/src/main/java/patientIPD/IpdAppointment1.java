package patientIPD;

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
 * Servlet implementation class IpdAppointment1
 */
@WebServlet("/IpdAppointment1")
public class IpdAppointment1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IpdAppointment1() {
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
		
		System.out.println("in ipd appointment seervlet");

		SQLConnect obj=new SQLConnect();
		Connection con=obj.connect();
		PreparedStatement ps=null;
		
		String patient_id=request.getParameter("patientID");
		String patient_name=request.getParameter("patientName");
		String doctor_name=request.getParameter("doctorName");
		String bed_id=request.getParameter("bed_id");
		String ward_id=request.getParameter("ward_id");
		String check_in=request.getParameter("check_in");
		
		try {
			ps=con.prepareStatement(
				"INSERT INTO ipd_appointment(patient_id,patient_name,"
					+"doctor_name,bed_id,ward_id,checkin_date,status) "
						+"VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, patient_id);
			ps.setString(2, patient_name);
			ps.setString(3, doctor_name);
			ps.setString(4, bed_id);
			ps.setString(5, ward_id);
			ps.setString(6, check_in);
			ps.setString(7, "pending");
			ps.executeUpdate();
			response.sendRedirect("patient/viewappointment.jsp");
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	

}

