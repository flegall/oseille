package lobstre.oseille.model;

import java.math.BigDecimal;

public class Budget {
    private String category;
    private BigDecimal amount;
    
    public String getCategory () {
        return category;
    }
    
    public void setCategory (String category) {
        this.category = category;
    }
    
    public BigDecimal getAmount () {
        return amount;
    }
    
    public void setAmount (BigDecimal amount) {
        this.amount = amount;
    }
}
