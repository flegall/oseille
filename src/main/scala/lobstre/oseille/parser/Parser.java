package lobstre.oseille.parser;

import lobstre.oseille.model.Damocles;
import lobstre.oseille.model.MutableAccount;
import lobstre.oseille.model.Operation;
import lobstre.oseille.model.Prevision;
import lobstre.oseille.util.Util;

import java.io.*;
import java.math.BigDecimal;
import java.util.Map;

public class Parser {
    private static final String INIT_TOKEN = "init:";
    private static final String BUDGET_TOKEN = "budget:";
    private static final String PREVISION_TOKEN = "prevision:";
    private static final String DAMOCLES_TOKEN = "damocles:";
    private static final String OPERATION_TOKEN = "operation:";

    public static MutableAccount read (final File file) throws IOException {
        final MutableAccount acc = new MutableAccount();
        
        final BufferedReader in = new BufferedReader (new FileReader (file));
        try {
            String line;
            while (null != (line = in.readLine ())) {
                final String[] split = line.split (":");
                if (line.startsWith (INIT_TOKEN)) {
                    check (INIT_TOKEN, split, 2);
                    acc.setInitialAmount (Util.getBD (split[1]));
                }
                if (line.startsWith (BUDGET_TOKEN)) {
                    check (BUDGET_TOKEN, split, 3);
                    final BigDecimal bd = Util.getBD (split[2]);
                    acc.getBudgets ().put (split[1], bd);
                }
                if (line.startsWith (PREVISION_TOKEN)) {
                    check (PREVISION_TOKEN, split, 4);
                    final Prevision p = new Prevision ();
                    p.setCategory (split[1]);
                    p.setLabel (split[2]);
                    p.setAmount (Util.getBD (split[3]));
                    acc.getPrevisions ().add (p);
                }                
                if (line.startsWith (DAMOCLES_TOKEN)) {
                    check (DAMOCLES_TOKEN, split, 4);
                    final Damocles dc = new Damocles ();
                    dc.setCategory (split[1]);
                    dc.setLabel (split[2]);
                    dc.setAmount (Util.getBD (split[3]));
                    acc.getDamocleses ().add (dc);
                }
                if (line.startsWith (OPERATION_TOKEN)) {
                    check (OPERATION_TOKEN, split, 5);
                    final Operation o = new Operation ();
                    o.setCategory (split[1]);
                    o.setLabel (split[2]);
                    o.setAmount (Util.getBD (split[3]));
                    o.setDate (split[4]);
                    acc.getOperations ().add (o);
                }                
            }
        } finally {
            in.close ();
        }
        
        initZeroBudgets (acc);
        acc.sortOperations ();
        return acc;
    }
    
    public static void write (final MutableAccount acc, final File file) throws IOException {
        acc.sortOperations ();
        initZeroBudgets (acc);
        
        final PrintWriter pw = new PrintWriter (file);
        try {
            pw.println (INIT_TOKEN + acc.getInitialAmount ().toString ());
            for (final Map.Entry<String, BigDecimal> b : acc.getBudgets ().entrySet ()) {
                pw.println (BUDGET_TOKEN + b.getKey () + ":" + b.getValue ());
            }
            for (final Prevision p : acc.getPrevisions ()) {
                pw.println (PREVISION_TOKEN + p.getCategory () + ":" + p.getLabel () + ":" + p.getAmount ());
            }            
            for (final Damocles d : acc.getDamocleses ()) {
                pw.println (DAMOCLES_TOKEN + d.getCategory () + ":" + d.getLabel () + ":" + d.getAmount ());
            }
            for (final Operation o : acc.getOperations ()) {
                pw.println (OPERATION_TOKEN + o.getCategory () + ":" + o.getLabel () + ":" + o.getAmount () + ":" + o.getDate ());
            }
        } finally {
            pw.close ();
        }
    }

    private static void initZeroBudgets (final MutableAccount acc) {
        for (final Prevision p : acc.getPrevisions ()) {
            if (!acc.getBudgets ().containsKey (p.getCategory ())) {
                acc.getBudgets ().put (p.getCategory (), BigDecimal.valueOf (0));
            }
        }
        for (final Damocles d : acc.getDamocleses ()) {
            if (!acc.getBudgets ().containsKey (d.getCategory ())) {
                acc.getBudgets ().put (d.getCategory (), BigDecimal.valueOf (0));
            }
        }
        for (final Operation o : acc.getOperations ()) {
            if (!acc.getBudgets ().containsKey (o.getCategory ())) {
                acc.getBudgets ().put (o.getCategory (), BigDecimal.valueOf (0));
            }
        }
    }

    private static void check (final String initToken, final String[] split, final int expected) {
        if (split.length != expected) {
            throw new RuntimeException (initToken + " expected " + expected + " values");
        }
    }
}
