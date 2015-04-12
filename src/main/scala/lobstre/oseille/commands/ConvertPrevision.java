package lobstre.oseille.commands;

import lobstre.oseille.Command;
import lobstre.oseille.model.MutableDamocles;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.model.MutablePrevision;
import lobstre.oseille.parser.Parser;
import lobstre.oseille.util.Util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class ConvertPrevision implements Command {

    @Override
    public void accepts (final List<String> arguments, final Collection<String> errors, String fileName) {
        if (arguments.size () != 1 && arguments.size () != 2) {
            errors.add ("Usage : convert-prevision index [amount]");
        } else {
            Integer.parseInt (arguments.get (0));
            if (arguments.size () == 2) {
                Util.getBD (arguments.get (1));
            }
        }
    }

    @Override
    public void execute (String fileName, final List<String> arguments) throws IOException {
        final File file = new File (fileName);
        final MutableAccount acc = Parser.read (file);

        final int index = Integer.parseInt (arguments.get (0));

        final MutablePrevision prevision = acc.getPrevisions ().get (index);
        acc.getPrevisions ().remove (index);
        
        final MutableDamocles d = new MutableDamocles ();
        d.setAmount (arguments.size () == 2 ? Util.getBD (arguments.get (1)) : prevision.getAmount ());
        d.setCategory (prevision.getCategory ());
        d.setLabel (prevision.getLabel ());
        acc.getDamocleses ().add (d);

        ListAccount.renderDamocles (acc);
        ListAccount.renderPrevisions (acc);

        Parser.write (acc, file);
    }
}
