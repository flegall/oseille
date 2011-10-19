package lobstre.osll.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {
    public static BigDecimal getBD (final String value) {
        final BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal (value);
        } catch (NumberFormatException nfe) {
            System.out.println ("Value : '" + value + "' couldn't be parsed");
            throw nfe;
        }
        return bigDecimal;
    }
    
    /**
     * Transform an array of objects array into a Map : iterates on the array, and takes the sub-array 
     * first element as key and the sub-array second element as value. 
     * 
     * Example : 
     * final Map<Integer, String> mapping = ArrayUtils.asMap (new Object [][] {
     *     { 1, "one"},
     *     { 2, "two"},
     *     { 3, "three"}, 
     * });
     * 
     * @return Map
     *        Map containing the key/values
     * @param in
     *        the input array (Object [][])
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> asMap (final Object [][] in) {
        final Map<K, V> ret = new LinkedHashMap <K, V> (in.length);
        
        for (Object[] keyValue : in) {
            ret.put ((K)keyValue[0], (V)keyValue[1]);
        }

        return Collections.unmodifiableMap (ret);
    }

    public static String padRight (String value, int padding) {
        int startIndex = padding - value.length ();
        final StringBuilder sb = new StringBuilder (padding);
        for (int i = 0; i < padding; i++) {
            sb.append (' ');
            if (i >= startIndex) {
                sb.setCharAt (i, value.charAt (i - startIndex));
            }
            
        }
        return sb.toString ();
    }
    
    public static String padLeft (String value, int padding) {
        final StringBuilder sb = new StringBuilder (padding);
        for (int i = 0; i < padding; i++) {
            sb.append (' ');
            if (i < value.length ()) {
                sb.setCharAt (i, value.charAt (i));
            }
            
        }
        return sb.toString ();
    }

    public static String renderNumber (Number value) {
        return new DecimalFormat ("0.00").format (value);
    }
}
