package lobstre.oseille.util

case object Padding {
  /**
   * Pads or cuts a String to be aligned to the left.
   *
   * @param value
     * the { @link String} value
   * @param size
     * the text size
   * @return a { @link String} of the desired size, including a cut or padded
   *                   copy of input { @link String}
   */
  def padLeft(value: String, size: Int): String = {
    if (value.length == size) {
      value
    } else if (value.length > size) {
      value substring(0, size)
    } else {
      value + " " * (size - value.length)
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
    if (value.length == size) {
      value
    } else if (value.length > size) {
      value substring(value.length - size, value.length)
    } else {
      " " * (size - value.length) + value
    }
  }
}
