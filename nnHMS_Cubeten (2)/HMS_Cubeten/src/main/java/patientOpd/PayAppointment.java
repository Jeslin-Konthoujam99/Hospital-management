package patientOpd;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class PayAppointment
 */
@WebServlet("/PayAppointment")
public class PayAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayAppointment() {
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
		
		Connection connection = null;
        SQLConnect model = new SQLConnect();
        try 
        {
        	connection = model.connect();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		
        // Calculate days between appointments
        PayAppointmentDao pay =new PayAppointmentDao();
        int daysBetweenAppointments = pay.calculateDaysBetweenAppointments(booked_id , connection);
        System.out.println("days between : "+daysBetweenAppointments);
        
       
        // Retrieve discount rate from RATES TABLE
        float discountRate = pay.getDiscountRate(daysBetweenAppointments, connection);
        System.out.println("discount rate: " + discountRate);
       
        // Retrieve doctor rate from DOCTOR RATES TABLE
        float doctorRate = pay.getDoctorRate(booked_id , connection);
        System.out.println("doctor rate: " + doctorRate);

        // Calculate discounted amount
        float discountedAmount = doctorRate * (1 - discountRate);
        System.out.println("discounted Amount: " + discountedAmount);        

        // Display amount for the patient to pay
        request.setAttribute("booked_id", booked_id);
        request.setAttribute("discountedAmount", discountedAmount);
        RequestDispatcher dispatcher = request.getRequestDispatcher("patient/payment.jsp");
        dispatcher.forward(request, response);
		
	}

}
