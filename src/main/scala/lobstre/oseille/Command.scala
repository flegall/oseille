package lobstre.oseille

import java.io.IOException
import java.text.ParseException
import java.util.{Collection => JCollection, List => JList}

trait Command {
  def accepts(arguments: JList[String], errors: JCollection[String], fileName: String)

  @throws(classOf[IOException])
  @throws(classOf[ParseException])
  def execute(fileName: String, arguments: JList[String])
}
