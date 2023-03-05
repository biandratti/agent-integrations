Global / dependencyCheckFormats := Seq("HTML", "JSON")

scalaVersion := "2.13.10"
name := """play-monitoring"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val commonSettings = Seq(
  organization := "biandratti",
  scalaVersion := "2.13.10",
  scalacOptions += "-Xsource:3",
  // ThisBuild / scapegoatVersion := "1.4.17",
  wartremoverErrors ++= Warts.unsafe.diff(Seq(Wart.Any)),
  coverageFailOnMinimum := true,
  coverageMinimumStmtTotal := 80,
  coverageMinimumBranchTotal := 80,
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalafixOnCompile := true
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .dependsOn(app1)
  .aggregate(app1)

lazy val app1 = project
/*lazy val app1 =
  project
    .in(file("app1"))
    .enablePlugins(PlayScala)
    .settings(commonSettings)
    .settings(
      name := "app1",
      libraryDependencies ++= Dependencies.appDependencies
    )*/

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
