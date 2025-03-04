package patientOpd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayAppointmentDao {
	public int calculateDaysBetweenAppointments(int bookedId , Connection connection) 
	{
		 int daysBetween = -1; // Default value indicating an error or no previous appointment

	        try (PreparedStatement statement = connection.prepareStatement(
	                     "SELECT patient_id, doctor_id, date " +
	                             "FROM book_appointment " +
	                             "WHERE booked_id = ?")) {

	            statement.setInt(1, bookedId);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    int patientID = resultSet.getInt("patient_id");
	                    int doctorID = resultSet.getInt("doctor_id");
	                    Date currentAppointmentDate = resultSet.getDate("date");

	                    // Find the previous appointment with the same doctor that occurred before the selected appointment
	                    try (PreparedStatement previousAppointmentStatement = connection.prepareStatement(
	                            "SELECT date " +
	                                    "FROM book_appointment " +
	                                    "WHERE patient_id = ? AND doctor_id = ? AND booked_id != ? AND date < ? " +
	                                    "ORDER BY date DESC " +
	                                    "LIMIT 1")) {

	                        previousAppointmentStatement.setInt(1, patientID);
	                        previousAppointmentStatement.setInt(2, doctorID);
	                        previousAppointmentStatement.setInt(3, bookedId);
	                        previousAppointmentStatement.setDate(4, currentAppointmentDate);

	                        try (ResultSet previousAppointmentResultSet = previousAppointmentStatement.executeQuery()) {
	                            if (previousAppointmentResultSet.next()) {
	                                Date previousAppointmentDate = previousAppointmentResultSet.getDate("date");

	                                // Calculate days between appointments
	                                long millisecondsBetween = currentAppointmentDate.getTime() - previousAppointmentDate.getTime();
	                                daysBetween = (int) (millisecondsBetween / (24 * 60 * 60 * 1000));
	                            }
	                        }
	                    }
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }

	        return daysBetween;
    }//calculateDaysBetweenAppointments end
	
	
	public float getDiscountRate(int daysBetweenAppointments, Connection connection) {
        float discountRate = 0.0f;

        try {
            String query = "SELECT discount_rate FROM RATES_TABLE WHERE days_between_appointments <= ? ORDER BY days_between_appointments DESC LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, daysBetweenAppointments);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        discountRate = resultSet.getFloat("discount_rate");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discountRate;
    }//getDiscountRate end 
	

    public float getDoctorRate(int bookedId, Connection connection) {
        float doctorRate = 0.0f;

        try {
            String query = "SELECT dr.rate " +
                           "FROM doctor_rates dr " +
                           "JOIN book_appointment ba ON dr.doctor_id = ba.doctor_id " +
                           "WHERE ba.booked_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, bookedId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        doctorRate = resultSet.getFloat("rate");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorRate;
    }//getDoctorRate end
	
	
	
	
	
	
	

}//class PayAppointmentDao end
