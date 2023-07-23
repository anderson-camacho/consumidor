name := """consumidor"""
organization := "com.camacho"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += ws
// https://mvnrepository.com/artifact/com.github.plokhotnyuk.jsoniter-scala/jsoniter-scala-core
libraryDependencies += "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % "2.10.0"
libraryDependencies += "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % "2.10.0"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0"

// Agregar la resolución de dependencias de sonatype (reemplaza el resolvers existente si es necesario)
resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.camacho.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.camacho.binders._"
