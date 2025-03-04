package adminPk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;

/**
 * Servlet implementation class updateEquipment
 */
@WebServlet("/updateEquipment")
public class updateEquipment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateEquipment() {
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
		String equipmentid = request.getParameter("equipmentid");
        String equipmentname = request.getParameter("equipmentname");
        String departmentid = request.getParameter("departmentid");
        String quantity = request.getParameter("quantity");
        String price = request.getParameter("price");
        String lowStockqty = request.getParameter("lowStockqty");
        String datetime = request.getParameter("datetime");

        // Extract date part (yyyy-MM-dd)
        String datePart = datetime.substring(0, 10);
        String timePart = datetime.substring(12);
        String date1 = datePart + " " + timePart + ":00";
        // Convert string to Timestamp
        Timestamp timestamp = Timestamp.valueOf(date1);

        try {
        	SQLConnect model = new SQLConnect();
            Connection con = model.connect();

            // Perform the update
            String updateQuery = "UPDATE equipment SET equipmentname = ?, departmentid = ?, quantity = ?, price = ?, lowStockqty = ?, date = ? WHERE equipmentid = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, equipmentname);
                preparedStatement.setString(2, departmentid);
                preparedStatement.setInt(3, Integer.parseInt(quantity));
                preparedStatement.setInt(4, Integer.parseInt(price));
                preparedStatement.setInt(5, Integer.parseInt(lowStockqty));
                preparedStatement.setTimestamp(6, timestamp);
                preparedStatement.setInt(7, Integer.parseInt(equipmentid));

                preparedStatement.executeUpdate();
                System.out.println("Equipment updated successfully");

                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<p class='fs-1 fc-success text-center'>Equipment updated successfully</p>");
                RequestDispatcher rd = request.getRequestDispatcher("/updateequipment.jsp");
                rd.include(request, response);
            } catch (SQLException e) {
                e.printStackTrace();

                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<p class='fs-1 bg-danger text-center'>Unable to update. Please retry after reload.</p>");
                RequestDispatcher rd = request.getRequestDispatcher("/updateequipment.jsp");
                rd.include(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
