package patientIPD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class IpdAppointment
 */
@WebServlet("/IpdAppointment")
public class IpdAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IpdAppointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ")
		.append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		int patientID = Integer.parseInt(request.getParameter("patient_id"));
		Connection connection = null;
		SQLConnect model = new SQLConnect();
		try {
			connection = model.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<IpdAppointmentObj> appointments = new ArrayList<>();
		try {
			// Your SQL query
			String sql = "SELECT " + "opd_patient.patientid, " 
			+ "opd_patient.fullname, ward.ward_name, "
			+ "doctorlist.firstname AS doctor_firstname, " 
			+ "nurselist.firstname AS nurse_firstname, "
			+ "ipd_appointment.ipd_appointment_id, " 
			+ "ipd_appointment.checkin_date, "
			+ "ipd_appointment.checkout_date, " 
			+ "ipd_appointment.status FROM " 
			+ "opd_patient " 
			+ "JOIN "
			+ "ipd_appointment ON opd_patient.patientid = "
			+ "ipd_appointment.patient_id " 
			+ "JOIN "
			+ "ward ON ipd_appointment.ward_id = ward.ward_id " 
			+ "LEFT JOIN "
			+ "doctorlist ON ipd_appointment.doctor_id = doctorlist.sl " 
			+ "LEFT JOIN "
			+ "nurselist ON ipd_appointment.nurse_id = nurselist.nurse_id " 
			+ "where opd_patient.patientid = ?";

			// Execute the query
			PreparedStatement preparedStatement = 
					connection.prepareStatement(sql);
			preparedStatement.setInt(1, patientID);
			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set
			while (resultSet.next()) {
				IpdAppointmentObj appointment = new IpdAppointmentObj();
				appointment.setIpd_appointment_id(
						resultSet.getInt("ipd_appointment_id"));
				appointment.setFullname(resultSet.getString("fullname"));
				appointment.setWardName(resultSet.getString("ward_name"));
				appointment.setDoctorFirstname(resultSet.getString(
						"doctor_firstname"));
				appointment.setNurseFirstname(resultSet.getString(
						"nurse_firstname"));
				appointment.setCheckinDate(resultSet.getString("checkin_date"));
				appointment.setCheckoutDate(resultSet.getString("checkout_date"));
				appointment.setStatus(resultSet.getString("status"));

				appointments.add(appointment);
			}

			// Close resources
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

// Store the result in the request
		request.setAttribute("ipd_appointments", appointments);

// Forward the request to the JSP page
		request.getRequestDispatcher("/ipd_appointments.jsp")
		.forward(request, response);

	}// doPost

}// IpdAppointment
