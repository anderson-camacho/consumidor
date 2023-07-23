name := """consumidor"""
organization := "com.camacho"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  name := "consumidor",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %% "requests" % "0.8.0",
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % "0.55.4" % Compile,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "0.55.4" % Provided,
    // https://mvnrepository.com/artifact/org.scalatest/scalatest
    "org.scalatest" %% "scalatest" % "3.3.0-SNAP4"
  )
)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += ws

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.camacho.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.camacho.binders._"
