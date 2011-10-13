package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.model.Damocles;
import lobstre.osll.model.Operation;
import lobstre.osll.model.Prevision;
import lobstre.osll.parser.Parser;

public class AdjustBudget implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
        if (arguments.size () != 1 ) {
            errors.add ("Usage : adjust-budget category");
        }
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);
        
        final String category = arguments.get (0);
        
        adjustBudget (acc, category);
        
        Status.printStatus (acc);

        Parser.write (acc, file);
    }

    public static void adjustBudget (final Account acc, final String category) {
        BigDecimal sumInCurrentCategory = BigDecimal.valueOf (0);
        
        for (final Operation o : acc.getOperations ()) {
            if (o.getCategory () != null && category != null && o.getCategory ().equals (category)) {
                sumInCurrentCategory = sumInCurrentCategory.add (o.getAmount ());
            }
        }

        for (final Damocles d : acc.getDamocleses ()) {
            if (d.getCategory () != null && category != null && d.getCategory ().equals (category)) {
                sumInCurrentCategory = sumInCurrentCategory.add (d.getAmount ());
            }
        }
        
        for (final Prevision p : acc.getPrevisions ()) {
            if (p.getCategory () != null && category != null && p.getCategory ().equals (category)) {
                sumInCurrentCategory = sumInCurrentCategory.add (p.getAmount ());
            }
        }
        
        acc.getBudgets ().put (category, sumInCurrentCategory);
    }

}
