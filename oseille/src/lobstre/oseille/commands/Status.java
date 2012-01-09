package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lobstre.oseille.Command;
import lobstre.oseille.model.Account;
import lobstre.oseille.model.Damocles;
import lobstre.oseille.model.Operation;
import lobstre.oseille.model.Prevision;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.TableRenderer;
import lobstre.oseille.util.Util;

public class Status implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
    }

    @Override
    public void execute (final String fileName, final List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account account = Parser.read (file);

        printStatus (account);
    }

    public static void printStatus (final Account account) {
        BigDecimal endBudget = account.getInitialAmount ();
        BigDecimal endPrevision = account.getInitialAmount ();
        BigDecimal currently = account.getInitialAmount ();
        BigDecimal soon = account.getInitialAmount ();

        final TableRenderer tr = new TableRenderer ();
        tr.left ("Category");
        tr.left ("Opers");
        tr.left ("+Dams");
        tr.left ("+Prevs");
        tr.left ("Budget");
        tr.left ("Warning");
        tr.left ("Category");
        tr.newLine ();
        for (final Map.Entry<String, BigDecimal> e : account.getBudgets ().entrySet ()) {
            final String key = e.getKey ();
            tr.left (key);
            BigDecimal sumInCurrentCategory = BigDecimal.valueOf (0L);

            for (final Operation o : account.getOperations ()) {
                if (o.getCategory () != null && key != null && o.getCategory ().equals (key)) {
                    sumInCurrentCategory = sumInCurrentCategory.add (o.getAmount ());
                }
            }
            currently = currently.subtract (sumInCurrentCategory);

            tr.right (Util.renderNumber (sumInCurrentCategory));

            for (final Damocles d : account.getDamocleses ()) {
                if (d.getCategory () != null && key != null && d.getCategory ().equals (key)) {
                    sumInCurrentCategory = sumInCurrentCategory.add (d.getAmount ());
                }
            }
            tr.right (Util.renderNumber (sumInCurrentCategory));

            soon = soon.subtract (sumInCurrentCategory);

            for (final Prevision o : account.getPrevisions ()) {
                if (o.getCategory () != null && key != null && o.getCategory ().equals (key)) {
                    sumInCurrentCategory = sumInCurrentCategory.add (o.getAmount ());
                }
            }

            tr.right (Util.renderNumber (sumInCurrentCategory));

            endPrevision = endPrevision.subtract (sumInCurrentCategory);
            endBudget = endBudget.subtract (e.getValue ());

            tr.right (Util.renderNumber (e.getValue ()));
            
            if (sumInCurrentCategory.compareTo (e.getValue ()) > 0) {
                tr.left ("!!!!");
            } else {
                tr.left ("");
            }
            
            tr.left (key);

            tr.newLine ();
        }

        System.out.print (tr.toString ());
        
        final TableRenderer summary = new TableRenderer ();
        summary.left ("Initially").right (Util.renderNumber (account.getInitialAmount ())).newLine ();
        summary.left ("Currently").right (Util.renderNumber (currently)).newLine ();
        summary.left ("Soon").right (Util.renderNumber (soon)).newLine ();
        summary.left ("End-Previsions").right (Util.renderNumber (endPrevision)).newLine ();
        summary.left ("End-Budget").right (Util.renderNumber (endBudget)).newLine ();
        
        System.out.print (summary.toString ());
    }

}
