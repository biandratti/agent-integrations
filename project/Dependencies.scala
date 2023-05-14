import sbt._

object Dependencies {

  lazy val playDependencies: Seq[ModuleID] = {
    Seq(
      "com.softwaremill.macwire" %% "macros" % "2.5.8" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  }

  lazy val zioDependencies: Seq[ModuleID] = {
    Seq(
      "dev.zio" %% "zio" % "2.0.0-RC6",
      "dev.zio" %% "zio-logging" % "2.0.0-RC10",
      "dev.zio" %% "zio-logging-slf4j" % "2.0.0-RC10",
      "ch.qos.logback" % "logback-classic" % "1.4.7",
      "dev.zio" %% "zio-json" % "0.3.0-RC8",
      "io.d11" %% "zhttp" % "2.0.0-RC9",
      "dev.zio" %% "zio-test" % "2.0.10" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.10" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.0.10" % Test
    )
  }

  lazy val logstashDependencies: Seq[ModuleID] = {
    Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.0",
      "net.logstash.logback" % "logstash-logback-encoder" % "7.3"
    )
  }

  lazy val kamonDependencies: Seq[ModuleID] = {
    val kamonVersion = "2.6.0"
    Seq(
      "io.kamon" %% "kamon-bundle" % kamonVersion,
      "io.kamon" %% "kamon-prometheus" % kamonVersion,
      "io.kamon" %% "kamon-jaeger" % kamonVersion
    )
  }

  /*lazy val openTelemetryDependencies = {
    val version = "1.24.0"
    val alphaVersion = s"$version-alpha"
    Seq(
      "io.opentelemetry" % "opentelemetry-bom" % version pomOnly (),
      "io.opentelemetry" % "opentelemetry-api" % version,
      "io.opentelemetry" % "opentelemetry-sdk" % version,
      "io.opentelemetry.instrumentation" % "opentelemetry-logback-appender-1.0" % alphaVersion % "runtime",
      "io.opentelemetry" % "opentelemetry-exporter-jaeger" % version,
      "io.opentelemetry" % "opentelemetry-sdk-extension-autoconfigure" % alphaVersion,
      "io.opentelemetry" % "opentelemetry-exporter-prometheus" % alphaVersion,
      // "io.opentelemetry" % "opentelemetry-exporter-zipkin" % version,
      "io.opentelemetry" % "opentelemetry-exporter-jaeger" % version,
      "io.opentelemetry" % "opentelemetry-exporter-otlp" % version,
      "io.opentelemetry.javaagent" % "opentelemetry-javaagent" % version % "runtime"
    )
  }*/

  lazy val openTelemetryDependencies = {
    val version = "1.25.0"
    val alphaVersion = s"$version-alpha"
    Seq(
      "io.opentelemetry" % "opentelemetry-api" % version,
      "io.opentelemetry.instrumentation" % "opentelemetry-logback-appender-1.0" % alphaVersion % "runtime"
    )
  }

  lazy val gatling = {
    val version = "3.9.5"
    Seq(
      "io.gatling" % "gatling-core" % version,
      "io.gatling" % "gatling-test-framework" % version,
      "io.gatling.highcharts" % "gatling-charts-highcharts" % version
    )
  }

  lazy val kamonAgent: ModuleID = "io.kamon" % "kanela-agent" % "1.0.17"

  lazy val openTelemetryAgent: ModuleID =
    "io.opentelemetry.javaagent" % "opentelemetry-javaagent" % "1.25.1"
}
