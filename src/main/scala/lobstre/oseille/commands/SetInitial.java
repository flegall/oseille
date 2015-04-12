package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

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
        final MutableAccount acc = Parser.read (file);
        acc.setInitialAmount (Util.getBD (arguments.get (0)));
        Parser.write (acc, file);
    }

}
