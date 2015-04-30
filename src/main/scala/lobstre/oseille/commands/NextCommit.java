package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.model.DamoclesBuilder;
import lobstre.oseille.model.OperationBuilder;
import lobstre.oseille.model.PrevisionBuilder;
import lobstre.oseille.parser.Parser;

public class NextCommit implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, final String fileName) {
        accepts (arguments, errors, fileName, "next-commit");
    }

    public static void accepts (final List<String> arguments, final Collection<String> errors, final String fileName, final String commandName) {
        if (arguments.size () != 2 && arguments.size () != 3) {
            errors.add ("Usage : " + commandName + " oldest-file current-file [template-file]");
        } else {
            final Set<String> args = new HashSet<>();
            args.addAll (arguments);
            args.add (fileName);
            if (args.size () != arguments.size () + 1) {
                errors.add ("All files must be different, even the target file");    
            }
            if (new File (fileName).exists ()) {
                errors.add ("Target file must not exist");
            }
        }
    }

    @Override
    public void execute (final String fileName, final List<String> arguments) throws IOException {
        next (fileName, arguments, true);
    }

    public static void next (final String fileName, final List<String> arguments, final boolean commit) throws IOException {
        final List<PrevisionBuilder> templatePrevisions;
        final List<DamoclesBuilder> templateDamocleses;
        if (arguments.size () == 3) {
            // If has template file : Add template previsions & damocles
            final File templateFile = new File (arguments.get (2));
            final AccountBuilder templateAcc = Parser.read (templateFile);
            templatePrevisions = templateAcc.getPrevisions ();
            templateDamocleses = templateAcc.getDamocleses ();
        } else {
            // Else empty values
            templatePrevisions = Collections.emptyList ();
            templateDamocleses = Collections.emptyList ();
        }

        // Loads the current file
        final File currentFile = new File (arguments.get (1));
        final AccountBuilder current = Parser.read (currentFile);

        // Compute the remaining amount
        BigDecimal remainingAmount = current.getInitialAmount ();
        for (final OperationBuilder op : current.getOperations ()) {
            remainingAmount = remainingAmount.subtract (op.getAmount ());
        }

        // Create target account with remaining amount
        final AccountBuilder targetAccount = new AccountBuilder();
        targetAccount.setInitialAmount (remainingAmount);

        // Move all previsions & damocleses from current to target
        targetAccount.getPrevisions ().addAll (current.getPrevisions ());
        targetAccount.getDamocleses ().addAll (current.getDamocleses ());
        current.getPrevisions ().clear ();
        current.getDamocleses ().clear ();

        // Adjust budget on all categories on current
        for (final String cat : current.getBudgets ().keySet ()) {
            AdjustBudget.adjustBudget (current, cat);
        }

        // Add all template previsions & damocles
        targetAccount.getPrevisions ().addAll (templatePrevisions);
        targetAccount.getDamocleses ().addAll (templateDamocleses);

        // List all history files
        @SuppressWarnings("ConstantConditions")
        final List<File> files = Arrays.asList (new File (".").listFiles ());
        final NavigableSet<String> fileNames = new TreeSet<>();
        for (final File file : files) {
            fileNames.add (file.getName ());
        }
        final NavigableSet<String> subSet = fileNames.subSet (arguments.get (0), true, arguments.get (1), false);

        // Parse all history accounts
        final List<AccountBuilder> historyAccounts = new ArrayList<>(subSet.size() + 1);
        for (final String hFileName : subSet) {
            final File hFile = new File (hFileName);
            historyAccounts.add (Parser.read (hFile));
        }
        historyAccounts.add (current);

        // Compute average per files
        final Map<String, List<BigDecimal>> totalsAverage = new TreeMap<>();
        for (final AccountBuilder acc : historyAccounts) {
            final Map<String, BigDecimal> totals = new TreeMap<>();
            for (final OperationBuilder op : acc.getOperations ()) {
                BigDecimal total = totals.get (op.getCategory ());
                if (null == total) {
                    total = BigDecimal.valueOf (0);
                }
                total = total.add (op.getAmount ());
                totals.put (op.getCategory (), total);
            }
            for (final Entry<String, BigDecimal> e : totals.entrySet ()) {
                List<BigDecimal> list = totalsAverage.get (e.getKey ());
                if (null == list) {
                    list = new ArrayList<>();
                    totalsAverage.put (e.getKey (), list);
                }
                list.add (e.getValue ());
            }
        }

        // Compute averages and set those to target account
        for (final Entry<String, List<BigDecimal>> e : totalsAverage.entrySet ()) {
            final int valuesCount = e.getValue ().size ();
            for (int i = valuesCount; i < historyAccounts.size (); i++) {
                e.getValue ().add (BigDecimal.valueOf (0));
            }
            BigDecimal total = BigDecimal.valueOf (0);
            for (final BigDecimal value : e.getValue ()) {
                total = total.add (value);
            }
            final BigDecimal average = total.divideToIntegralValue (BigDecimal.valueOf (e.getValue ().size ()));
            targetAccount.getBudgets ().put (e.getKey (), average);
        }

        // List current
        System.out.println ("Current : ");
        ListAccount.listAccount (current);
        Status.printStatus (current);
        System.out.println ();
        System.out.println ();

        // List target
        System.out.println ("Target : ");
        ListAccount.listAccount (targetAccount);
        Status.printStatus (targetAccount);
        
        if (commit) {
            // Export target & current
            final File targetFile = new File (fileName);
            Parser.write (targetAccount, targetFile);
            Parser.write (current, currentFile);
        }
    }
}
