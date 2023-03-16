addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.19")
addSbtPlugin("io.kamon" % "sbt-kanela-runner-play-2.8" % "2.0.14") //Kamon

addSbtPlugin("com.lightbend.sbt" % "sbt-javaagent" % "0.1.6") //OpenTelemetry

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.0.11")
addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.1")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.0")

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.4")

addSbtPlugin("net.vonbuchholtz" % "sbt-dependency-check" % "5.1.0")
