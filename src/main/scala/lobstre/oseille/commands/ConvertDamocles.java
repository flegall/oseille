package lobstre.oseille.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import lobstre.oseille.Command;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.model.MutableDamocles;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.model.MutableOperation;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

public class ConvertDamocles implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 2 && arguments.size () != 3) {
            errors.add ("Usage : convert-damocles index date [amount]");
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
        final MutableAccount acc = Parser.read (file);

        final int index = Integer.parseInt (arguments.get (0));

        final MutableDamocles damocles = acc.getDamocleses ().get (index);
        acc.getDamocleses ().remove (index);
        
        final MutableOperation op = new MutableOperation ();
        op.setAmount (arguments.size () == 3 ? Util.getBD (arguments.get (2)) : damocles.getAmount ());
        op.setCategory (damocles.getCategory ());
        op.setDate (arguments.get (1));
        op.setLabel (damocles.getLabel ());
        acc.getOperations ().add (op);
        
        ListAccount.renderOperations (acc);
        ListAccount.renderDamocles (acc);

        Parser.write (acc, file);
    }
}
