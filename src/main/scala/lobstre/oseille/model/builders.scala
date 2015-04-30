package lobstre.oseille.model


import java.util.{Collections, Comparator}

import lobstre.oseille.util.StringComparator
import org.threeten.bp.LocalDate._
import org.threeten.bp.format.DateTimeFormatter._


class AccountBuilder {
  def sortOperations() {
    val comparator: StringComparator = new StringComparator
    val operationsComparator: Comparator[OperationBuilder] = new Comparator[OperationBuilder] {
      override def compare(o1: OperationBuilder, o2: OperationBuilder): Int = {
        comparator.compare(o1.getDate, o2.getDate)
      }
    }
    Collections.sort(operations, operationsComparator)
  }

  def getInitialAmount: java.math.BigDecimal = {
    initialAmount
  }

  def setInitialAmount(initialAmout: java.math.BigDecimal) {
    this.initialAmount = initialAmout
  }

  def getBudgets: java.util.NavigableMap[String,  java.math.BigDecimal] = {
    budgets
  }

  def getPrevisions: java.util.List[PrevisionBuilder] = {
    previsions
  }

  def getDamocleses: java.util.List[DamoclesBuilder] = {
    damocleses
  }

  def getOperations: java.util.List[OperationBuilder] = {
    operations
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

    previsions.map { mutablePrevision => mutablePrevision.build()}
  }

  private def toOperations: Seq[Operation] = {
    import scala.collection.JavaConversions._

    operations.map { mutableOperation => mutableOperation.build()}
  }


  private var initialAmount:  java.math.BigDecimal = null
  private final val budgets: java.util.NavigableMap[String, java.math.BigDecimal] = new java.util.TreeMap[String, java.math.BigDecimal]
  private final val previsions: java.util.List[PrevisionBuilder] = new java.util.ArrayList[PrevisionBuilder]
  private final val damocleses: java.util.List[DamoclesBuilder] = new java.util.ArrayList[DamoclesBuilder]
  private final val operations: java.util.List[OperationBuilder] = new java.util.ArrayList[OperationBuilder]
}

class DamoclesBuilder {

  def getCategory: String = {
    category
  }

  def setCategory(category: String) {
    this.category = category
  }

  def getLabel: String = {
    label
  }

  def setLabel(label: String) {
    this.label = label
  }

  def getAmount: java.math.BigDecimal = {
    amount
  }

  def setAmount(value: java.math.BigDecimal) {
    this.amount = value
  }

  def build(): Damocles = Damocles(getCategory, getLabel, getAmount)

  private var category: String = null
  private var label: String = null
  private var amount: java.math.BigDecimal = null
}

class OperationBuilder {
  def getCategory: String = {
    category
  }

  def setCategory(category: String) {
    this.category = category
  }

  def getLabel: String = {
    label
  }

  def setLabel(label: String) {
    this.label = label
  }

  def getAmount: java.math.BigDecimal = {
    amount
  }

  def setAmount(value: java.math.BigDecimal) {
    this.amount = value
  }

  def getDate: String = {
    date
  }

  def setDate(date: String) {
    this.date = date
  }

  def build(): Operation = Operation(category, label, amount, parse(date, ofPattern("yy-MM-dd")))

  private var category: String = null
  private var label: String = null
  private var amount: java.math.BigDecimal = null
  private var date: String = null
}

class PrevisionBuilder {
  def getCategory: String = {
    category
  }

  def setCategory(category: String) {
    this.category = category
  }

  def getLabel: String = {
    label
  }

  def setLabel(label: String) {
    this.label = label
  }

  def getAmount: java.math.BigDecimal = {
    amount
  }

  def setAmount(value: java.math.BigDecimal) {
    this.amount = value
  }

  def build(): Prevision = Prevision(category, label, amount)

  private var category: String = null
  private var label: String = null
  private var amount: java.math.BigDecimal = null
}



