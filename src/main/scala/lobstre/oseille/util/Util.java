package lobstre.oseille.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static BigDecimal getBD (final String value) {
        final BigDecimal bigDecimal;
        try {
            bigDecimal = new BigDecimal (value);
        } catch (final NumberFormatException nfe) {
            System.out.println ("Value : '" + value + "' couldn't be parsed");
            throw nfe;
        }
        return bigDecimal;
    }

    /**
     * Transform an array of objects array into a Map : iterates on the array,
     * and takes the sub-array first element as key and the sub-array second
     * element as value.
     * 
     * Example : final Map<Integer, String> mapping = ArrayUtils.asMap (new
     * Object [][] { { 1, "one"}, { 2, "two"}, { 3, "three"}, });
     * 
     * @return Map Map containing the key/values
     * @param in
     *            the input array (Object [][])
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> asMap (final Object[][] in) {
        final Map<K, V> ret = new LinkedHashMap<K, V> (in.length);

        for (final Object[] keyValue : in) {
            ret.put ((K) keyValue [0], (V) keyValue [1]);
        }

        return Collections.unmodifiableMap (ret);
    }

    /**
     * Renders a Number as String using 0.00 decimal format.
     * 
     * @param value
     *            the {@link Number} value
     * @return the {@link String} text
     */
    public static String renderNumber (final Number value) {
        return new DecimalFormat ("0.00").format (value);
    }

    /**
     * Moves a value from a position to another within a List
     * 
     * @param list a {@link List}
     * @param from a source position
     * @param to a source destination
     */
    public static <T> void rebase (final List<T> list, final int from, final int to) {
        final int min = Math.min (from, to);
        final int max = Math.max (from, to);
        final List<T> subList = list.subList (min, max + 1);

        final int direction = from < to ? -1 : +1;
        Collections.rotate (subList, direction);
    }
}
