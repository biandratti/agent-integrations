//play framework
addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.5")

//Agents (Kanela and Opentelemetry)
addSbtPlugin("com.github.sbt" % "sbt-javaagent" % "0.1.8")

//scala-fmt
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.5.2")

//lints
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.2.1")
//addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.2.6")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.13.0")

//coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.2.0")

//check vulnerability
addSbtPlugin("net.vonbuchholtz" % "sbt-dependency-check" % "5.1.0")

//gatling
addSbtPlugin("io.gatling" % "gatling-sbt" % "4.10.0")

//https://github.com/scala/bug/issues/12632
ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)
