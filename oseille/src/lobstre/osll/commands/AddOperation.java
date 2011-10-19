package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.model.Operation;
import lobstre.osll.parser.Parser;
import lobstre.osll.util.Util;

public class AddOperation implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 4) {
            errors.add ("Usage : add-operation category label amount date");
        } else {
            Util.getBD (arguments.get (2));
        }
    }

    @Override
    public void execute (String fileName, final List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);
        
        final String category = arguments.get (0);
        final String label = arguments.get (1);
        final BigDecimal amount = Util.getBD (arguments.get (2));
        final String date = arguments.get (3);
        
        final Operation o = new Operation ();
        o.setAmount (amount);
        o.setCategory (category);
        o.setLabel (label);
        o.setDate (date);
        
        acc.getOperations ().add (o);
        
        ListAccount.renderOperations (acc);
        
        Parser.write (acc, file);        
    }

}
