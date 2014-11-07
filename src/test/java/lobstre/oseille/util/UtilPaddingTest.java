package lobstre.oseille.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class UtilPaddingTest {
    @Test
    public void test () {
        BigDecimal bd = Util.getBD ("1.5");
        assertEquals(bd, new BigDecimal("1.5"));
        
        assertEquals("   abc", Util.padRight("abc", 6));
        assertEquals("abc", Util.padRight("abc", 3));
        assertEquals("c", Util.padRight("abc", 1));
        
        assertEquals("abc   ", Util.padLeft("abc", 6));
        assertEquals("abc", Util.padLeft("abc", 3));
        assertEquals("a", Util.padLeft("abc", 1));
    }
}
