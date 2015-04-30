package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

public class SetOperation implements Command {

    @Override
    public void accepts (List<String> arguments, Collection<String> errors, String fileName) {
        if (arguments.size () != 2) {
            errors.add ("Usage : set-operation index amount");
        } else {
            Util.getBD (arguments.get (1));
        }
    }

    @Override
    public void execute (String fileName, List<String> arguments) throws IOException, ParseException {
        final int index = Integer.parseInt (arguments.get (0));
        final BigDecimal amount = Util.getBD (arguments.get (1));
        
        final File file = new File (fileName);
        final AccountBuilder acc = Parser.read (file);
        acc.getOperations ().get (index).setAmount (amount);
        Parser.write (acc, file);
        
        ListAccount.renderOperations (acc);
    }

}
