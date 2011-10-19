package lobstre.osll.util;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class TableRenderer {
    public TableRenderer () {
        newLine ();
    }

    public TableRenderer left (String text) {
        lines.getLast ().addLast (new Left (text));
        return this;
    }

    public TableRenderer right (String text) {
        lines.getLast ().addLast (new Right (text));
        return this;
    }

    public TableRenderer newLine () {
        lines.add (new LinkedList<TableRenderer.StringOperation> ());
        return this;
    }

    @Override
    public String toString () {
        final Map<Integer, Integer> counts = new HashMap<Integer, Integer> ();
        int columnCount = 0;
        for (Deque<StringOperation> line : lines) {
            for (StringOperation column : line) {
                Integer col = counts.get (columnCount);
                if (null == col) {
                    col = Integer.valueOf (0);
                    counts.put (columnCount, col);
                }
                col = Integer.valueOf (Math.max (col.intValue (), column.getText ().length ()));
                counts.put (columnCount, col);
                columnCount++;
            }
            columnCount = 0;
        }

        final StringBuilder sb = new StringBuilder ();
        for (Deque<StringOperation> line : lines) {
            for (final Iterator<StringOperation> i = line.iterator (); i.hasNext ();) {
                StringOperation word = i.next ();
                sb.append (word.render (counts.get (columnCount)));
                columnCount++;
                if (i.hasNext ()) {
                    sb.append ('|');
                }
            }
            sb.append ("\n");
            columnCount = 0;
        }

        return sb.toString ();
    }

    private Deque<Deque<StringOperation>> lines = new LinkedList<Deque<StringOperation>> ();

    private abstract static class StringOperation {
        private final String text;

        public abstract String render (int size);

        public StringOperation (String text) {
            this.text = text;
        }

        public String getText () {
            return text;
        }
    }

    private static class Left extends StringOperation {

        public Left (String text) {
            super (text);
        }

        @Override
        public String render (int size) {
            return Util.padLeft (getText (), size);
        }

    }

    private static class Right extends StringOperation {

        public Right (String text) {
            super (text);
        }

        @Override
        public String render (int size) {
            return Util.padRight (getText (), size);
        }

    }

}
