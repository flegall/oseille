package lobstre.oseille.util

import java.math.BigDecimal

import lobstre.oseille.util.Padding.{padLeft, padRight}
import lobstre.oseille.util.Util.getBD
import org.scalatest._

class Padding_Spec extends FlatSpec with Matchers {
  behavior of "Padding"

  it should "pad right correctly" in {
    assert("   abc" == padRight("abc", 6))
    assert("abc" == padRight("abc", 3))
    assert("c" == padRight("abc", 1))
  }

  it should "pad left correctly" in {
    assert("abc   " == padLeft("abc", 6))
    assert("abc" == padLeft("abc", 3))
    assert("a" == padLeft("abc", 1))
  }
}

class Util_getBD_Spec extends FlatSpec with Matchers {
  behavior of "Util.getBd()"

  it should "parse a big decimal correctly" in {
    val bd = getBD("1.5")
    assert(bd == new BigDecimal("1.5"))
  }
}