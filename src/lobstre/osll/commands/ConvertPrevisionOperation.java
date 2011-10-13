package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.model.Operation;
import lobstre.osll.model.Prevision;
import lobstre.osll.parser.Parser;
import lobstre.osll.util.Util;

public class ConvertPrevisionOperation implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 2 && arguments.size () != 3) {
            errors.add ("Usage : convert-prevision-operation index date [amount]");
        } else {
            Integer.parseInt (arguments.get (0));
            if (arguments.size () == 3) {
                Util.getBD (arguments.get (2));
            }
        }
    }

    @Override
    public void execute (String fileName, final List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);

        final int index = Integer.parseInt (arguments.get (0));

        final Prevision p = acc.getPrevisions ().get (index);
        acc.getPrevisions ().remove (index);
        
        final Operation op = new Operation ();
        op.setAmount (arguments.size () == 3 ? Util.getBD (arguments.get (2)) : p.getAmount ());
        op.setCategory (p.getCategory ());
        op.setDate (arguments.get (1));
        op.setLabel (p.getLabel ());
        acc.getOperations ().add (op);
        
        ListAccount.renderOperations (acc);
        ListAccount.renderPrevisions (acc);

        Parser.write (acc, file);
    }
}
