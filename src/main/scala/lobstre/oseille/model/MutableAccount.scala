package lobstre.oseille.model

import java.util
import java.util.{Collections, Comparator}

import lobstre.oseille.model.MutableAccount.{toDamocleses, toScalaMap}
import lobstre.oseille.util.StringComparator


class MutableAccount {

  def sortOperations {
    val comparator: StringComparator = new StringComparator
    val operationsComparator: Comparator[MutableOperation] = new Comparator[MutableOperation] {
      override def compare(o1: MutableOperation, o2: MutableOperation): Int = {
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

  def getPrevisions: java.util.List[MutablePrevision] = {
    previsions
  }

  def getDamocleses: java.util.List[MutableDamocles] = {
    damocleses
  }

  def getOperations: java.util.List[MutableOperation] = {
    operations
  }

  def build(): Account = {
    Account(initialAmount = initialAmount,
      budgets = toScalaMap(budgets),
      damocleses = toDamocleses(damocleses),
      previsions= Seq.empty,
      operations = Seq.empty)
  }

  private var initialAmount:  java.math.BigDecimal = null
  private final val budgets: java.util.NavigableMap[String, java.math.BigDecimal] = new java.util.TreeMap[String, java.math.BigDecimal]
  private final val previsions: java.util.List[MutablePrevision] = new java.util.ArrayList[MutablePrevision]
  private final val damocleses: java.util.List[MutableDamocles] = new java.util.ArrayList[MutableDamocles]
  private final val operations: java.util.List[MutableOperation] = new java.util.ArrayList[MutableOperation]
}

object MutableAccount {
  private[MutableAccount] def toScalaMap(budgets: java.util.NavigableMap[String, java.math.BigDecimal]): Map[String, BigDecimal] = {
    import scala.collection.JavaConversions._

    budgets.map {
      case (category, amount) =>
        category -> BigDecimal(amount)
    }.toMap
  }

  private[MutableAccount] def toDamocleses(damocleses: java.util.List[MutableDamocles]): Seq[Damocles] = {
    Seq.empty
  }
}
