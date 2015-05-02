import scala.language.postfixOps

version := "1.0.0"

organization := "lobstre"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.threeten" % "threetenbp" % "1.2"
)

jacoco.settings

val deployTask = TaskKey[Unit]("deploy", "Copies assembly jar to install location")

deployTask <<= assembly map { (asm) =>
  val local = asm.getPath
  val envPath = System.getenv("OSEILLE_INSTALL_PATH")
  val remote = if (envPath != null) envPath else "/tmp/"

  println(s"Copying: $local -> $remote")

  Seq("cp", local, remote + "/harpagon.jar") !!;
  Seq("cp", "harpagon", remote + "/harpagon") !!;
  Seq("chmod", "+x", remote + "/harpagon") !!;
  Seq("cp", "harpagon.bat", remote + "/harpagon.bat") !!
}