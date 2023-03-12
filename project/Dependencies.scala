import sbt._

object Dependencies {

  lazy val playDependencies: Seq[ModuleID] = {
    Seq(
      "com.softwaremill.macwire" %% "macros" % "2.5.8" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  }

  lazy val kamonDependencies: Seq[ModuleID] = {
    val kamonVersion = "2.6.0"
    Seq(
      "io.kamon" %% "kamon-bundle" % kamonVersion,
      "io.kamon" %% "kamon-apm-reporter" % kamonVersion
      // "io.kamon" %% "kamon-play" % kamonVersion
    )
  }
}
