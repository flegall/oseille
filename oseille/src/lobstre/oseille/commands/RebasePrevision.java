package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.Account;
import lobstre.oseille.parser.Parser;

public class RebasePrevision implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, final String fileName) {
        RebaseOperation.acceptsCheck (arguments, errors, "rebase-prevision");
    }

    @Override
    public void execute (final String fileName, final List<String> arguments) throws IOException, ParseException {
        final File file = new File (fileName);
        final Account acc = Parser.read (file);
        
        RebaseOperation.rebase (acc.getPrevisions (), arguments);
        
        ListAccount.renderPrevisions (acc);
        Parser.write (acc, file);   
    }

}