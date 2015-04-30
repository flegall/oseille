package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.AccountBuilder;
import lobstre.oseille.model.DamoclesBuilder;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

public class AddDamocles implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 3) {
            errors.add ("Usage : add-damocles category label amount");
        } else {
            Util.getBD (arguments.get (2));
        }
    }

    @Override
    public void execute (String fileName, final List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final AccountBuilder acc = Parser.read (file);
        
        final String category = arguments.get (0);
        final String label = arguments.get (1);
        final BigDecimal amount = Util.getBD (arguments.get (2));
        
        final DamoclesBuilder d = new DamoclesBuilder();
        d.setAmount (amount);
        d.setCategory (category);
        d.setLabel (label);
        
        acc.getDamocleses ().add (d);
        
        ListAccount.renderDamocles (acc);
        
        Parser.write (acc, file);        
    }

}
