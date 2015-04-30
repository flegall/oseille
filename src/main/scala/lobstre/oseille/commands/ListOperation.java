package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.parser.Parser;

public class ListOperation implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final AccountBuilder account = Parser.read (file);
        
        lobstre.oseille.commands.ListAccount.renderOperations (account);
    }

}
