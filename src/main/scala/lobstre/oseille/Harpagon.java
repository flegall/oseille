package lobstre.oseille;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lobstre.oseille.commands.AddDamocles;
import lobstre.oseille.commands.AddOperation;
import lobstre.oseille.commands.AddPrevision;
import lobstre.oseille.commands.AdjustBudget;
import lobstre.oseille.commands.Average;
import lobstre.oseille.commands.ConvertDamocles;
import lobstre.oseille.commands.ConvertPrevision;
import lobstre.oseille.commands.ConvertPrevisionOperation;
import lobstre.oseille.commands.Create;
import lobstre.oseille.commands.ListBudget;
import lobstre.oseille.commands.ListDamocles;
import lobstre.oseille.commands.ListOperation;
import lobstre.oseille.commands.ListPrevision;
import lobstre.oseille.commands.LongPeriodSummary;
import lobstre.oseille.commands.MonteCarlo;
import lobstre.oseille.commands.NextCommit;
import lobstre.oseille.commands.NextSimulate;
import lobstre.oseille.commands.RebaseDamocles;
import lobstre.oseille.commands.RebaseOperation;
import lobstre.oseille.commands.RebasePrevision;
import lobstre.oseille.commands.RemoveDamocles;
import lobstre.oseille.commands.RemoveOperation;
import lobstre.oseille.commands.RemovePrevision;
import lobstre.oseille.commands.SetBudget;
import lobstre.oseille.commands.SetDamocles;
import lobstre.oseille.commands.SetDateOperation;
import lobstre.oseille.commands.SetInitial;
import lobstre.oseille.commands.SetOperation;
import lobstre.oseille.commands.SetPrevision;
import lobstre.oseille.commands.Status;
import lobstre.oseille.util.TableRenderer;
import lobstre.oseille.util.Util;

public class Harpagon {
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
                printCommands ();
            }
        } else {
            System.out.println ("Usage : Harpagon fileName COMMAND");
            printCommands ();
        }
    }
    
    private static void printCommands () {
        System.out.println ();
        System.out.println ("Available commands: ");
        
        TableRenderer tr = new TableRenderer ();
        int count = 0;
        tr.left ("Command");
        tr.left ("Shortcut");
        tr.newLine ();
        for (String cmd : COMMANDS.keySet ()) {
            tr.left (cmd);
            count++;
            if (count % 2 == 0) {
                tr.newLine ();
            }
        }
        System.out.println (tr.toString ());
    }
    
    private static final Map<String, Command> COMMANDS = Util.asMap (new Object [][] {
            {"create", new Create ()},
            {"c", new Create ()},
            {"set-initial", new SetInitial ()},
            {"si", new SetInitial ()},
            {"set-budget", new SetBudget ()},
            {"sb", new SetBudget ()},
            {"list", new lobstre.oseille.commands.ListAccount ()},
            {"l", new lobstre.oseille.commands.ListAccount ()},
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
			{"monte-carlo", new MonteCarlo() },
			{"mc", new MonteCarlo() }, 
			{"average", new Average() },
			{"av", new Average() },
			{"long-period-summary", new LongPeriodSummary() },
			{"lps", new LongPeriodSummary() },     
			{"rebase-operation", new RebaseOperation ()},
			{"rbo", new RebaseOperation ()},
	        {"rebase-damocles", new RebaseDamocles ()},
            {"rbd", new RebaseDamocles ()},
            {"rebase-prevision", new RebasePrevision ()},
            {"rbp", new RebasePrevision ()},
            {"set-operation", new SetOperation ()},
            {"so", new SetOperation ()},
            {"set-damocles", new SetDamocles ()},
            {"sd", new SetDamocles ()},
            {"set-prevision", new SetPrevision ()},
            {"sp", new SetPrevision ()},
            {"set-date-operation", new SetDateOperation ()},
            {"sdo", new SetDateOperation ()},
    });
}
