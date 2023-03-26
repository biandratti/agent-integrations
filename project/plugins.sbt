//play framework
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.19")

//Agents (Kanela and Opentelemetry)
addSbtPlugin("com.lightbend.sbt" % "sbt-javaagent" % "0.1.6")

//scala-fmt
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.0")

//lints
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.0.11")
addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.1.1")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.4")

//coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.7")

//check vulnerability
addSbtPlugin("net.vonbuchholtz" % "sbt-dependency-check" % "5.1.0")
