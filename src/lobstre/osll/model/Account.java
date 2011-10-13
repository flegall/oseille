package lobstre.osll.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import lobstre.osll.util.StringComparator;

public class Account {
    public void sortOperations () {
        final StringComparator comparator = new StringComparator ();
        Collections.sort (operations, new Comparator<Operation> () {
            @Override
            public int compare (final Operation o1, final Operation o2) {
                return comparator.compare (o1.getDate (), o2.getDate ());
            }
        });
    }

    public BigDecimal getInitialAmount () {
        return initialAmount;
    }

    public void setInitialAmount (final BigDecimal initialAmout) {
        this.initialAmount = initialAmout;
    }
    
    public NavigableMap<String, BigDecimal> getBudgets () {
        return budgets;
    }

    public List<Prevision> getPrevisions () {
        return previsions;
    }

    public List<Damocles> getDamocleses () {
        return damocleses;
    }

    public List<Operation> getOperations () {
        return operations;
    }

    private BigDecimal initialAmount;
    private final NavigableMap<String, BigDecimal> budgets = new TreeMap <String, BigDecimal> ();
    private final List<Prevision> previsions = new ArrayList <Prevision> ();
    private final List<Damocles> damocleses = new ArrayList <Damocles> ();
    private final List<Operation> operations = new ArrayList<Operation> ();
}
