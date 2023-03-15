import sbt._

object Dependencies {

  lazy val playDependencies: Seq[ModuleID] = {
    Seq(
      "com.softwaremill.macwire" %% "macros" % "2.5.8" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  }

  lazy val logstashDependencies: Seq[ModuleID] = {
    Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.2",
      "net.logstash.logback" % "logstash-logback-encoder" % "7.3"
    )
  }

  lazy val kamonDependencies: Seq[ModuleID] = {
    val kamonVersion = "2.6.0"
    Seq(
      "io.kamon" %% "kamon-bundle" % kamonVersion,
      "io.kamon" %% "kamon-jaeger" % kamonVersion
    )
  }

  lazy val openTelemetryDependencies = {
    val version = "1.11.0"
    val alphaVersion = s"$version-alpha"
    Seq(
      "io.opentelemetry" % "opentelemetry-bom" % version pomOnly (),
      "io.opentelemetry" % "opentelemetry-api" % version,
      "io.opentelemetry" % "opentelemetry-sdk" % version,
      "io.opentelemetry" % "opentelemetry-exporter-jaeger" % version,
      "io.opentelemetry" % "opentelemetry-sdk-extension-autoconfigure" % alphaVersion,
      "io.opentelemetry" % "opentelemetry-exporter-prometheus" % alphaVersion,
      "io.opentelemetry" % "opentelemetry-exporter-zipkin" % version,
      "io.opentelemetry" % "opentelemetry-exporter-jaeger" % version,
      "io.opentelemetry" % "opentelemetry-exporter-otlp" % version,
      "io.opentelemetry.javaagent" % "opentelemetry-javaagent" % version % "runtime"
    )
  }
}
