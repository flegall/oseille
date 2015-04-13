package lobstre.oseille.util

import java.util.Arrays.asList

import lobstre.oseille.util.Util.rebase
import org.scalatest.{FlatSpec, Matchers}

class Rebase_Spec extends FlatSpec with Matchers {
  behavior of "Rebase"

  it should "rebase properly" in {
    val list = asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m")

    rebase(list, 1, 7)
    "[a, c, d, e, f, g, h, b, i, j, k, l, m]" shouldBe list.toString

    rebase(list, 7, 1)
    "[a, b, c, d, e, f, g, h, i, j, k, l, m]" shouldBe list.toString

    rebase(list, 1, 1)
    "[a, b, c, d, e, f, g, h, i, j, k, l, m]" shouldBe list.toString

    rebase(list, 0, 12)
    "[b, c, d, e, f, g, h, i, j, k, l, m, a]" shouldBe list.toString


    rebase(list, 12, 0)
    "[a, b, c, d, e, f, g, h, i, j, k, l, m]" shouldBe list.toString

    rebase(list, 0, 0)
    "[a, b, c, d, e, f, g, h, i, j, k, l, m]" shouldBe list.toString

    rebase(list, 12, 12)
    "[a, b, c, d, e, f, g, h, i, j, k, l, m]" shouldBe list.toString

    rebase(list, 12, 11)
    "[a, b, c, d, e, f, g, h, i, j, k, m, l]" shouldBe list.toString
  }
}
