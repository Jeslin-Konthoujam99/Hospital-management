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
 * Servlet implementation class buyinventory
 */
@WebServlet("/buyinventory")
public class buyinventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public buyinventory() {
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
	        int purchasedQuantity = Integer.parseInt(request.getParameter("qty"));
	       // int pricePerItem = Integer.parseInt(request.getParameter("pricePerItem"));

	        try {
	        	SQLConnect model = new SQLConnect();
	            Connection con = model.connect();
	            
	            // SQL query to retrieve the price from the inventory table
	            String selectPriceQuery = "SELECT price FROM inventory WHERE itemid = ?";
	            int pricePerItem = 0; // Initialize to default value

	            try (PreparedStatement selectPriceStatement = con.prepareStatement(selectPriceQuery)) {
	                selectPriceStatement.setInt(1, itemId);
	                ResultSet priceResultSet = selectPriceStatement.executeQuery();

	                if (priceResultSet.next()) {
	                    // Retrieve the price from the result set
	                    pricePerItem = priceResultSet.getInt("price");
	                }
	            }
	            
	            // SQL query to update the inventory table
	            String updateInventoryQuery = "UPDATE inventory SET quantity = quantity + ? WHERE itemid = ?";
	            try (PreparedStatement updateInventoryStatement = con.prepareStatement(updateInventoryQuery)) {
	                updateInventoryStatement.setInt(1, purchasedQuantity);
	                updateInventoryStatement.setInt(2, itemId);
	                updateInventoryStatement.executeUpdate();
	                
	                PrintWriter out = response.getWriter();
		            response.setContentType("text/html");
		            out.println("<p class='fs-1 bg-danger text-center'>Updated quantity of  inventory table.</p>");
	            }

	            // SQL query to update the inventorybill table
	            String updateInventoryBillQuery = "INSERT INTO inventorybill (itemid, Amount, qty, date, transaction_type) VALUES (?, ?, ?, NOW(),'buy')";
	            try (PreparedStatement updateInventoryBillStatement = con.prepareStatement(updateInventoryBillQuery)) {
	                int totalPrice = purchasedQuantity * pricePerItem;
	                updateInventoryBillStatement.setInt(1, itemId);
	                updateInventoryBillStatement.setInt(2, totalPrice);
	                updateInventoryBillStatement.setInt(3, purchasedQuantity);
	                updateInventoryBillStatement.executeUpdate();
	                
	                PrintWriter out = response.getWriter();
		            response.setContentType("text/html");
		            out.println("<p class='fs-1 bg-danger text-center'>And  Inventory items purchased successfully.</p>");
	            }
	           
	           // Redirect to a confirmation page or wherever needed
	            RequestDispatcher rd = request.getRequestDispatcher("/buyinventoryitems.jsp");
                rd.include(request, response);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.getWriter().println("Error buying inventory items: " + e.getMessage());
	        }
	}

}
