package adminPk;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class returnEquipment
 */
@WebServlet("/returnEquipment")
public class returnEquipment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public returnEquipment() {
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
		// TODO Auto-generated method stub
		int equipmentId = Integer.parseInt(request.getParameter("equipmentid"));
        int quantityReturned = Integer.parseInt(request.getParameter("quantityReturned"));

        // Connect to the database (you may use a DAO or any other database access method)
        SQLConnect model = new SQLConnect();
        Connection con = model.connect();

        try {
            // Retrieve the original price of the returned equipment from the equipment table
            PreparedStatement selectStatement = con.prepareStatement("SELECT price FROM equipment WHERE equipmentid = ?");
            selectStatement.setInt(1, equipmentId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Calculate the total price of the returned equipment
                int originalPrice = resultSet.getInt("price");
                int totalPriceReturned = originalPrice * quantityReturned;

                // Update the equipment by adjusting the quantity
                PreparedStatement updateEquipmentStatement = con
                        .prepareStatement("UPDATE equipment SET quantity = quantity - ? WHERE equipmentid = ?");
                updateEquipmentStatement.setInt(1, quantityReturned);
                updateEquipmentStatement.setInt(2, equipmentId);

                updateEquipmentStatement.executeUpdate();

                // Update the equipmentbill table by adding a new entry with transaction_type 'return'
                PreparedStatement insertEquipmentBillStatement = con.prepareStatement(
                        "INSERT INTO equipmentbill (equipmentid, Amount, qty, date, transaction_type) VALUES (?, ?, ?, NOW(), 'return')");
                insertEquipmentBillStatement.setInt(1, equipmentId);
                insertEquipmentBillStatement.setInt(2, totalPriceReturned);
                insertEquipmentBillStatement.setInt(3, quantityReturned);

                insertEquipmentBillStatement.executeUpdate();

                // Redirect to a confirmation page or display a success message
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println(
                        "<p class='fs-1 bg-danger text-center'>Updated quantity of equipment table and returned equipment items.</p>");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/returnequipementitems.jsp");
                dispatcher.include(request, response);
            } else {
                // Handle the case where the equipment ID is not found
                RequestDispatcher dispatcher = request.getRequestDispatcher("/returnequipementitems.jsp");
                dispatcher.include(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
            response.sendRedirect("returnError.jsp");
        }
	}

}
