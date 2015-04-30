package lobstre.oseille.parser

import java.io.File
import java.math.BigDecimal

import lobstre.oseille.model.MutableDamocles
import lobstre.oseille.parser.Parser.{readAccount, read, write}
import lobstre.oseille.util.Util
import lobstre.oseille.util.Util.bigDecimal
import org.scalatest.{FlatSpec, Matchers}
import org.threeten.bp.LocalDate

class Parser_Spec extends FlatSpec with Matchers {
  behavior of "Parser"

  it should "parse accounts correctly" in {
    val account = readAccount(new File("test-files/test.txt"))

    account.initialAmount shouldBe bigDecimal("300.0")
    account.budgets.get("car") shouldBe Some(bigDecimal("600"))

    account.damocleses should have size 1
    val damocles = account.damocleses.head
    damocles.category shouldBe "car"
    damocles.label shouldBe "plein-essence"
    damocles.amount shouldBe bigDecimal("55.75")

    account.previsions should have size 2
    val prevision = account.previsions.head
    prevision.category shouldBe "car"
    prevision.label shouldBe "vidange"
    prevision.amount shouldBe bigDecimal("25.75")

    account.operations should have size 3
    val operation = account.operations.head
    operation.category shouldBe "car"
    operation.label shouldBe "plein-essence"
    operation.amount shouldBe bigDecimal("45.75")
    operation.date shouldBe LocalDate.of(2010, 8, 10)
  }

  it should "write accounts correctly" in {
    val original = read(new File("test-files/test.txt"))

    write(original, new File("test-files/test-output.txt"))
    val copy = read(new File("test-files/test-output.txt"))

    original.getBudgets shouldBe copy.getBudgets
  }
}
