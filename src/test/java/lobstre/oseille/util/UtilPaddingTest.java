package lobstre.oseille.util;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class UtilPaddingTest {
    @Test
    public void test () {
        BigDecimal bd = Util.getBD ("1.5");
        Assert.assertEquals (bd, new BigDecimal ("1.5"));
        
        Assert.assertEquals ("   abc", Util.padRight ("abc", 6));
        Assert.assertEquals ("abc", Util.padRight ("abc", 3));
        Assert.assertEquals ("c", Util.padRight ("abc", 1));
        
        Assert.assertEquals ("abc   ", Util.padLeft ("abc", 6));
        Assert.assertEquals ("abc", Util.padLeft ("abc", 3));
        Assert.assertEquals ("a", Util.padLeft ("abc", 1));
    }
}
