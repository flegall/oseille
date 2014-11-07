package lobstre.oseille.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class UtilRebaseTest {
    @Test
    public void test () {
        List<String> list = Arrays.asList ("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m");
        Util.rebase (list, 1, 7);
        Assert.assertEquals("[a, c, d, e, f, g, h, b, i, j, k, l, m]", list.toString());
        Util.rebase (list, 7, 1);
        Assert.assertEquals ("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString ());
        Util.rebase (list, 1, 1);
        Assert.assertEquals ("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString ());
        Util.rebase (list, 0, 12);
        Assert.assertEquals ("[b, c, d, e, f, g, h, i, j, k, l, m, a]", list.toString ());
        Util.rebase (list, 12, 0);
        Assert.assertEquals ("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString ());
        Util.rebase (list, 0, 0);
        Assert.assertEquals ("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString ());
        Util.rebase (list, 12, 12);
        Assert.assertEquals ("[a, b, c, d, e, f, g, h, i, j, k, l, m]", list.toString ());
        Util.rebase (list, 12, 11);
        Assert.assertEquals ("[a, b, c, d, e, f, g, h, i, j, k, m, l]", list.toString ());
    }
}
