package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.parser.Parser;
import lobstre.osll.util.Util;

public class Create implements Command {

    @Override
    public void execute (String fileName, final List<String> arguments) throws IOException {
        final Account acc = new Account ();
        
        acc.setInitialAmount (Util.getBD (arguments.get (0)));
        
        ListAccount.renderInitialAmount (acc);
        
        final File file = new File (fileName);
        Parser.write (acc, file);
    }

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 1) {
            errors.add ("Usage : create initial-amount");
        } else {
            Util.getBD (arguments.get (0));
        }
    }
}
