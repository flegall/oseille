package lobstre.oseille.util

case object Padding {
  /**
   * Pads or cuts a String to be aligned to the left.
   *
   * @param value
     * the { @link String} value
   * @param padding
     * the text size
   * @return a { @link String} of the desired size, including a cut or padded
   *                   copy of input { @link String}
   */
  def padLeft(value: String, padding: Int): String = {
    if (value.length == padding) {
      value
    } else if (value.length > padding) {
      value substring(0, padding)
    } else {
      value + " " * (padding - value.length);
    }
  }

  /**
   * Pads or cuts a String to be aligned to the right.
   *
   * @param value
     * the { @link String} value
   * @param size
     * the text size
   * @return a { @link String} of the desired size, including a cut or padded
   *                   copy of input { @link String}
   */
  def padRight(value: String, size: Int): String = {
    val startIndex = size - value.length
    val sb = new StringBuilder(size)
    var i: Int = 0
    while (i < size) {
      {
        sb.append(' ')
        if (i >= startIndex) {
          sb.setCharAt(i, value.charAt(i - startIndex))
        }
        i += 1
      }
    }
    sb.toString()
  }
}
