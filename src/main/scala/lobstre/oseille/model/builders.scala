package lobstre.oseille.model


import java.util.{Collections, Comparator}

import lobstre.oseille.model.DamoclesBuilder.BY_AMOUNT
import lobstre.oseille.util.StringComparator
import org.threeten.bp.LocalDate._
import org.threeten.bp.format.DateTimeFormatter._

import scala.beans.BeanProperty

class AccountBuilder {
  @BeanProperty
  var initialAmount: java.math.BigDecimal = null

  @BeanProperty
  val budgets: java.util.NavigableMap[String, java.math.BigDecimal] = new java.util.TreeMap[String, java.math.BigDecimal]

  @BeanProperty
  val previsions: java.util.List[PrevisionBuilder] = new java.util.ArrayList[PrevisionBuilder]

  @BeanProperty
  val damocleses: java.util.List[DamoclesBuilder] = new java.util.ArrayList[DamoclesBuilder]

  @BeanProperty
  val operations: java.util.List[OperationBuilder] = new java.util.ArrayList[OperationBuilder]

  def sortOperations() {
    val comparator: StringComparator = new StringComparator
    val operationsComparator: Comparator[OperationBuilder] = new Comparator[OperationBuilder] {
      override def compare(o1: OperationBuilder, o2: OperationBuilder): Int = {
        comparator.compare(o1.getDate, o2.getDate)
      }
    }
    Collections.sort(operations, operationsComparator)
  }

  def sortDamocleses() = {
    Collections.sort(damocleses, BY_AMOUNT)
  }

  def build(): Account = {
    Account(initialAmount = initialAmount,
      budgets = buildBudgets,
      damocleses = toDamocleses,
      previsions = toPrevisions,
      operations = toOperations)
  }

  private def buildBudgets: Map[String, BigDecimal] = {
    import scala.collection.JavaConversions._

    budgets.map {
      case (category, amount) =>
        category -> BigDecimal(amount)
    }.toMap
  }

  private def toDamocleses: Seq[Damocles] = {
    import scala.collection.JavaConversions._

    damocleses.map { mutableDamocles => mutableDamocles.build() }
  }

  private def toPrevisions: Seq[Prevision] = {
    import scala.collection.JavaConversions._

    previsions.map { mutablePrevision => mutablePrevision.build() }
  }

  private def toOperations: Seq[Operation] = {
    import scala.collection.JavaConversions._

    operations.map { mutableOperation => mutableOperation.build() }
  }
}

class DamoclesBuilder {

  @BeanProperty
  var category: String = null

  @BeanProperty
  var label: String = null

  @BeanProperty
  var amount: java.math.BigDecimal = null

  def build(): Damocles = Damocles(getCategory, getLabel, getAmount)

  def withAmount(decimal: java.math.BigDecimal): DamoclesBuilder = {
    amount = decimal
    this
  }
}

object DamoclesBuilder {
  def newDamoclesBuilder() = new DamoclesBuilder()

  val BY_AMOUNT: Comparator[DamoclesBuilder] with Object {def compare(o1: DamoclesBuilder, o2: DamoclesBuilder): Int} = new Comparator[DamoclesBuilder] {
    override def compare(o1: DamoclesBuilder, o2: DamoclesBuilder): Int = {
      o1.amount.compareTo(o2.amount)
    }
  }
}

class OperationBuilder {

  @BeanProperty
  var category: String = null

  @BeanProperty
  var label: String = null

  @BeanProperty
  var amount: java.math.BigDecimal = null

  @BeanProperty
  var date: String = null

  def build(): Operation = Operation(category, label, amount, parse(date, ofPattern("yy-MM-dd")))
}

class PrevisionBuilder {

  @BeanProperty
  var category: String = null

  @BeanProperty
  var label: String = null

  @BeanProperty
  var amount: java.math.BigDecimal = null

  def build(): Prevision = Prevision(category, label, amount)
}



