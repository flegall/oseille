package lobstre.oseille.model

import java.math.BigDecimal
import java.util
import java.util.{Comparator, Collections}

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

  def getInitialAmount: BigDecimal = {
    initialAmount
  }

  def setInitialAmount(initialAmout: BigDecimal) {
    this.initialAmount = initialAmout
  }

  def getBudgets: util.NavigableMap[String, BigDecimal] = {
    budgets
  }

  def getPrevisions: util.List[MutablePrevision] = {
    previsions
  }

  def getDamocleses: util.List[MutableDamocles] = {
    damocleses
  }

  def getOperations: util.List[MutableOperation] = {
    operations
  }

  private var initialAmount: BigDecimal = null
  private final val budgets: util.NavigableMap[String, BigDecimal] = new util.TreeMap[String, BigDecimal]
  private final val previsions: util.List[MutablePrevision] = new util.ArrayList[MutablePrevision]
  private final val damocleses: util.List[MutableDamocles] = new util.ArrayList[MutableDamocles]
  private final val operations: util.List[MutableOperation] = new util.ArrayList[MutableOperation]
}
