package lobstre.oseille.model

import java.math.BigDecimal
import java.util
import java.util.{Comparator, Collections}

import lobstre.oseille.util.StringComparator


class MutableAccount {

  def sortOperations {
    val comparator: StringComparator = new StringComparator
    val operationsComparator: Comparator[Operation] = new Comparator[Operation] {
      override def compare(o1: Operation, o2: Operation): Int = {
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

  def getPrevisions: util.List[Prevision] = {
    previsions
  }

  def getDamocleses: util.List[Damocles] = {
    damocleses
  }

  def getOperations: util.List[Operation] = {
    operations
  }

  private var initialAmount: BigDecimal = null
  private final val budgets: util.NavigableMap[String, BigDecimal] = new util.TreeMap[String, BigDecimal]
  private final val previsions: util.List[Prevision] = new util.ArrayList[Prevision]
  private final val damocleses: util.List[Damocles] = new util.ArrayList[Damocles]
  private final val operations: util.List[Operation] = new util.ArrayList[Operation]
}
