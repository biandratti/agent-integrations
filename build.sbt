import com.typesafe.sbt.packager.docker.{
  Cmd,
  DockerChmodType,
  DockerPermissionStrategy
}

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
  // coverageMinimumStmtTotal := 80,
  // coverageMinimumBranchTotal := 80,
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
      Docker / maintainer := "maxibiandra@example.com",
      Docker / packageName := "app1",
      Docker / version := sys.env.getOrElse("BUILD_NUMBER", "0"),
      Docker / daemonUserUid := None,
      Docker / daemonUser := "daemon",
      dockerExposedPorts := Seq(9000),
      dockerBaseImage := "openjdk:8-jre-alpine",
      // dockerRepository := sys.env.get("ecr_repo"),
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
      Docker / maintainer := "maxibiandra@example.com",
      Docker / packageName := "app2",
      Docker / version := sys.env.getOrElse("BUILD_NUMBER", "0"),
      Docker / daemonUserUid := None,
      Docker / daemonUser := "daemon",
      dockerExposedPorts := Seq(9001),
      dockerBaseImage := "openjdk:8-jre-alpine",
      // dockerRepository := sys.env.get("ecr_repo"),
      dockerUpdateLatest := true,
      dockerChmodType := DockerChmodType.UserGroupWriteExecute,
      dockerPermissionStrategy := DockerPermissionStrategy.CopyChown
    )

lazy val app3 =
  project
    .in(file("app3"))
    .enablePlugins(PlayScala, JavaAppPackaging, DockerPlugin, JavaAgent)
    .settings(commonSettings)
    .settings(
      name := "app3",
      libraryDependencies ++= Dependencies.playDependencies ++ Dependencies.openTelemetryDependencies,
      javaAgents += Dependencies.openTelemetryAgent,
      javaOptions += "-Dotel.javaagent.debug=true", // Debug OpenTelemetry Java agent
      dockerAlias := dockerAlias.value.withTag(Some(version.value)),
      dockerUpdateLatest := true
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
