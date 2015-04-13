package lobstre.oseille.util

import java.math.BigDecimal

import lobstre.oseille.util.Padding.{padLeft, padRight}
import lobstre.oseille.util.Util.getBD
import org.scalatest._

class Padding_Spec extends FlatSpec with Matchers {
  behavior of "Padding"

  it should "pad right correctly" in {
    "   abc" shouldBe padRight("abc", 6)
    "abc" shouldBe padRight("abc", 3)
    "c" shouldBe padRight("abc", 1)
  }

  it should "pad left correctly" in {
    "abc   " shouldBe padLeft("abc", 6)
    "abc" shouldBe padLeft("abc", 3)
    "a" shouldBe padLeft("abc", 1)
  }
}

class Util_getBD_Spec extends FlatSpec with Matchers {
  behavior of "Util.getBd()"

  it should "parse a big decimal correctly" in {
    val bd = getBD("1.5")
    bd shouldBe new BigDecimal("1.5")
  }
}