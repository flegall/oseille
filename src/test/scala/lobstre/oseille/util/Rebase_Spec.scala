package lobstre.oseille.util

import java.util
import java.util.Arrays.asList

import lobstre.oseille.util.Util.rebase
import org.scalatest.{FlatSpec, Matchers}

class Rebase_Spec extends FlatSpec with Matchers {
  behavior of "Rebase"

  it should "rebase properly" in {
    val list: util.List[String] = asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m")

    rebase(list, 1, 7)
    assert("[a, c, d, e, f, g, h, b, i, j, k, l, m]" == list.toString)

    rebase(list, 7, 1)
    assert("[a, b, c, d, e, f, g, h, i, j, k, l, m]" == list.toString)

    rebase(list, 1, 1)
    assert("[a, b, c, d, e, f, g, h, i, j, k, l, m]" == list.toString)

    rebase(list, 0, 12)
    assert("[b, c, d, e, f, g, h, i, j, k, l, m, a]" == list.toString)


    rebase(list, 12, 0)
    assert("[a, b, c, d, e, f, g, h, i, j, k, l, m]" == list.toString)

    rebase(list, 0, 0)
    assert("[a, b, c, d, e, f, g, h, i, j, k, l, m]" == list.toString)

    rebase(list, 12, 12)
    assert("[a, b, c, d, e, f, g, h, i, j, k, l, m]" == list.toString)

    rebase(list, 12, 11)
    assert("[a, b, c, d, e, f, g, h, i, j, k, m, l]" == list.toString)
  }
}
