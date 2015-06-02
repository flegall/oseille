package lobstre.oseille.parser

import java.io._
import java.math.BigDecimal

import lobstre.oseille.model._
import lobstre.oseille.util.Util

object Parser {
  private val INIT_TOKEN: String = "init:"
  private val BUDGET_TOKEN: String = "budget:"
  private val PREVISION_TOKEN: String = "prevision:"
  private val DAMOCLES_TOKEN: String = "damocles:"
  private val OPERATION_TOKEN: String = "operation:"

  def readAccount(file: File): Account = {
    read(file).build()
  }

  def read(file: File): AccountBuilder = {
    val acc: AccountBuilder = new AccountBuilder
    val in: BufferedReader = new BufferedReader(new FileReader(file))
    try {
      var line: String = null
      while (null != {
        line = in.readLine
        line
      }) {
        val split: Array[String] = line.split(":")
        if (line.startsWith(INIT_TOKEN)) {
          check(INIT_TOKEN, split, 2)
          acc.setInitialAmount(Util.getBD(split(1)))
        }
        if (line.startsWith(BUDGET_TOKEN)) {
          check(BUDGET_TOKEN, split, 3)
          val bd: BigDecimal = Util.getBD(split(2))
          acc.getBudgets.put(split(1), bd)
        }
        if (line.startsWith(PREVISION_TOKEN)) {
          check(PREVISION_TOKEN, split, 4)
          val p: PrevisionBuilder = new PrevisionBuilder
          p.setCategory(split(1))
          p.setLabel(split(2))
          p.setAmount(Util.getBD(split(3)))
          acc.getPrevisions.add(p)
        }
        if (line.startsWith(DAMOCLES_TOKEN)) {
          check(DAMOCLES_TOKEN, split, 4)
          val dc: DamoclesBuilder = new DamoclesBuilder
          dc.setCategory(split(1))
          dc.setLabel(split(2))
          dc.setAmount(Util.getBD(split(3)))
          acc.getDamocleses.add(dc)
        }
        if (line.startsWith(OPERATION_TOKEN)) {
          check(OPERATION_TOKEN, split, 5)
          val o: OperationBuilder = new OperationBuilder
          o.setCategory(split(1))
          o.setLabel(split(2))
          o.setAmount(Util.getBD(split(3)))
          o.setDate(split(4))
          acc.getOperations.add(o)
        }
      }
    } finally {
      in.close()
    }
    initZeroBudgets(acc)
    acc.sortOperations()
    acc
  }

  def write(acc: AccountBuilder, file: File) {
    acc.sortOperations()
    initZeroBudgets(acc)
    val pw: PrintWriter = new PrintWriter(file)
    try {
      pw.println(INIT_TOKEN + acc.getInitialAmount.toString)
      import scala.collection.JavaConversions._
      for (b <- acc.getBudgets.entrySet) {
        pw.println(BUDGET_TOKEN + b.getKey + ":" + b.getValue)
      }
      import scala.collection.JavaConversions._
      for (p <- acc.getPrevisions) {
        pw.println(PREVISION_TOKEN + p.getCategory + ":" + p.getLabel + ":" + p.getAmount)
      }
      import scala.collection.JavaConversions._
      for (d <- acc.getDamocleses) {
        pw.println(DAMOCLES_TOKEN + d.getCategory + ":" + d.getLabel + ":" + d.getAmount)
      }
      import scala.collection.JavaConversions._
      for (o <- acc.getOperations) {
        pw.println(OPERATION_TOKEN + o.getCategory + ":" + o.getLabel + ":" + o.getAmount + ":" + o.getDate)
      }
    } finally {
      pw.close()
    }
  }

  private def initZeroBudgets(acc: AccountBuilder) {
    import scala.collection.JavaConversions._
    for (p <- acc.getPrevisions) {
      if (!acc.getBudgets.containsKey(p.getCategory)) {
        acc.getBudgets.put(p.getCategory, BigDecimal.valueOf(0))
      }
    }
    import scala.collection.JavaConversions._
    for (d <- acc.getDamocleses) {
      if (!acc.getBudgets.containsKey(d.getCategory)) {
        acc.getBudgets.put(d.getCategory, BigDecimal.valueOf(0))
      }
    }
    import scala.collection.JavaConversions._
    for (o <- acc.getOperations) {
      if (!acc.getBudgets.containsKey(o.getCategory)) {
        acc.getBudgets.put(o.getCategory, BigDecimal.valueOf(0))
      }
    }
  }

  private def check(initToken: String, split: Array[String], expected: Int) {
    if (split.length != expected) {
      throw new RuntimeException(initToken + " expected " + expected + " values")
    }
  }
}


