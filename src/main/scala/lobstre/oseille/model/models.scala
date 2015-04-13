package lobstre.oseille.model

import org.threeten.bp.LocalDate

sealed trait Expense {
  def category: String
  def label: String
  def amount: BigDecimal
}

case class Damocles(category: String, label: String, amount: BigDecimal)
  extends Expense

case class Prevision(category: String, label: String, amount: BigDecimal)
  extends Expense

case class Operation(category: String, label: String, amount: BigDecimal, date: LocalDate)
  extends Expense

case class Account(initialAmount: BigDecimal,
                   budgets: Map[String, BigDecimal],
                   damocleses: Seq[Damocles],
                   previsions: Seq[Damocles],
                   operations: Seq[Operation])

