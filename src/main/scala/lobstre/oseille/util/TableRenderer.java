package lobstre.oseille.util;

import java.util.*;

public class TableRenderer {
    public TableRenderer() {
        newLine();
    }

    public TableRenderer left(String text) {
        lines.getLast().addLast(new Left(text));
        return this;
    }

    public TableRenderer right(String text) {
        lines.getLast().addLast(new Right(text));
        return this;
    }

    public TableRenderer newLine() {
        lines.add(new LinkedList<TableRenderer.StringOperation>());
        return this;
    }

    @Override
    public String toString() {
        final Map<Integer, Integer> counts = new HashMap<>();
        int columnCount = 0;
        for (Deque<StringOperation> line : lines) {
            for (StringOperation column : line) {
                Integer col = counts.get(columnCount);
                if (null == col) {
                    col = 0;
                    counts.put(columnCount, col);
                }
                col = Math.max(col, column.getText().length());
                counts.put(columnCount, col);
                columnCount++;
            }
            columnCount = 0;
        }

        final StringBuilder sb = new StringBuilder();
        for (Deque<StringOperation> line : lines) {
            for (final Iterator<StringOperation> i = line.iterator(); i.hasNext(); ) {
                StringOperation word = i.next();
                sb.append(word.render(counts.get(columnCount)));
                columnCount++;
                if (i.hasNext()) {
                    sb.append('|');
                }
            }
            sb.append("\n");
            columnCount = 0;
        }

        return sb.toString();
    }

    private Deque<Deque<StringOperation>> lines = new LinkedList<>();

    private abstract static class StringOperation {
        private final String text;

        public abstract String render(int size);

        public StringOperation(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private static class Left extends StringOperation {

        public Left(String text) {
            super(text);
        }

        @Override
        public String render(int size) {
            return Padding.padLeft(getText(), size);
        }

    }

    private static class Right extends StringOperation {

        public Right(String text) {
            super(text);
        }

        @Override
        public String render(int size) {
            return Padding.padRight(getText(), size);
        }

    }

}
