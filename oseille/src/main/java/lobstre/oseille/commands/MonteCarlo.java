package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import lobstre.oseille.Command;
import lobstre.oseille.model.Account;
import lobstre.oseille.model.Operation;
import lobstre.oseille.parser.Parser;

public class MonteCarlo implements Command {

	private static final long MILLIS_IN_DAYS = 86400000L;

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, final String fileName) {
        if (arguments.size () != 1 && arguments.size () != 2) {
            errors.add ("Usage : monte-carlo oldest [date]");
        } else {
            if (arguments.size () == 2) {
                try {
                    DATE_FORMAT.parse (arguments.get (1));
                } catch (final ParseException e) {
                    e.printStackTrace (System.out);
                }
            }
        }
    }

	@Override
	public void execute(final String fileName, final List<String> arguments)
			throws IOException, ParseException {
		// List all history files
		final List<File> files = Arrays.asList(new File(".").listFiles());
		final NavigableSet<String> fileNames = new TreeSet<String>();
		for (final File file : files) {
			fileNames.add(file.getName());
		}
		final NavigableSet<String> subSet = fileNames.subSet(arguments.get(0),
				true, fileName, false);

		// Parse all history accounts
		final List<Account> historyAccounts = new ArrayList<Account>(subSet
				.size());
		for (final String hFileName : subSet) {
			final File hFile = new File(hFileName);
			historyAccounts.add(Parser.read(hFile));
		}

		// Enumerate all categories
		final Map<String, List<List<Op>>> universe = new TreeMap<String, List<List<Op>>>();
		for (final Account acc : historyAccounts) {
			for (final Operation op : acc.getOperations()) {
				universe.put(op.getCategory(), new ArrayList<List<Op>>());
			}
		}

		// Filling universe
		for (final Account acc : historyAccounts) {
			final String firstDateString = acc.getOperations().iterator()
					.next().getDate();
			final Date firstDate = DATE_FORMAT.parse(firstDateString);
			for (final Map.Entry<String, List<List<Op>>> e : universe
					.entrySet()) {
				final List<Op> list = new ArrayList<Op>();
				e.getValue().add(list);
				for (final Operation o : acc.getOperations()) {
					if (o.getCategory().equals(e.getKey())) {
						final String dateString = o.getDate();
						final Date date = DATE_FORMAT.parse(dateString);
						final int deltaDays = delta(firstDate, date);
						final Op op = new Op(o.getAmount().doubleValue(),
								deltaDays, o.getCategory());
						list.add(op);
					}
				}
				Collections.shuffle(list);
			}
		}

		final Account account = Parser.read(new File(fileName));
		final Operation firstOperation = account.getOperations().get(0);
		final Date firstDate = DATE_FORMAT.parse(firstOperation.getDate());
		final Date targetDate;
		if (arguments.size() == 2) {
			targetDate = DATE_FORMAT.parse(arguments.get(1));
		} else {
			final Operation lastOperation = account.getOperations().get(
					account.getOperations().size() - 1);
			targetDate = DATE_FORMAT.parse(lastOperation.getDate());
		}

		final int currentDate = delta(firstDate, targetDate);
		double currentBalance = account.getInitialAmount().doubleValue();
		for (final Operation o : account.getOperations()) {
			final Date opDate = DATE_FORMAT.parse(o.getDate());
			final int delta = delta(firstDate, opDate);
			if (delta <= currentDate) {
				currentBalance -= o.getAmount().doubleValue();
			}
		}
		
		System.out.println("Current date : " + currentDate);
		System.out.println("Current balance : " + round (currentBalance));
		System.out.println();

		final List<Estimation> estimations = new ArrayList<Estimation>();
		for (int i = 0; i < 1000; i++) {
			final List<Op> month = month(universe);
			double result = currentBalance;
			final Collection<Op> operations = new ArrayList<Op>();
			for (final Op op : month) {
				if (op.date > currentDate) {
					result -= op.amount;
					operations.add(op);
				}
			}
			estimations.add(new Estimation(operations, result));
		}

		Collections.sort(estimations, new Comparator<Estimation> () {
			@Override
			public int compare(final Estimation o1, final Estimation o2) {
				return (int) Math.signum (o1.result - o2.result);
			}
		});

		System.out.println("5% worst  : " + estimationString(0.05d, estimations));
		System.out.println("15% worst  : " + estimationString(0.15d, estimations));
		System.out.println("25% worst  : " + estimationString(0.25d, estimations));
		System.out.println("33% worst : " + estimationString(0.33333d, estimations));
		System.out.println("Mid       : " + estimationString(0.5d, estimations));
		System.out.println("33% best  : " + estimationString(0.66666d, estimations));
		System.out.println("25% best   : " + estimationString(0.75d, estimations));
	}

	public static double round(final double r) {
		return Math.round (r * 100.d) / 100.d;
	}

	public static int delta(final Date first, final Date second) {
		return (int) ((second.getTime() - first.getTime()) / MILLIS_IN_DAYS);
	}

	private String estimationString (final double index, final List<Estimation> valuesList) {
		final int size = valuesList.size();
		final double sizeIndex = index * size;
		final int i = sizeIndex >= 0 ? sizeIndex < size - 1 ? (int) sizeIndex
				: size - 1 : 0;
		final Estimation estimation = valuesList.get(i);
		
		final double result = round(estimation.result);
		final StringBuilder out = new StringBuilder (result + "\n");
		
		for (final Op o : estimation.operations) {
			out.append(o.toString());
			out.append ("\n");
		}
		
		return out.toString();
	}

	private List<Op> month(final Map<String, List<List<Op>>> universe) {
		final List<Op> operations = new ArrayList<Op>();
		for (final List<List<Op>> opsByCurrentCateg : universe.values()) {
			final int randomIndex = (int) (Math.random() * (opsByCurrentCateg
					.size()));
			final List<Op> list = opsByCurrentCateg.get(randomIndex);
			for (final Op op : list) {
				operations.add(op);
			}
		}
		Collections.sort(operations, DATE_COMPARATOR);
		return operations;
	}
	
	static class Estimation {
		public Estimation(final Collection<Op> operations, final double result) {
			this.result = result;
			this.operations = operations;
		}
		
		@Override
		public String toString() {
			return "Estimation [result=" + result + ", operations="
					+ operations + "]";
		}
		
		public final double result;
		public final Collection<Op> operations;
	}

	static class Op {
		public Op(final double amount, final int date, final String category) {
			this.amount = amount;
			this.date = date;
			this.category = category;
		}

		@Override
		public String toString() {
			return "Op [amount=" + amount + ", category=" + category
					+ ", date=" + date + "]";
		}

		public final double amount;
		public final int date;
		public final String category;
	}

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final Comparator<Op> DATE_COMPARATOR = new Comparator<Op>() {
		@Override
		public int compare(final Op o1, final Op o2) {
			return o1.date - o2.date;
		}
	};
}
