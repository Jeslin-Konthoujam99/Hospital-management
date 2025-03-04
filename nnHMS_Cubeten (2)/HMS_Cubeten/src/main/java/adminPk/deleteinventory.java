package adminPk;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;


/**
 * Servlet implementation class deleteinventory
 */
@WebServlet("/deleteinventory")
public class deleteinventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteinventory() {
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
		  String[] selectedItems = request.getParameterValues("selectedItems");

	        if (selectedItems != null) {
	            try {
	            	SQLConnect model = new SQLConnect();
	                Connection con = model.connect();

	                // Assuming your table has a primary key column named "itemid"
	                String deleteQuery = "DELETE FROM inventory WHERE itemid = ?";

	                try (PreparedStatement preparedStatement = con.prepareStatement(deleteQuery)) {
	                    // Loop through the selected items and delete them from the database
	                    for (String itemId : selectedItems) {
	                        preparedStatement.setInt(1, Integer.parseInt(itemId));
	                        preparedStatement.executeUpdate();
	                    }

	                    response.sendRedirect("deleteinventory.jsp"); // Redirect to a confirmation page or wherever needed
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	                response.getWriter().println("Error deleting inventory items: " + e.getMessage());
	            }
	        } else {
	            response.getWriter().println("No items selected for deletion.");
	        }
	}

}
