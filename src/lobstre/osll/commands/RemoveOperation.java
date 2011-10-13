package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.parser.Parser;

public class RemoveOperation implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 1) {
            errors.add ("Usage : remove-operation index");
        } else {
            Integer.parseInt (arguments.get (0));
        }
    }

    @Override
    public void execute (String fileName, final List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);
        
        final int index = Integer.parseInt (arguments.get (0));
        
        acc.getOperations ().remove (index);
        
        ListAccount.renderOperations (acc);
        
        Parser.write (acc, file);        
    }

}