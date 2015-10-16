package lobstre.oseille.model

import lobstre.oseille.model.DamoclesBuilder.newDamoclesBuilder
import org.scalatest.{FlatSpec, Matchers}
import lobstre.oseille.util.Util

class AccountBuilder_Spec extends FlatSpec with Matchers {
  behavior of "AccountBuilder"

  it should "sort damocleses correctly" in {
    val account = new AccountBuilder()
    val damocles1 = newDamoclesBuilder().withAmount(Util.getBD("2.0"))
    val damocles2 = newDamoclesBuilder().withAmount(Util.getBD("1.0"))
    account.damocleses.add(damocles1)
    account.damocleses.add(damocles2)

    val sortedDamocleses = account.sortDamocleses()

    account.damocleses.get(0) shouldBe damocles2
    account.damocleses.get(1) shouldBe damocles1
  }
}
