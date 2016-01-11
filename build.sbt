name := """ga"""

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.7",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  //"com.typesafe.akka" %% "akka-testkit" % "2.3.5",
  "org.scalaz" %% "scalaz-core" % "7.1.5"
)
