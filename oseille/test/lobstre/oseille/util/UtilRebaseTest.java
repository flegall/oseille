package lobstre.oseille.util;

import java.util.Arrays;
import java.util.List;

public class UtilRebaseTest {
    public static void main (String[] args) {
        List<String> list = Arrays.asList ("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m");
        Util.rebase (list, 1, 7);
        System.out.println (list);
        Util.rebase (list, 7, 1);
        System.out.println (list);
        Util.rebase (list, 1, 1);
        System.out.println (list);
        Util.rebase (list, 0, 12);
        System.out.println (list);
        Util.rebase (list, 12, 0);
        System.out.println (list);
        Util.rebase (list, 0, 0);
        System.out.println (list);
        Util.rebase (list, 12, 12);
        System.out.println (list);
        Util.rebase (list, 12, 11);
        System.out.println (list);
    }
}
