package lobstre.osll.model;

import java.math.BigDecimal;

public class Operation {
    public String getCategory () {
        return category;
    }
    public void setCategory (String category) {
        this.category = category;
    }
    public String getLabel () {
        return label;
    }
    public void setLabel (String label) {
        this.label = label;
    }
    public BigDecimal getAmount () {
        return amount;
    }
    public void setAmount (BigDecimal value) {
        this.amount = value;
    }
    public String getDate () {
        return date;
    }
    public void setDate (String date) {
        this.date = date;
    }
    private String category;
    private String label;
    private BigDecimal amount;
    private String date;
}
