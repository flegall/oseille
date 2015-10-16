package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.model.DamoclesBuilder;
import lobstre.oseille.model.OperationBuilder;
import lobstre.oseille.model.PrevisionBuilder;
import lobstre.oseille.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class AdjustBudget implements Command {

    @Override
    public void accepts(List<String> arguments, Collection<String> errors, String fileName) {
        if (arguments.size() != 1) {
            errors.add("Usage : adjust-budget category");
        }
    }

    @Override
    public void execute(String fileName, List<String> arguments) throws IOException {
        final File file = new File(fileName);
        final AccountBuilder acc = Parser.read(file);

        final String category = arguments.get(0);

        adjustBudget(acc, category);

        Status.printStatus(acc);

        Parser.write(acc, file);
    }

    public static void adjustBudget(final AccountBuilder acc, final String category) {
        BigDecimal sumInCurrentCategory = BigDecimal.valueOf(0);

        for (final OperationBuilder o : acc.getOperations()) {
            if (o.getCategory() != null && category != null && o.getCategory().equals(category)) {
                sumInCurrentCategory = sumInCurrentCategory.add(o.getAmount());
            }
        }

        for (final DamoclesBuilder d : acc.getDamocleses()) {
            if (d.getCategory() != null && category != null && d.getCategory().equals(category)) {
                sumInCurrentCategory = sumInCurrentCategory.add(d.getAmount());
            }
        }

        for (final PrevisionBuilder p : acc.getPrevisions()) {
            if (p.getCategory() != null && category != null && p.getCategory().equals(category)) {
                sumInCurrentCategory = sumInCurrentCategory.add(p.getAmount());
            }
        }

        acc.getBudgets().put(category, sumInCurrentCategory);
    }

}
