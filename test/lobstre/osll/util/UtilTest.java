package lobstre.osll.util;

import java.math.BigDecimal;

public class UtilTest {
    public static void main (String[] args) {
        BigDecimal bd = Util.getBD ("1.5");
        System.out.println (bd);
        
        Util.padRight ("abc", 6);
        Util.padRight ("abc", 3);
        Util.padRight ("abc", 1);
        
        Util.padLeft ("abc", 6);
        Util.padLeft ("abc", 3);
        Util.padLeft ("abc", 1);
    }
}
