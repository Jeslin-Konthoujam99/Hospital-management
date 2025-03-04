package adminPk;

import java.time.LocalDateTime;

public class Bills {
    private int billId;
    private String billType;
    private String description;
    private double amount;
    private LocalDateTime createdAt;
    private int isIncome;
    
  
    public Bills(int billId, String billType,
                String description, double amount, LocalDateTime createdAt, int isIncome) 
    {
        this.billId = billId;
        this.billType = billType;
        this.description = description;
        this.amount = amount;
       
        this.createdAt = createdAt;
        this.isIncome = isIncome;
    }
    
    


	public int getBillId() {
        return billId;
    }

    public String getBillType() {
        return billType;
    }


    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

   

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getIsIncome() {
        return isIncome;
    }
}
