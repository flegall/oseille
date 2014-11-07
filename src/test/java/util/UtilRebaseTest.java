package util;

import lobstre.oseille.util.Util;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UtilRebaseTest {
    @Test
    public void test () {
        List<String> list = Arrays.asList ("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m");
        Util.rebase(list, 1, 7);
        assertEquals("[a, c, d, e, f, g, h, b, i, j, k, l, m]", list.toString());
        Util.rebase (list, 7, 1);
        assertEquals("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString());
        Util.rebase (list, 1, 1);
        assertEquals("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString());
        Util.rebase (list, 0, 12);
        assertEquals("[b, c, d, e, f, g, h, i, j, k, l, m, a]", list.toString());
        Util.rebase (list, 12, 0);
        assertEquals("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString());
        Util.rebase (list, 0, 0);
        assertEquals("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString());
        Util.rebase (list, 12, 12);
        assertEquals("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString());
        Util.rebase (list, 12, 11);
        assertEquals("[a, b, c, d, e, f, g, h, i, j, k, m, l]", list.toString());
    }
}
