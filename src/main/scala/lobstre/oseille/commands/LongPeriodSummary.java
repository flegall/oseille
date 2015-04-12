package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import lobstre.oseille.Command;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.model.MutableOperation;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.TableRenderer;
import lobstre.oseille.util.Util;

public class LongPeriodSummary implements Command {

	@Override
	public void accepts(List<String> arguments, Collection<String> errors,
			String fileName) {
		if (arguments.size() != 1) {
			errors.add("Usage : long-period-summary oldest");
		}
	}

	@Override
	public void execute(String fileName, List<String> arguments)
			throws IOException, ParseException {
		// List all history files
		final List<File> files = Arrays.asList(new File(".").listFiles());
		final NavigableSet<String> fileNames = new TreeSet<String>();
		for (final File file : files) {
			fileNames.add(file.getName());
		}
		final NavigableSet<String> subSet = fileNames.subSet(arguments.get(0),
				true, fileName, true);

		// Parse all history accounts
		final List<MutableAccount> historyAccounts = new ArrayList<MutableAccount>(
				subSet.size());
		for (final String hFileName : subSet) {
			final File hFile = new File(hFileName);
			historyAccounts.add(Parser.read(hFile));
		}

		// Fill all operations in a Map<String, List<Operation>>
		final Map<String, BigDecimal> totals = new TreeMap<String, BigDecimal>();
		for (final MutableAccount acc : historyAccounts) {
			for (final MutableOperation op : acc.getOperations()) {
				BigDecimal total = totals.get(op.getCategory());
				if (null == total) {
					total = new BigDecimal(0);
				}
				totals.put(op.getCategory(), total.add(op.getAmount()));
			}
		}

		final TableRenderer tr = new TableRenderer();
		tr.left("Category");
		tr.left("Opers");
		tr.newLine();

		for (Map.Entry<String, BigDecimal> e : totals.entrySet()) {
			tr.left(e.getKey());
			tr.right(Util.renderNumber(e.getValue()));
			tr.newLine();
		}

		System.out.println(tr.toString());
	}
}
