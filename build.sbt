import com.typesafe.sbt.packager.docker.{
  DockerChmodType,
  DockerPermissionStrategy
}

Global / dependencyCheckFormats := Seq("HTML", "JSON")

lazy val scalaV = "2.13.12"
scalaVersion := scalaV
name := """agents"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val commonSettings = Seq(
  organization := "biandratti",
  scalaVersion := scalaV,
  scalacOptions += "-Xsource:3",
  ThisBuild / scapegoatVersion := "2.1.3",
  // wartremoverErrors ++= Warts.unsafe.diff(Seq(Wart.Any)),
  // wartremoverExcluded ++= (Compile / routes).value,
  coverageFailOnMinimum := true,
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
    .enablePlugins(
      PlayScala,
      AshScriptPlugin,
      JavaAppPackaging,
      DockerPlugin,
      JavaAgent
    )
    .settings(commonSettings)
    .settings(
      name := "app1",
      libraryDependencies ++= Dependencies.playDependencies
        ++ Dependencies.logstashDependencies
        ++ Dependencies.kamonDependencies
        ++ Seq(ws),
      javaAgents += Dependencies.kamonAgent,
      dockerExposedPorts := Seq(9001),
      dockerBaseImage := "openjdk:8-jre-alpine",
      dockerUpdateLatest := true,
      dockerChmodType := DockerChmodType.UserGroupWriteExecute,
      dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
    )

lazy val app2 =
  project
    .in(file("app2"))
    .enablePlugins(
      PlayScala,
      AshScriptPlugin,
      JavaAppPackaging,
      DockerPlugin,
      JavaAgent
    )
    .settings(commonSettings)
    .settings(
      name := "app2",
      libraryDependencies ++= Dependencies.playDependencies
        ++ Dependencies.logstashDependencies
        ++ Dependencies.kamonDependencies,
      javaAgents += Dependencies.kamonAgent,
      dockerExposedPorts := Seq(9002),
      dockerBaseImage := "openjdk:8-jre-alpine",
      dockerUpdateLatest := true,
      dockerChmodType := DockerChmodType.UserGroupWriteExecute,
      dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
    )

lazy val app3 =
  project
    .in(file("app3"))
    .enablePlugins(
      PlayScala,
      AshScriptPlugin,
      JavaAppPackaging,
      DockerPlugin,
      JavaAgent
    )
    .settings(commonSettings)
    .settings(
      name := "app3",
      libraryDependencies ++= Dependencies.playDependencies
        ++ Dependencies.logstashDependencies
        ++ Dependencies.openTelemetryDependencies
        ++ Seq(ws),
      javaAgents += Dependencies.openTelemetryAgent,
      javaOptions += "-Dotel.javaagent.debug=true", // Debug OpenTelemetry Java agent
      dockerExposedPorts := Seq(9003),
      dockerBaseImage := "openjdk:8-jre-alpine",
      dockerUpdateLatest := true,
      dockerChmodType := DockerChmodType.UserGroupWriteExecute,
      dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
    )

lazy val app4 =
  project
    .in(file("app4"))
    .enablePlugins(
      PlayScala,
      AshScriptPlugin,
      JavaAppPackaging,
      DockerPlugin,
      JavaAgent
    )
    .settings(commonSettings)
    .settings(
      name := "app4",
      libraryDependencies ++= Dependencies.playDependencies
        ++ Dependencies.logstashDependencies
        ++ Dependencies.openTelemetryDependencies,
      javaAgents += Dependencies.openTelemetryAgent,
      javaOptions += "-Dotel.javaagent.debug=true", // Debug OpenTelemetry Java agent
      dockerExposedPorts := Seq(9004),
      dockerBaseImage := "openjdk:8-jre-alpine",
      dockerUpdateLatest := true,
      dockerChmodType := DockerChmodType.UserGroupWriteExecute,
      dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
    )

lazy val app5 = project
  .in(file("app5"))
  .enablePlugins(
    AshScriptPlugin,
    JavaAppPackaging,
    DockerPlugin,
    JavaAgent
  )
  .settings(commonSettings)
  .settings(
    name := "app5",
    libraryDependencies ++= Dependencies.zioDependencies
      ++ Dependencies.openTelemetryDependencies,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    javaOptions += "-Dotel.javaagent.debug=true", // Debug OpenTelemetry Java agent
    javaAgents += Dependencies.openTelemetryAgent,
    dockerExposedPorts := Seq(9005),
    dockerBaseImage := "openjdk:8-jre-alpine",
    dockerUpdateLatest := true,
    dockerChmodType := DockerChmodType.UserGroupWriteExecute,
    dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
  )

lazy val gatling = project
  .in(file("gatling"))
  .enablePlugins(GatlingPlugin)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Dependencies.gatling
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
