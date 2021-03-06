package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.model.OperationBuilder;
import lobstre.oseille.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

public class SetDateOperation implements Command {
    @Override
    public void accepts(final List<String> arguments, final Collection<String> errors, final String fileName) {
        if (arguments.size() != 2) {
            errors.add("Usage : set-date-operation index date");
        } else {
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(arguments.get(0));
        }
    }

    @Override
    public void execute(final String fileName, final List<String> arguments) throws IOException, ParseException {
        final int index = Integer.parseInt(arguments.get(0));
        final String date = arguments.get(1);

        final File file = new File(fileName);
        final AccountBuilder acc = Parser.read(file);

        OperationBuilder op = acc.getOperations().get(index);
        op.setDate(date);

        ListAccount.renderOperations(acc);

        Parser.write(acc, file);
    }
}
