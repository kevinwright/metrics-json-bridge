import sbt._
import Keys._
import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin.{ Dist, outputDirectory, distJvmOptions}
import spray.revolver.RevolverPlugin._
 
object MasterBuild extends Build {
  val Organization = "net.thecoda"
  val Version      = "0-SNAPSHOT"
  val ScalaVersion = "2.11.1"
 
  lazy val Root = Project(
    id = "metrics-json-bridge",
    base = file("."),
    settings = defaultSettings ++ AkkaKernelPlugin.distSettings ++ Revolver.settings ++ Seq(
      resolvers += "spray repo" at "http://repo.spray.io",
      libraryDependencies ++= Dependencies.all,
      distJvmOptions in Dist := "-Xms256M -Xmx1024M",
      outputDirectory in Dist := file("target/dist")
    )
  )
 
  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := Organization,
    version      := Version,
    scalaVersion := ScalaVersion,
    crossPaths   := false,
    organizationName := "@thecoda",
    organizationHomepage := Some(url("http://www.thecoda.net"))
  )
  
  lazy val defaultSettings = buildSettings ++ Seq(
    // compile options
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked"),
    javacOptions  ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")
 
  )
}
 
object Dependencies {
  import Dependency._
 
  val all = Seq(
    akkaKernel, akkaSlf4j, logback, sprayClient, xml
  )
}
 
object Dependency {
  // Versions
  object V {
    val Akka      = "2.3.4"
    val Logback   = "1.0.0"
    val Spray     = "1.3.1"
    val Xml       = "1.0.2"
  }
 
  val akkaKernel  = "com.typesafe.akka"      %% "akka-kernel"     % V.Akka
  val akkaSlf4j   = "com.typesafe.akka"      %% "akka-slf4j"      % V.Akka
  val logback     = "ch.qos.logback"         %  "logback-classic" % V.Logback
  val sprayClient = "io.spray"               %% "spray-can"       % V.Spray
  val xml         = "org.scala-lang.modules" %% "scala-xml"       % V.Xml
}
