package lobstre.oseille.model;

import java.math.BigDecimal;

public class Damocles {
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
    private String category;
    private String label;
    private BigDecimal amount;
}
