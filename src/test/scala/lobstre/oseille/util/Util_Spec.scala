package lobstre.oseille.util

import java.math.BigDecimal

import org.scalatest._

class Util_Padding_Spec extends FlatSpec with Matchers {
  behavior of "Util.Padding()"

  it should "pad right correctly" in {
    assert("   abc" == Util.padRight("abc", 6))
    assert("abc" == Util.padRight("abc", 3))
    assert("c" == Util.padRight("abc", 1))
  }

  it should "pad left correctly" in {
    assert("abc   " == Util.padLeft("abc", 6))
    assert("abc" == Util.padLeft("abc", 3))
    assert("a" == Util.padLeft("abc", 1))
  }
}

class Util_getBD_Spec extends FlatSpec with Matchers {
  behavior of "Util.getBd()"

  it should "parse a big decimal correctly" in {
    val bd: BigDecimal = Util.getBD("1.5")
    assert(bd == new BigDecimal("1.5"))
  }
}