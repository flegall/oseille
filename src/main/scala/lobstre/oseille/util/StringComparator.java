package lobstre.oseille.util;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        return compareStrings(o1, o2);
    }

    public static int compareStrings(String o1, String o2) {
        if (o1 == null) {
            if (o2 == null) {
                return 0;
            } else {
                return -1;
            }
        }
        if (o2 == null) {
            return 1;
        }
        return o1.compareTo(o2);
    }
}
