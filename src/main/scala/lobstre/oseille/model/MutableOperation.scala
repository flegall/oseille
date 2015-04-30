package lobstre.oseille.model

import java.math.BigDecimal

import org.threeten.bp.LocalDate.parse
import org.threeten.bp.format.DateTimeFormatter.ofPattern

class MutableOperation {
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

  def getAmount: BigDecimal = {
    amount
  }

  def setAmount(value: BigDecimal) {
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
  private var amount: BigDecimal = null
  private var date: String = null
}

