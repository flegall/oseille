package lobstre.oseille.model

import java.math.BigDecimal

class MutablePrevision {
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

  private var category: String = null
  private var label: String = null
  private var amount: BigDecimal = null
}

