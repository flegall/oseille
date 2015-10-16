package lobstre.oseille.util

import java.text.DecimalFormat
import java.util.Collections

object Util {
  def bigDecimal(value: String): BigDecimal = {
    try {
      BigDecimal(value)
    }
    catch {
      case nfe: NumberFormatException =>
        System.out.println("Value : '" + value + "' couldn't be parsed")
        throw nfe
    }
  }

  def getBD(value: String): java.math.BigDecimal = {
    try {
      new java.math.BigDecimal(value)
    }
    catch {
      case nfe: NumberFormatException =>
        System.out.println("Value : '" + value + "' couldn't be parsed")
        throw nfe
    }
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
     * the input array (Object [][])
    */
  @SuppressWarnings(Array("unchecked"))
  def asMap[K, V](in: Array[Array[AnyRef]]): java.util.Map[K, V] = {
    val ret: java.util.Map[K, V] = new java.util.LinkedHashMap[K, V](in.length)
    for (keyValue <- in) {
      ret.put(keyValue(0).asInstanceOf[K], keyValue(1).asInstanceOf[V])
    }
    Collections.unmodifiableMap(ret)
  }

  /**
    * Renders a Number as String using 0.00 decimal format.
    *
    * @param value
     * the { @link Number} value
    * @return the { @link String} text
    */
  def renderNumber(value: Number): String = {
    new DecimalFormat("0.00").format(value)
  }

  /**
    * Moves a value from a position to another within a List
    *
    * @param list a { @link List}
    * @param from a source position
    * @param to a source destination
    */
  def rebase[T](list: java.util.List[T], from: Int, to: Int) {
    val min: Int = Math.min(from, to)
    val max: Int = Math.max(from, to)
    val subList: java.util.List[T] = list.subList(min, max + 1)
    val direction: Int = if (from < to) -1 else +1
    Collections.rotate(subList, direction)
  }
}
