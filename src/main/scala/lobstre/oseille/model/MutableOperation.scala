package lobstre.oseille.model

import java.math.BigDecimal

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

  private var category: String = null
  private var label: String = null
  private var amount: BigDecimal = null
  private var date: String = null
}

