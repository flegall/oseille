package lobstre.osll.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.osll.Command;
import lobstre.osll.model.Account;
import lobstre.osll.parser.Parser;

public class ListOperation implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final Account account = Parser.read (file);
        
        lobstre.osll.commands.ListAccount.renderOperations (account);
    }

}
