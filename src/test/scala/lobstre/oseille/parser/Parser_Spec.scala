package lobstre.oseille.parser

import java.io.File
import java.math.BigDecimal

import lobstre.oseille.model.MutableDamocles
import lobstre.oseille.parser.Parser.{read, write}
import lobstre.oseille.util.Util
import org.scalatest.{FlatSpec, Matchers}

class Parser_Spec extends FlatSpec with Matchers {
  behavior of "Parser"

  it should "parse accounts correctly" in {
    val account = read(new File("test-files/test.txt"))

    account.getInitialAmount shouldBe Util.getBD("300.0")
    account.getBudgets.get("car") shouldBe Util.getBD("600")

    account.getDamocleses should have size 1
    val damocles = account.getDamocleses.get(0)
    damocles.getCategory shouldBe "car"
    damocles.getLabel shouldBe "plein-essence"
    damocles.getAmount shouldBe Util.getBD("55.75")

    account.getPrevisions should have size 2
    val prevision = account.getPrevisions.get(0)
    prevision.getCategory shouldBe "car"
    prevision.getLabel shouldBe "vidange"
    prevision.getAmount shouldBe Util.getBD("25.75")

    account.getOperations should have size 3
    val operation = account.getOperations.get(0)
    operation.getCategory shouldBe "car"
    operation.getLabel shouldBe "plein-essence"
    operation.getAmount shouldBe Util.getBD("45.75")
    operation.getDate shouldBe "10-08-10"
  }

  it should "write accounts correctly" in {
    val original = read(new File("test-files/test.txt"))

    write(original, new File("test-files/test-output.txt"))
    val copy = read(new File("test-files/test-output.txt"))

    original.getBudgets shouldBe copy.getBudgets
  }
}
