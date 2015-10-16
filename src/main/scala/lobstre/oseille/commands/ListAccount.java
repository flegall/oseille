package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.model.DamoclesBuilder;
import lobstre.oseille.model.OperationBuilder;
import lobstre.oseille.model.PrevisionBuilder;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.TableRenderer;
import lobstre.oseille.util.Util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class ListAccount implements Command {

    @Override
    public void accepts(final java.util.List<String> arguments, final Collection<String> errors, String fileName) {
    }

    @Override
    public void execute(String fileName, final java.util.List<String> arguments) throws IOException {
        final File file = new File(fileName);
        final AccountBuilder account = Parser.read(file);

        listAccount(account);
    }

    public static void listAccount(final AccountBuilder account) {
        renderInitialAmount(account);
        renderBudgets(account);
        renderPrevisions(account);
        renderDamocles(account);
        renderOperations(account);
    }

    public static void renderOperations(final AccountBuilder account) {
        account.sortOperations();
        System.out.println("Operations: ");

        int count = 0;
        final TableRenderer tr = new TableRenderer();
        for (final OperationBuilder o : account.getOperations()) {
            tr.right(Integer.toString(count));
            tr.left(o.getCategory());
            tr.left(o.getLabel());
            tr.right(Util.renderNumber(o.getAmount()));
            tr.right(o.getDate());
            tr.newLine();
            count++;
        }
        System.out.print(tr.toString());
    }

    public static void renderInitialAmount(final AccountBuilder account) {
        System.out.println("Initial amount: " + Util.renderNumber(account.getInitialAmount()));
        System.out.println();
    }

    public static void renderPrevisions(final AccountBuilder account) {
        System.out.println("Previsions: ");

        int count = 0;
        final TableRenderer tr = new TableRenderer();
        for (final PrevisionBuilder p : account.getPrevisions()) {
            tr.left(Integer.toString(count));
            tr.left(p.getCategory());
            tr.left(p.getLabel());
            tr.right(Util.renderNumber(p.getAmount()));
            tr.newLine();
            count++;
        }
        System.out.print(tr.toString());
    }

    public static void renderDamocles(final AccountBuilder account) {
        System.out.println("Damocles: ");

        int count = 0;
        final TableRenderer tr = new TableRenderer();
        for (final DamoclesBuilder d : account.getDamocleses()) {
            tr.left(Integer.toString(count));
            tr.left(d.getCategory());
            tr.left(d.getLabel());
            tr.right(Util.renderNumber(d.getAmount()));
            tr.newLine();
            count++;
        }
        System.out.print(tr.toString());
    }

    public static void renderBudgets(final AccountBuilder account) {
        System.out.println("Budgets: ");

        final TableRenderer tr = new TableRenderer();
        for (final Map.Entry<String, BigDecimal> e : account.getBudgets().entrySet()) {
            tr.left(e.getKey());
            tr.right(Util.renderNumber(e.getValue()));
            tr.newLine();
        }
        System.out.print(tr.toString());
    }

}
