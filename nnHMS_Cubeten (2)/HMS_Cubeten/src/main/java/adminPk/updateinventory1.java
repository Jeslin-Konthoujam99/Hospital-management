package adminPk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;


/**
 * Servlet implementation class updateinventory1
 */
@WebServlet("/updateinventory1")
public class updateinventory1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateinventory1() {
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
		 try {
			 SQLConnect model = new SQLConnect();
	            Connection con = model.connect();

	            // Assuming your table has columns: itemid, itemname, departmentid, quantity, price, lowStockqty, datetime
	            String updateQuery = "UPDATE inventory SET itemname=?, departmentid=?, quantity=?, price=?, lowStockqty=?, date=? WHERE itemid=?";

	            // Get array data from the form
	            String[] itemIds = request.getParameterValues("itemid");
	            String[] itemNames = request.getParameterValues("itemname");
	            String[] departmentIds = request.getParameterValues("departmentid");
	            String[] quantities = request.getParameterValues("quantity");
	            String[] prices = request.getParameterValues("price");
	            String[] lowStockQtys = request.getParameterValues("lowStockqty");
	            String[] datetimes = request.getParameterValues("datetime");

	            if (itemIds != null && itemNames != null && departmentIds != null && quantities != null && prices != null
	                    && lowStockQtys != null && datetimes != null) {

	                try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
	                    // Loop through the arrays and update the database
	                    for (int i = 0; i < itemIds.length; i++) {
	                        preparedStatement.setString(1, itemNames[i]);
	                        preparedStatement.setString(2, departmentIds[i]);
	                        preparedStatement.setInt(3, Integer.parseInt(quantities[i]));
	                        preparedStatement.setInt(4, Integer.parseInt(prices[i]));
	                        preparedStatement.setInt(5, Integer.parseInt(lowStockQtys[i]));

	                        // Convert String datetime to Timestamp
	                        Timestamp timestamp = Timestamp.valueOf(datetimes[i].replace("T", " ") + ":00");
	                        preparedStatement.setTimestamp(6, timestamp);

	                        preparedStatement.setInt(7, Integer.parseInt(itemIds[i]));

	                        // Execute the update
	                        preparedStatement.executeUpdate();
	                    }

	                    response.sendRedirect("updateinventory.jsp"); // Redirect to a confirmation page or wherever needed
	                }
	            } else {
	                response.getWriter().println("No data received from the form.");
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.getWriter().println("Error updating inventory: " + e.getMessage());
	        }
	    
	}

}
