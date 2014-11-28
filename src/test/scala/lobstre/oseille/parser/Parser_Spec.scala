package lobstre.oseille.parser

import java.io.File
import java.math.BigDecimal

import lobstre.oseille.parser.Parser.{read, write}
import org.scalatest.{FlatSpec, Matchers}

class Parser_Spec extends FlatSpec with Matchers {
  behavior of "Parser"

  it should "parse accounts correctly" in {
    val account = read(new File("test-files/test.txt"))

    assert(account.getInitialAmount == new BigDecimal("300.0"))
  }

  it should "write accounts correctly" in {
    val original = read(new File("test-files/test.txt"))

    write(original, new File("test-files/test-output.txt"))
    val copy = read(new File("test-files/test-output.txt"))

    assert(original.getBudgets == copy.getBudgets)
  }
}
