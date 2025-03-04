package adminPk;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.SQLConnect;
/**
 * Servlet implementation class inventory
 */
@WebServlet("/inventory")
public class inventory extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public inventory() {
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
		
		String itemname = request.getParameter("itemname");
		String departmentid = request.getParameter("departmentid");
		Integer quantity =Integer.parseInt(request.getParameter("quantity"));
		Integer price =Integer.parseInt (request.getParameter("price"));
		Integer lowStockqty =Integer.parseInt(request.getParameter("lowStockqty"));
		String date = request.getParameter("datetime");
		
		try
		{
			SQLConnect model = new SQLConnect();
			Connection con = model.connect();

			PreparedStatement pstmt=con.prepareStatement("INSERT INTO inventory  ( itemname ,departmentid,quantity,price,lowStockqty,date ) VALUES(?,?,?,?,?,?) ");
			
			
			
			pstmt.setString(1,itemname);
			pstmt.setString(2,departmentid);
			pstmt.setInt(3,quantity);
			pstmt.setInt(4,price);
			pstmt.setInt(5,lowStockqty);
			pstmt.setString(6,date);
			
			
		    pstmt.executeUpdate();
		    
			System.out.println("Item added successfully");
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<p class='fs-1 fc-success text-center' >Item added successfully </p>");
			RequestDispatcher rd = request.getRequestDispatcher("/inventory.html");
			rd.include(request, response);
		}
		catch(SQLException e)
		{
			System.out.println("Item added failed"+e);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.println("<p class='fs-1  bg-danger text-center' > Unable to add . please retry after relaod.</p>");
			RequestDispatcher rd = request.getRequestDispatcher("/inventory.html");
			rd.include(request, response);
		
		}
	
		
	
	}

}
