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
 * Servlet implementation class returnitem
 */
@WebServlet("/returnitem")
public class returnitem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public returnitem() {
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
		 int itemId = Integer.parseInt(request.getParameter("itemid"));
	        int quantityReturned = Integer.parseInt(request.getParameter("quantityReturned"));

	        // Connect to the database (you may use a DAO or any other database access method)
	        SQLConnect model = new SQLConnect();
	        Connection con = model.connect();

	        try {
	            // Retrieve the original price of the returned item from the inventory table
	            PreparedStatement selectStatement = con.prepareStatement("SELECT price FROM inventory WHERE itemid = ?");
	            selectStatement.setInt(1, itemId);
	            ResultSet resultSet = selectStatement.executeQuery();

	            if (resultSet.next()) {
	                // Calculate the total price of the returned items
	                int originalPrice = resultSet.getInt("price");
	                int totalPriceReturned = originalPrice * quantityReturned;

	                // Update the inventory by adjusting the quantity and adding the price
	                PreparedStatement updateInventoryStatement = con.prepareStatement("UPDATE inventory SET quantity = quantity - ? WHERE itemid = ?");
	              //  System.out.println("Generated SQL query: " + updateInventoryStatement);

	                updateInventoryStatement.setInt(1, quantityReturned);
	                //updateInventoryStatement.setInt(2, totalPriceReturned);
	                updateInventoryStatement.setInt(2, itemId);

	                updateInventoryStatement.executeUpdate();

	                // Update the inventorybill table by adding a new entry with transaction_type 'return'
	                PreparedStatement insertInventoryBillStatement = con.prepareStatement("INSERT INTO inventorybill (itemid, amount, qty, date, transaction_type) VALUES (?, ?, ?, NOW(), 'return')");
	                insertInventoryBillStatement.setInt(1, itemId);
	                insertInventoryBillStatement.setInt(2, totalPriceReturned);
	                insertInventoryBillStatement.setInt(3, quantityReturned);

	                insertInventoryBillStatement.executeUpdate();

	                // Redirect to a confirmation page or display a success message
	                PrintWriter out = response.getWriter();
		            response.setContentType("text/html");
		            out.println("<p class='fs-1 bg-danger text-center'>Updated quantity of  inventory table  and returned inventory items.</p>");
		            RequestDispatcher dispatcher = request.getRequestDispatcher("/returninventoryitems.jsp");
		            dispatcher.include(request, response);
	            } 
	           
	            else {
	                // Handle the case where the item ID is not found
	            	 RequestDispatcher dispatcher = request.getRequestDispatcher("/returninventoryitems.jsp");
			            dispatcher.include(request, response);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle database errors
	            response.sendRedirect("returnError.jsp");
	        }
	}

}
