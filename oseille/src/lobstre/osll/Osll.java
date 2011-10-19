package lobstre.osll;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lobstre.osll.commands.AddDamocles;
import lobstre.osll.commands.AddOperation;
import lobstre.osll.commands.AddPrevision;
import lobstre.osll.commands.AdjustBudget;
import lobstre.osll.commands.Average;
import lobstre.osll.commands.ConvertDamocles;
import lobstre.osll.commands.ConvertPrevision;
import lobstre.osll.commands.ConvertPrevisionOperation;
import lobstre.osll.commands.Create;
import lobstre.osll.commands.ListBudget;
import lobstre.osll.commands.ListDamocles;
import lobstre.osll.commands.ListOperation;
import lobstre.osll.commands.ListPrevision;
import lobstre.osll.commands.MonteCarlo;
import lobstre.osll.commands.NextCommit;
import lobstre.osll.commands.NextSimulate;
import lobstre.osll.commands.RemoveDamocles;
import lobstre.osll.commands.RemoveOperation;
import lobstre.osll.commands.RemovePrevision;
import lobstre.osll.commands.SetBudget;
import lobstre.osll.commands.SetInitial;
import lobstre.osll.commands.Status;
import lobstre.osll.util.Util;

public class Osll {
    public static void main (final String[] args) throws IOException, ParseException {
        Locale.setDefault (Locale.US);
        
        if (args.length >= 2) {
            final String fileName = args[0];
            final String commandName = args[1];
            final Command c = COMMANDS.get (commandName);
            if (null != c) {
                final List<String> arguments = Arrays.asList (args).subList (2, args.length);
                final Collection<String> errors = new LinkedList<String> ();
                c.accepts (arguments, errors, fileName);
                if (errors.isEmpty ()) {
                    c.execute (fileName, arguments);
                } else {
                    for (final String err : errors) {
                        System.out.println ("Command : " + commandName + " : " + err);
                    }
                }
            } else {
                System.out.println ("Command : " + commandName+ " not found.");    
            }
        } else {
            System.out.println ("Usage : Osll fileName COMMAND");
        }
    }
    private static final Map<String, Command> COMMANDS = Util.asMap (new Object [][] {
            {"create", new Create ()},
            {"c", new Create ()},
            {"set-initial", new SetInitial ()},
            {"si", new SetInitial ()},
            {"set-budget", new SetBudget ()},
            {"sb", new SetBudget ()},
            {"list", new lobstre.osll.commands.ListAccount ()},
            {"l", new lobstre.osll.commands.ListAccount ()},
            {"list-budget", new ListBudget ()},
            {"lb", new ListBudget ()},
            {"list-damocles", new ListDamocles ()},
            {"ld", new ListDamocles ()},
            {"list-previsions", new ListPrevision ()},
            {"lp", new ListPrevision ()},
            {"list-operation", new ListOperation ()},
            {"lo", new ListOperation ()},        
            {"add-damocles", new AddDamocles ()},
            {"ad", new AddDamocles ()},
            {"add-prevision", new AddPrevision ()},
            {"ap", new AddPrevision ()},
            {"remove-damocles" , new RemoveDamocles ()},
            {"rd" , new RemoveDamocles ()},
            {"convert-damocles", new ConvertDamocles ()},
            {"cd", new ConvertDamocles ()},
            {"convert-prevision", new ConvertPrevision ()},
            {"cp", new ConvertPrevision ()},
            {"convert-prevision-operation", new ConvertPrevisionOperation ()},
            {"cpo", new ConvertPrevisionOperation ()},
            {"remove-operation", new RemoveOperation ()},
            {"ro", new RemoveOperation ()},
            {"remove-damocles", new RemoveDamocles ()},
            {"rd", new RemoveDamocles ()},
            {"remove-prevision", new RemovePrevision ()},
            {"rp", new RemovePrevision ()},
            {"remove-operation", new RemoveOperation ()},
            {"ro", new RemoveOperation ()},
            {"add-operation", new AddOperation ()},
            {"ao", new AddOperation ()},
            {"status", new Status ()},
            {"s", new Status ()},
            {"adjust-bugdget", new AdjustBudget ()},
            {"ab", new AdjustBudget ()},
            {"next-commit", new NextCommit ()},
            {"nc", new NextCommit ()},
            {"next-simulate", new NextSimulate ()},
            {"ns", new NextSimulate ()},
            {"monte-carlo", new MonteCarlo()},
            {"mc", new MonteCarlo()},
            {"average", new Average()},
            {"av", new Average()},
    });
}
