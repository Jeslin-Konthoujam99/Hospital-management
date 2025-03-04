package patientOpd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;


/**
 * Servlet implementation class OpdBillPaid
 */
@WebServlet("/OpdBillPaid")
public class OpdBillPaid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpdBillPaid() {
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
		 int booked_id = Integer.parseInt(request.getParameter("booked_id"));
	        request.setAttribute("booked_id", booked_id);

	        float payAmount = Float.parseFloat(request.getParameter("pay"));
	     // Set pay parameter in request attribute
	        request.setAttribute("pay", payAmount);

	        // Perform payment processing and acknowledgement here...

	        // Fetch relevant data from the database
	        SQLConnect model = new SQLConnect();
	        Connection connection = null;
	        try {
	            connection = model.connect();

	         // Fetch data from book_appointment
	            int ptid=0,dpid=0,dcid=0;
	            String appointmentQuery = 
	            		"SELECT * FROM book_appointment WHERE booked_id = ?";
	            try (PreparedStatement appointmentStatement = 
	            		connection.prepareStatement(appointmentQuery)) {
	                appointmentStatement.setInt(1, booked_id);
	                try (ResultSet appointmentResultSet = 
	                		appointmentStatement.executeQuery()) {
	                    if (appointmentResultSet.next()) {
	                        // Set the relevant data in request attributes
	                       
	                        ptid=appointmentResultSet.getInt("patient_id");
	                        dpid=appointmentResultSet.getInt("department_id");
	                        dcid=appointmentResultSet.getInt("doctor_id");
	                        request.setAttribute("date",
	                        		appointmentResultSet.getDate("date"));
	                        // Add other attributes as needed
	                    }
	                } // end of try-with-resources appointmentResultSet
	            } // end of try-with-resources appointmentStatement

	            // Fetch patient name from opd_patient
	            String patientQuery =
	            		"SELECT fullname FROM opd_patient WHERE patientid = ?";
	            try (PreparedStatement patientStatement = 
	            		connection.prepareStatement(patientQuery)) {
	                patientStatement.setInt(1, ptid);
	                try (ResultSet patientResultSet =
	                		patientStatement.executeQuery()) {
	                    if (patientResultSet.next()) {
	                        request.setAttribute("patient_name",
	                        		patientResultSet.getString("fullname"));
	                    }
	                } // end of try-with-resources patientResultSet
	            } // end of try-with-resources patientStatement

	            // Fetch department name from your_department_table 
	            String departmentQuery = 
	            		"SELECT dpt_name FROM department WHERE department_id = ?";
	            try (PreparedStatement departmentStatement = 
	            		connection.prepareStatement(departmentQuery)) {
	                departmentStatement.setInt(1, dpid);
	                try (ResultSet departmentResultSet = departmentStatement.executeQuery()) {
	                    if (departmentResultSet.next()) {
	                        request.setAttribute("department_name",
	                        		departmentResultSet.getString("dpt_name"));
	                    }
	                } // end of try-with-resources departmentResultSet
	            } // end of try-with-resources departmentStatement

	            // Fetch doctor name from your_doctor_table 
	            String doctorQuery = 
	            "SELECT firstname FROM doctorlist WHERE sl = ?";
	            try (PreparedStatement doctorStatement = 
	            		connection.prepareStatement(doctorQuery)) {
	                doctorStatement.setInt(1, dcid);
	                try (ResultSet doctorResultSet = doctorStatement.executeQuery()) {
	                    if (doctorResultSet.next()) {
	                        request.setAttribute("doctor_name",
	                        		doctorResultSet.getString("firstname"));
	                    }
	                } // end of try-with-resources doctorResultSet
	            } // end of try-with-resources doctorStatement
	            
	      
	// insert into opd_bill
	            String payStatus = "Paid"; 
	            java.util.Date utilDate = new java.util.Date(); // Current date
	            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

	            try {
	                String insertQuery = 
	 "INSERT INTO opd_bill (booked_id, pay_status, date, amount) VALUES (?, ?, ?, ?)";

	                try (PreparedStatement preparedStatement = 
	                		connection.prepareStatement(insertQuery)) {
	                    preparedStatement.setInt(1, booked_id);
	                    preparedStatement.setString(2, payStatus);
	                    preparedStatement.setDate(3, sqlDate);
	                    preparedStatement.setFloat(4, payAmount);

	                    int rowsAffected = preparedStatement.executeUpdate();

	                    if (rowsAffected > 0) {
	                        System.out.println("Data inserted into opd_bill successfully!");
	                    } else {
	                        System.out.println("Failed to insert data in opd_bill  .");
	                    }
	                } // end of try-with-resources preparedStatement
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } // end of try-with-resources connection

	// Update appointment status
	            String updateQuery = 
	       "UPDATE book_appointment SET appointment_status = 'Confirmed' WHERE booked_id = ?";
	            try (PreparedStatement updateStatement = 
	            		connection.prepareStatement(updateQuery)) {
	                updateStatement.setInt(1, booked_id);
	                updateStatement.executeUpdate();
	            } // end of try-with-resources updateStatement
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        } // end of finally

	        

	        // Forward to opd_receipt.jsp
	        RequestDispatcher dispatcher = 
	        		request.getRequestDispatcher("patient/opd_receipt.jsp");
	        dispatcher.forward(request, response);
	    } // end of doPost
	} // end of OpdBillPaidServlet

