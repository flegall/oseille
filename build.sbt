version := "1.0.0"

organization := "lobstre"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
  "org.threeten" % "threetenbp" % "1.2"
)

jacoco.settings

