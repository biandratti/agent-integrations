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
    "-feature",
    "-deprecation",
    "-Xfatal-warnings"
  )
)

lazy val app1 =
  project
    .in(file("app1"))
    .enablePlugins(PlayScala, JavaAgent)
    .settings(commonSettings)
    .settings(
      name := "app1",
      // libraryDependencies ++= Dependencies.playDependencies ++ Dependencies.kamonDependencies
      libraryDependencies ++= Seq(
        "com.softwaremill.macwire" %% "macros" % "2.5.8" % "provided",
        "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
      )
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
