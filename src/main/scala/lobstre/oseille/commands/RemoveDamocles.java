package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class RemoveDamocles implements Command {

    @Override
    public void accepts(final List<String> arguments, final Collection<String> errors, final String fileName) {
        if (arguments.size() != 1) {
            errors.add("Usage : remove-damocles index");
        } else {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(arguments.get(0));
        }
    }

    @Override
    public void execute(final String fileName, final List<String> arguments) throws IOException {
        final File file = new File(fileName);
        final AccountBuilder acc = Parser.read(file);

        final int index = Integer.parseInt(arguments.get(0));

        acc.getDamocleses().remove(index);

        ListAccount.renderDamocles(acc);

        Parser.write(acc, file);
    }

}
