package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

public class RebaseOperation implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, final String fileName) {
        acceptsCheck (arguments, errors, "rebase-operation");
    }

    public static void acceptsCheck (final List<String> arguments, final Collection<String> errors, final String commandName) {
        if (arguments.size () != 2) {
            errors.add ("Usage : " + commandName + " fromIndex toIndex");
        } else {
            final int from = Integer.parseInt (arguments.get (0));
            final int to = Integer.parseInt (arguments.get (1));
            if (from == to) {
                errors.add ("fromIndex must be different from to toIndex");
            }
        }
    }

    @Override
    public void execute (final String fileName, final List<String> arguments) throws IOException, ParseException {
        final File file = new File (fileName);
        final MutableAccount acc = Parser.read (file);
        
        RebaseOperation.rebase (acc.getOperations (), arguments);
        
        ListAccount.renderOperations (acc);
        Parser.write (acc, file);   
    }

    public static <T> void rebase (final List<T> opers, final List<String> arguments) {
        final int from = Integer.parseInt (arguments.get (0));
        final int to = Integer.parseInt (arguments.get (1));
    
        Util.rebase (opers, from, to);
    }

}
