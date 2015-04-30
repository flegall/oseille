package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.model.OperationBuilder;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.TableRenderer;
import lobstre.oseille.util.Util;

public class Average implements Command {

	private static final int MAX_DAYS_IN_MONTH = 31;

	@Override
	public void accepts(final List<String> arguments,
			final Collection<String> errors, final String fileName) {
		if (arguments.size() != 1 && arguments.size() != 2) {
			errors.add("Usage : average oldest");
		}
	}

    @Override
    public void execute (final String fileName, final List<String> arguments) throws IOException, ParseException {
        // List all history files
        @SuppressWarnings("ConstantConditions")
        final List<File> allFiles = Arrays.asList (new File (".").listFiles ());
        final NavigableSet<String> allFileNames = new TreeSet<>();
        for (final File file : allFiles) {
            allFileNames.add (file.getName ());
        }
        final NavigableSet<String> historyFileNames = allFileNames.subSet (arguments.get (0), true, fileName, false);

        // Parse all history accounts
        final List<AccountBuilder> historyAccounts = new ArrayList<>(historyFileNames.size());
        for (final String hFileName : historyFileNames) {
            final File hFile = new File (hFileName);
            historyAccounts.add (Parser.read (hFile));
        }

        final NavigableMap<Integer, Double> totalSums = new TreeMap<>();
        for (final AccountBuilder acc : historyAccounts) {
            final NavigableMap<Integer, Double> sums = new TreeMap<>();
            Double initValue = sums.get (-1);
            if (null == initValue) {
                initValue = 0.0;
            }
            sums.put (-1, initValue + acc.getInitialAmount ().doubleValue ());

            final String firstDateString = acc.getOperations ().iterator ().next ().getDate ();
            final Date firstDate = MonteCarlo.DATE_FORMAT.parse (firstDateString);

            BigDecimal currentValue = acc.getInitialAmount ();
            String currentDate = acc.getOperations ().iterator ().next ().getDate ();
            for (final OperationBuilder op : acc.getOperations ()) {
                final String dateString = op.getDate ();
                if (!currentDate.equals (dateString)) {
                    final Date date = MonteCarlo.DATE_FORMAT.parse (currentDate);
                    final int deltaDays = MonteCarlo.delta (firstDate, date);
                    Double val = sums.get (deltaDays);
                    if (null == val) {
                        val = 0.0;
                    }
                    sums.put (deltaDays, val + currentValue.doubleValue ());
                }
                currentValue = currentValue.subtract (op.getAmount ());
                currentDate = op.getDate ();
            }

            double current = sums.get (sums.firstKey ());
            for (int i = sums.firstKey () + 1; i <= MAX_DAYS_IN_MONTH; i++) {
                if (!sums.containsKey (i)) {
                    sums.put (i, current);
                }
                current = sums.get (i);
            }

            for (final int i : sums.keySet ()) {
                Double sum = totalSums.get (i);
                if (null == sum) {
                    sum = 0.0;
                }
                sum = sum + sums.get (i);
                totalSums.put (i, sum);
            }
        }

        for (final int i : totalSums.keySet ()) {
            Double sum = totalSums.get (i);
            sum /= historyAccounts.size ();
            sum = MonteCarlo.round (sum);
            totalSums.put (i, sum);
        }

        final AccountBuilder account = Parser.read (new File (fileName));
        final OperationBuilder firstOperation = account.getOperations ().get (0);
        final Date firstDate = MonteCarlo.DATE_FORMAT.parse (firstOperation.getDate ());
        final OperationBuilder lastOperation = account.getOperations ().get (account.getOperations ().size () - 1);
        final Date targetDate = MonteCarlo.DATE_FORMAT.parse (lastOperation.getDate ());
        final int currentDate = MonteCarlo.delta (firstDate, targetDate);
        
        BigDecimal currentValue = account.getInitialAmount ();
        final NavigableMap<Integer, BigDecimal> currentValues = new TreeMap<>();
        for (final OperationBuilder op : account.getOperations ()) {
            final Date curDate = MonteCarlo.DATE_FORMAT.parse (op.getDate ());
            final int delta = MonteCarlo.delta (firstDate, curDate);
            currentValue = currentValue.subtract (op.getAmount ());
            currentValues.put (delta, currentValue);
        }
        
        BigDecimal last = null;
        for (int i = currentValues.firstKey (); i <= currentValues.lastKey (); i++) {
            final BigDecimal v = currentValues.get (i);
            if (null != v) {
                last = v;
            } else {
                currentValues.put (i, last);
            }
        }
        
        final TableRenderer tr = new TableRenderer ();
        tr.left ("Date");
        tr.left ("Expected");
        tr.left ("Current");
        tr.newLine ();
        for (final int i : totalSums.keySet ()) {
            final Double sum = totalSums.get (i);
            tr.right (Integer.valueOf (i).toString ());
            tr.right (Util.renderNumber (sum));
            final BigDecimal current = currentValues.get (i);
            if (null != current) {
                tr.right (Util.renderNumber (current));
            } else if (i == -1) {
                tr.right (Util.renderNumber (account.getInitialAmount ()));
            }
            tr.newLine ();
        }
        System.out.println (tr.toString ());

        {
            final TableRenderer trs = new TableRenderer ();
            trs.left ("Current date");
            trs.left (Integer.valueOf (currentDate).toString ());
            trs.newLine ();

            trs.left ("Balance");
            trs.right (Util.renderNumber (currentValue));
            trs.newLine ();

            trs.left ("Expected");
            Double value = totalSums.get (currentDate);
            if (null == value) {
                value = totalSums.lowerEntry (currentDate).getValue ();
            }
            trs.right (Util.renderNumber (value));
            trs.newLine ();
            System.out.println (trs);
        }

    }

}
