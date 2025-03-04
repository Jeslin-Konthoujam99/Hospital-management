package adminPk;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/InExServlet")
public class InExServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        try 
        {
        	String sortBy = request.getParameter("sortBy");

            allbills billModel = new allbills();
        
            List<Bills> bills = billModel.getAllBillsFromDatabase(sortBy);
            double incomeAmount = billModel.getIncomeAmountFromDatabase();
            double expenditureAmount = billModel.getExpenditureAmountFromDatabase();
            
            List<String> billTypesForChart = new ArrayList<>();
            List<Double> amountsForChart = new ArrayList<>();
            
            for (Bills bill : bills) {
            	billTypesForChart.add(bill.getBillType());  
                amountsForChart.add(bill.getAmount());
            }
            
            
            HttpSession session = request.getSession();
            
            session.setAttribute("incomeAmount", incomeAmount);
            session.setAttribute("expenditureAmount", expenditureAmount);
            System.out.println("Setting bills attribute in session");
            session.setAttribute("bills", bills);
            System.out.println("Sorted Bills: " + bills);
            session.setAttribute("sortBy", sortBy);
            session.setAttribute("billTypesForChart", billTypesForChart);
            session.setAttribute("amountsForChart", amountsForChart);
            
            System.out.println("Forwarding to JSP");
            RequestDispatcher dispatcher = request.getRequestDispatcher("incomeAndexpence.jsp");
            
            dispatcher.forward(request, response);
            
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
}
