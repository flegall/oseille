package lobstre.oseille.util

import java.math.BigDecimal

import org.scalatest._

class Padding_Spec extends FlatSpec with Matchers {
  behavior of "Padding"

  it should "pad right correctly" in {
    assert("   abc" == Padding.padRight("abc", 6))
    assert("abc" == Padding.padRight("abc", 3))
    assert("c" == Padding.padRight("abc", 1))
  }

  it should "pad left correctly" in {
    assert("abc   " == Padding.padLeft("abc", 6))
    assert("abc" == Padding.padLeft("abc", 3))
    assert("a" == Padding.padLeft("abc", 1))
  }
}

class Util_getBD_Spec extends FlatSpec with Matchers {
  behavior of "Util.getBd()"

  it should "parse a big decimal correctly" in {
    val bd: BigDecimal = Util.getBD("1.5")
    assert(bd == new BigDecimal("1.5"))
  }
}