package lobstre.osll.util;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {
	public int compare(String o1, String o2) {
		return compareStrings(o1, o2);
	}
	
	public static int compareStrings(String o1, String o2) {
		return comp(o1, o2, true);
	}
	
	public static int compareStringsIgnoreCase(String o1, String o2) {
		return comp(o1, o2, false);
	}

	private static int comp(String o1, String o2, boolean caseSensitive) {
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
		if (caseSensitive) {
			return o1.compareTo(o2);
		} else {
			return o1.compareToIgnoreCase(o2);
		}
	}
}
