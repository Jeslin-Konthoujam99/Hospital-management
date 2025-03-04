package adminPk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class allbills 
{
    private String url = "jdbc:mysql://localhost:3306/hms?useSSL=false";
    private String USER = "root";
    private String PASS = "root";
    private Connection con = null;

  /*  public List<Bills> getAllBillsFromDatabase() 
    {
        List<Bills> bills = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            con = DriverManager.getConnection(url, USER, PASS);
            System.out.println("Connection is established successfully");
            
            Statement statement = con.createStatement();

            String[] sqlStatements = {
                "select * from hms.all_bills",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(consultation_fee + test_charge + prescription_charge), 0) FROM opd_bills) WHERE bill_type = 'OPD'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(room_charge + medicine_charge + other_charges), 0) FROM ipd_bills) WHERE bill_type = 'IPD'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(salary), 0) FROM staff_salary) WHERE bill_type = 'Staff Salary'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(unit_price), 0) FROM inventory_and_equipment_bills) WHERE bill_type = 'Inventory'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(amount), 0) FROM extra_bills) WHERE bill_type = 'Extra'"
            };

            try {
                for (String sql : sqlStatements) {
                    statement.execute(sql);
                }
            } catch (SQLException e) {
                System.err.println("Error during SQL execution:");
                e.printStackTrace();
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.err.println("Error while closing statement:");
                    e.printStackTrace();
                }
            }


            try (PreparedStatement preparedStatement = con.prepareStatement("select * from hms.all_bills;");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) 
                {
                    Bills bill = new Bills(
                            resultSet.getInt("bill_id"),
                            resultSet.getString("bill_type"),
                            resultSet.getString("description"),
                            resultSet.getDouble("amount"),
                            resultSet.getDate("bill_date").toLocalDate(),
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getInt("isIncome")
                    );
                    bills.add(bill);
                }
            }
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            System.err.println("Error during database access:");
            e.printStackTrace(); 
        } 
        finally 
        {
            try {
                if (con != null) 
                {
                    con.close();
                }
            } 
            catch (SQLException e) 
            {
                System.err.println("Error while closing connection:");
                e.printStackTrace();
            }
        }

        return bills;
        
        
    }*/
    
    public List<Bills> getAllBillsFromDatabase(String sortBy)
    {
    	List<Bills> bills = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");

            con = DriverManager.getConnection(url, USER, PASS);
            System.out.println("Connection is established successfully");
            
            Statement statement = con.createStatement();

            String[] sqlStatements = {
                "select * from hms.all_bills",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(amount), 1) FROM opd_bill) WHERE bill_type = 'OPD'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(amount), 1) FROM ipd_bill) WHERE bill_type = 'IPD'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(test_cost), 1) FROM ipd_test_bill) WHERE bill_type = 'IPD Test Bill'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(Amount), 1) FROM testbill) WHERE bill_type = 'Test Bill'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(salary), 0) FROM employee_salary) WHERE bill_type = 'Employee Salary'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(Amount), 0) FROM equipmentbill) WHERE bill_type = 'Equipment'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(Amount), 0) FROM inventorybill) WHERE bill_type = 'Inventory'",
                "UPDATE all_bills SET amount = (SELECT COALESCE(SUM(amount), 0) FROM extra_bills) WHERE bill_type = 'Extra'"
            };

            try {
                for (String sql : sqlStatements) {
                    statement.execute(sql);
                }
            } catch (SQLException e) {
                System.err.println("Error during SQL execution:");
                e.printStackTrace();
            } finally {
                try {
                    if (statement != null) {
                        statement.close();
                    }
                } catch (SQLException e) {
                    System.err.println("Error while closing statement:");
                    e.printStackTrace();
                }
            }


            try (PreparedStatement preparedStatement = con.prepareStatement("select * from hms.all_bills;");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) 
                {
                    Bills bill = new Bills(
                            resultSet.getInt("bill_id"),
                            resultSet.getString("bill_type"),
                            resultSet.getString("description"),
                            resultSet.getDouble("amount"),               
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getInt("isIncome")
                    );
                    bills.add(bill);
                }
            }
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            System.err.println("Error during database access:");
            e.printStackTrace(); 
        } 
        finally 
        {
            try {
                if (con != null) 
                {
                    con.close();
                }
            } 
            catch (SQLException e) 
            {
                System.err.println("Error while closing connection:");
                e.printStackTrace();
            }
        }
        
        if (sortBy == null) {
            sortBy = "defaultColumn"; 
        }
        
        System.out.println("sortBy: " + sortBy);
        
        System.out.println("Before switch");
        switch (sortBy) {
        case "defaultColumn":
            System.out.println("Default sorting");          
            Collections.sort(bills, Comparator.comparingInt(Bills::getBillId));
            break;
        case "bill_type":
            System.out.println("Sorting by bill type");
            Collections.sort(bills, Comparator.comparing(Bills::getBillType));
            break;
        case "amount":
            System.out.println("Sorting by amount");
            Collections.sort(bills, Comparator.comparingDouble(Bills::getAmount));
            break;
        case "date":
            Collections.sort(bills, Comparator.comparing(Bills::getCreatedAt));
            break;
        default:
            System.out.println("Default sorting");           
            Collections.sort(bills, Comparator.comparingInt(Bills::getBillId));
            break;
    }

        System.out.println("after switch");
        return bills;
        	
    }
    
    public double getIncomeAmountFromDatabase() {
        double incomeAmount = 0.0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?useSSL=false", "root", "root");
             PreparedStatement preparedStatement = con.prepareStatement("SELECT SUM(amount) FROM hms.all_bills WHERE isIncome = 1");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                incomeAmount = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            System.err.println("Error during database access:");
            e.printStackTrace();
        }

        return incomeAmount;
    }

    public double getExpenditureAmountFromDatabase() {
        double expenditureAmount = 0.0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms?useSSL=false", "root", "root");
             PreparedStatement preparedStatement = con.prepareStatement("SELECT SUM(amount) FROM hms.all_bills WHERE isIncome != 1");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                expenditureAmount = resultSet.getDouble(1);
            }

        } catch (SQLException e) {
            System.err.println("Error during database access:");
            e.printStackTrace();
        }

        return expenditureAmount;
    }
}
