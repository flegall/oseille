package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ListPrevision implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final MutableAccount account = Parser.read (file);
        
        lobstre.oseille.commands.ListAccount.renderPrevisions (account);
    }

}
