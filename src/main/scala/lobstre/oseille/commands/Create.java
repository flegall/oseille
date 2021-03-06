package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Create implements Command {

    @Override
    public void execute(String fileName, final List<String> arguments) throws IOException {
        final AccountBuilder acc = new AccountBuilder();

        acc.setInitialAmount(Util.getBD(arguments.get(0)));

        ListAccount.renderInitialAmount(acc);

        final File file = new File(fileName);
        Parser.write(acc, file);
    }

    @Override
    public void accepts(final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size() != 1) {
            errors.add("Usage : create initial-amount");
        } else {
            Util.getBD(arguments.get(0));
        }
    }
}
