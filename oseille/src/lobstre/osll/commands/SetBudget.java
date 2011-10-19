package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.parser.Parser;
import lobstre.osll.util.Util;

public class SetBudget implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
        if (arguments.size () == 2) {
            Util.getBD (arguments.get (1));
        } else {
            errors.add ("Usage : set-budget category budget");
        }
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);
        final String category = arguments.get (0);
        final BigDecimal amount = Util.getBD (arguments.get (1));
        acc.getBudgets ().put (category, amount);
        
        Status.printStatus (acc);
        
        Parser.write (acc, file);
    }

}
