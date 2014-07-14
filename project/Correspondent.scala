import sbt._
import sbt.Keys._

import play._
import sbtbuildinfo.Plugin._

trait Correspondent {

  val appVersion = "1.0-SNAPSHOT"

  val commonDependencies = Seq(
    PlayImport.ws
  )

  def commonSettings = Seq(
    organization := "to-do",
    version := appVersion,
    scalaVersion := "2.10.4",
   // resolvers += "todo",
    libraryDependencies ++= commonDependencies,
    parallelExecution in Global := false
  )

  def app(name: String) = Project(name, file(name)).enablePlugins(PlayScala)
    .settings(commonSettings: _*)
}

object Correspondent extends Build with Correspondent {
  val frontend = app("frontend").settings(addCommandAlias("devrun", "run -Dconfig.resource=dev.conf 9100"): _*)

  val root = Project("root", base=file(".")).aggregate(frontend)
}

