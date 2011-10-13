package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.parser.Parser;
import lobstre.osll.util.Util;

public class SetInitial implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
        if (arguments.size () != 1) {
            errors.add ("Usage : set-initial initial-amount");
        } else {
            Util.getBD (arguments.get (0));
        }
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);
        acc.setInitialAmount (Util.getBD (arguments.get (0)));
        Parser.write (acc, file);
    }

}
