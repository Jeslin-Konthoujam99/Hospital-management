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
 * Servlet implementation class buyEquipment
 */
@WebServlet("/buyEquipment")
public class buyEquipment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public buyEquipment() {
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
	        int purchasedQuantity = Integer.parseInt(request.getParameter("qty"));

	        try {
	        	SQLConnect model = new SQLConnect();
	            Connection con = model.connect();

	            // SQL query to retrieve the price from the equipment table
	            String selectPriceQuery = "SELECT price FROM equipment WHERE equipmentid = ?";
	            int pricePerItem = 0; // Initialize to default value

	            try (PreparedStatement selectPriceStatement = con.prepareStatement(selectPriceQuery)) {
	                selectPriceStatement.setInt(1, equipmentId);
	                ResultSet priceResultSet = selectPriceStatement.executeQuery();

	                if (priceResultSet.next()) {
	                    // Retrieve the price from the result set
	                    pricePerItem = priceResultSet.getInt("price");
	                }
	            }

	            // SQL query to update the equipment table
	            String updateEquipmentQuery = "UPDATE equipment SET quantity = quantity + ? WHERE equipmentid = ?";
	            try (PreparedStatement updateEquipmentStatement = con.prepareStatement(updateEquipmentQuery)) {
	                updateEquipmentStatement.setInt(1, purchasedQuantity);
	                updateEquipmentStatement.setInt(2, equipmentId);
	                updateEquipmentStatement.executeUpdate();

	                PrintWriter out = response.getWriter();
	                response.setContentType("text/html");
	                out.println("<p class='fs-1 bg-danger text-center'>Updated quantity of equipment table.</p>");
	            }

	            // SQL query to update the equipmentbill table
	            String updateEquipmentBillQuery = "INSERT INTO equipmentbill (equipmentid, Amount, qty, date, transaction_type) VALUES (?, ?, ?, NOW(),'buy')";
	            try (PreparedStatement updateEquipmentBillStatement = con.prepareStatement(updateEquipmentBillQuery)) {
	                int totalPrice = purchasedQuantity * pricePerItem;
	                updateEquipmentBillStatement.setInt(1, equipmentId);
	                updateEquipmentBillStatement.setInt(2, totalPrice);
	                updateEquipmentBillStatement.setInt(3, purchasedQuantity);
	                updateEquipmentBillStatement.executeUpdate();

	                PrintWriter out = response.getWriter();
	                response.setContentType("text/html");
	                out.println("<p class='fs-1 bg-danger text-center'>Equipment items purchased successfully.</p>");
	            }

	            // Redirect to a confirmation page or wherever needed
	            RequestDispatcher rd = request.getRequestDispatcher("/buyEquipmentItems.jsp");
	            rd.include(request, response);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.getWriter().println("Error buying equipment items: " + e.getMessage());
	        }
	}

}
