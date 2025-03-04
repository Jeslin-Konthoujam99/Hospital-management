package patientOpd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class CancelOpdAppointment
 */
@WebServlet("/CancelOpdAppointment")
public class CancelOpdAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelOpdAppointment() {
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
        
     // Set the current date as an attribute
        Date currentDate = new Date();
        request.setAttribute("currentDate", currentDate);


        Connection connection = null;
        SQLConnect model = new SQLConnect();
        try {
            connection = model.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Get amount from opd_bill if bill is paid
            float amount = getAmountFromOpdBill(booked_id, connection);
            request.setAttribute("amount", amount);


            // Send amount to patient through bank 
            //and get confirmation (Simulated action)

            // Set appointment_status in book_appointment to cancelled
            updateAppointmentStatus(booked_id, connection);

            // Insert into opd_bill table new record with 
            //negative amount, pay status to refunded
            insertRefundRecord(booked_id, amount, connection);
            
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
                     // Convert java.sql.Date to java.util.Date
                       // java.sql.Date sqlDate = appointmentResultSet.getDate("date");
                       // java.util.Date utilDate = new java.util.Date(sqlDate.getTime());

                      //  request.setAttribute("date", utilDate);
                        request.setAttribute("date",
                        		appointmentResultSet.getDate("date"));
                        
                        // Add other attributes as needed
                    }
                } // end of try-with-resources appointmentResultSet
            } // end of try-with-resources appointmentStatement
System.out.println("Date Attribute Type: " + 
            request.getAttribute("date").getClass().getName());

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
            

          
         // Forward to cancel_receipt.jsp
            RequestDispatcher dispatcher = 
            		request.getRequestDispatcher("patient/cancel_receipt.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException appropriately
            // You might want to redirect to an error page
        }
    }// doPost end

    /**
     * Get amount from opd_bill if the bill is paid
     */
    private float getAmountFromOpdBill
    (int booked_id, Connection connection) throws SQLException 
    {
        float amount = 0.0f;
        // Query to retrieve amount from opd_bill based on booked_id
        String amountQuery = "SELECT amount FROM opd_bill WHERE booked_id = ?";
        try (PreparedStatement amountStatement = 
        		connection.prepareStatement(amountQuery)) {
            amountStatement.setInt(1, booked_id);
            try (ResultSet amountResult = amountStatement.executeQuery()) {
                if (amountResult.next()) {
                    amount = amountResult.getFloat("amount");
                }
            }
        }
        return amount;
    }// getAmountFromOpdBill end

    /**
     * Update appointment_status in book_appointment to cancelled
     */
    private void updateAppointmentStatus
    (int booked_id, Connection connection) throws SQLException 
    {
        // Query to update appointment_status to cancelled
        String updateStatusQuery = 
"UPDATE book_appointment SET appointment_status = 'Cancelled' WHERE booked_id = ?";
        try (PreparedStatement updateStatusStatement = 
        		connection.prepareStatement(updateStatusQuery)) {
            updateStatusStatement.setInt(1, booked_id);
            updateStatusStatement.executeUpdate();
        }
    }// updateAppointmentStatus end

    /**
     * Insert into opd_bill table new record with negative amount, pay status to refunded
     */
    private void insertRefundRecord
    (int booked_id, double amount, Connection connection) throws SQLException 
    {
        // Query to insert a new record into opd_bill
        String insertRefundQuery = 
"INSERT INTO opd_bill (booked_id, pay_status, date, amount) VALUES (?, ?, NOW(), ?)";
        try (PreparedStatement insertRefundStatement = 
        		connection.prepareStatement(insertRefundQuery)) {
            insertRefundStatement.setInt(1, booked_id);
            insertRefundStatement.setString(2, "Refunded");
            insertRefundStatement.setDouble(3, -amount); // Negative amount for refund
            insertRefundStatement.executeUpdate();
        }
    }// insertRefundRecord end
}// CancelOpdAppointment end