package adminPk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class updateinventory
 */
@WebServlet("/updateinventory")
public class updateinventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateinventory() {
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
		System.out.println("inside servlet updateinventory");
	        String itemid = request.getParameter("itemid");
	        String itemname = request.getParameter("itemname");
	        String departmentid = request.getParameter("departmentid");
	        String quantity = request.getParameter("quantity");
	        String price = request.getParameter("price");
	        String lowStockqty = request.getParameter("lowStockqty");
	        String datetime = request.getParameter("datetime");
	        System.out.println("itemid    :"+ itemid   + "date :    "+  datetime);
	        
	        // Extract date part (yyyy-MM-dd)
	        String datePart = datetime.substring(0, 10);
	        String timePart = datetime.substring(12);
	        String date1 = datePart + " " + timePart+":00";
	     // Convert string to Timestamp
	        Timestamp timestamp = Timestamp.valueOf(date1);
//	        
	        System.out.println("Timestamp: " + timestamp);

	        try {
	        	SQLConnect model = new SQLConnect();
	            Connection con = model.connect();
	           // Timestamp datetime1 = convertStringToTimestamp(datetime);
	           // Timestamp timestamp = Timestamp.valueOf(datetime);
	            // Perform the update
	            String updateQuery = "UPDATE inventory SET itemname = ?, departmentid = ?, quantity = ?, price = ?, lowStockqty = ?, date = ? WHERE itemid = ?";
	            try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
	                preparedStatement.setString(1, itemname);
	                preparedStatement.setString(2, departmentid);
	                preparedStatement.setInt(3, Integer.parseInt(quantity));
	                preparedStatement.setInt(4, Integer.parseInt(price));
	                preparedStatement.setInt(5, Integer.parseInt(lowStockqty));
	                preparedStatement.setTimestamp(6, timestamp);
	                preparedStatement.setInt(7, Integer.parseInt(itemid));

	                preparedStatement.executeUpdate();
	                System.out.println("Item updated successfully");

	                PrintWriter out = response.getWriter();
	                response.setContentType("text/html");
	                out.println("<p class='fs-1 fc-success text-center'>Item updated successfully</p>");
	                RequestDispatcher rd = request.getRequestDispatcher("/updateinventory.jsp");
	                rd.include(request, response);
	            } catch (SQLException e) {
	                e.printStackTrace();

	                PrintWriter out = response.getWriter();
	                response.setContentType("text/html");
	                out.println("<p class='fs-1 bg-danger text-center'>Unable to update. Please retry after reload.</p>");
	                RequestDispatcher rd = request.getRequestDispatcher("/updateinventory.jsp");
	                rd.include(request, response);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  private Timestamp convertStringToTimestamp(String datetime) {
		  
		  try {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	            
	            // Parse the input datetime string to a Date object
	            Date parsedDate = dateFormat.parse(datetime);
	            
	            // Format the Date object to the desired format
	            String formattedDate = dateFormat.format(parsedDate);
	            
	            // Replace 'T' with a space
	            formattedDate = formattedDate.replaceAll("T", " ");
	            
	            // Convert the formatted string to a Timestamp
	            Timestamp timestamp = Timestamp.valueOf(formattedDate);
	            
	            System.out.println("Timestamp  :: " + timestamp);
	            return timestamp;
	        } catch (ParseException e) {
	            e.printStackTrace();
	            // Handle the ParseException as needed
	            return null;
	        }//catch
	    }//convertStringToTimestamp

}
