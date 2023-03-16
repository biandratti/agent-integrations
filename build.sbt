Global / dependencyCheckFormats := Seq("HTML", "JSON")

lazy val scalaV = "2.13.10"
scalaVersion := scalaV
name := """play-monitoring"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val commonSettings = Seq(
  organization := "biandratti",
  scalaVersion := scalaV,
  scalacOptions += "-Xsource:3",
  ThisBuild / scapegoatVersion := "2.1.1",
  // wartremoverErrors ++= Warts.unsafe.diff(Seq(Wart.Any)),
  // wartremoverExcluded ++= (Compile / routes).value,
  coverageFailOnMinimum := true,
  coverageMinimumStmtTotal := 80,
  coverageMinimumBranchTotal := 80,
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalafixOnCompile := true,
  scalacOptions ++= Seq(
    "-Wunused"
    /*"-feature",
    "-deprecation",
    "-Xfatal-warnings"*/
  )
)

lazy val app1 =
  project
    .in(file("app1"))
    .enablePlugins(PlayScala, JavaAgent)
    .settings(commonSettings)
    .settings(
      name := "app1",
      libraryDependencies ++= Dependencies.playDependencies ++ Dependencies.kamonDependencies ++ Seq(
        ws
      )
    )

lazy val app2 =
  project
    .in(file("app2"))
    .enablePlugins(PlayScala, JavaAgent)
    .settings(commonSettings)
    .settings(
      name := "app2",
      libraryDependencies ++= Dependencies.playDependencies ++ Dependencies.kamonDependencies
    )

lazy val app3 =
  project
    .in(file("app3"))
    .enablePlugins(PlayScala, JavaAgent)
    .settings(commonSettings)
    .settings(
      name := "app3",
      libraryDependencies ++= Dependencies.playDependencies ++ Dependencies.openTelemetryDependencies,
      javaAgents += "io.opentelemetry.javaagent" % "opentelemetry-javaagent" % "1.13.0",
      javaOptions += "-Dotel.javaagent.debug=true" // Debug OpenTelemetry Java agent
    )

addCommandAlias("checkFormat", ";scalafmtSbtCheck ;scalafmtCheckAll")
addCommandAlias("scapegoatLint", ";compile ;scapegoat")
addCommandAlias("scalafixLint", ";compile ;scalafix")
addCommandAlias(
  "testCoverage",
  ";coverage ;test ;coverageAggregate; coverageReport"
)
addCommandAlias(
  "verify",
  ";checkFormat ;scapegoatLint ;scalafixLint ;testCoverage; dependencyCheck"
)
