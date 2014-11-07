package util;

import lobstre.oseille.util.Util;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class UtilPaddingTest {
    @Test
    public void test () {
        BigDecimal bd = Util.getBD("1.5");
        assertEquals(bd, new BigDecimal("1.5"));
    }
}
