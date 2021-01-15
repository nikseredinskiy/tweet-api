name := "tweet-api"

version := "0.1"

scalaVersion := "2.12.10"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "ch.megard" %% "akka-http-cors" % "1.1.1"
)

libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.3.7" % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "macrosakka" % "2.3.7" % "provided"
libraryDependencies += "com.softwaremill.macwire" %% "util" % "2.3.7"
libraryDependencies += "com.softwaremill.macwire" %% "proxy" % "2.3.7"

enablePlugins(JavaAppPackaging)

dockerExposedPorts in Docker := Seq(8080)