import sbt._

object Dependencies {

  lazy val playDependencies: Seq[ModuleID] = {
    Seq(
      "com.softwaremill.macwire" %% "macros" % "2.5.9" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
    )
  }

  lazy val zioDependencies: Seq[ModuleID] = {
    Seq(
      "dev.zio" %% "zio" % "2.1.1",
      "dev.zio" %% "zio-logging" % "2.2.4",
      "dev.zio" %% "zio-logging-slf4j" % "2.2.4",
      "ch.qos.logback" % "logback-classic" % "1.5.6",
      "dev.zio" %% "zio-json" % "0.6.2",
      "io.d11" %% "zhttp" % "2.0.0-RC11",
      "dev.zio" %% "zio-test" % "2.1.1" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.1" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.1.1" % Test
    )
  }

  lazy val catsEffectDependencies: Seq[ModuleID] = {
    val http4sVersion = "0.23.16"
    Seq(
      "org.http4s" %% "http4s-blaze-server" % http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion
    )
  }

  lazy val logstashDependencies: Seq[ModuleID] = {
    Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.17.1",
      "net.logstash.logback" % "logstash-logback-encoder" % "7.4"
    )
  }

  lazy val kamonDependencies: Seq[ModuleID] = {
    val kamonVersion = "2.7.3"
    Seq(
      "io.kamon" %% "kamon-bundle" % kamonVersion,
      "io.kamon" %% "kamon-prometheus" % kamonVersion,
      "io.kamon" %% "kamon-jaeger" % kamonVersion
    )
  }

  lazy val JavaInstrumentVersion = "2.4.0"
  lazy val OpenTelemetryVersion = "1.39.0"

  lazy val openTelemetryDependencies: Seq[ModuleID] = {
    Seq(
      "io.opentelemetry" % "opentelemetry-api" % OpenTelemetryVersion,
      "io.opentelemetry.instrumentation" % "opentelemetry-logback-appender-1.0" % s"$JavaInstrumentVersion-alpha" % "runtime"
    )
  }

  lazy val otel4sDependencies: Seq[ModuleID] = Seq(
    "org.typelevel" %% "otel4s-oteljava" % "0.8.0",
    "io.opentelemetry" % "opentelemetry-exporter-otlp" % OpenTelemetryVersion % Runtime,
    "io.opentelemetry.instrumentation" % "opentelemetry-logback-appender-1.0" % s"$JavaInstrumentVersion-alpha" % "runtime"
    // "io.opentelemetry" % "opentelemetry-sdk-extension-autoconfigure" % OpenTelemetryVersion % Runtime
  )

  lazy val gatling = {
    val version = "3.11.3"
    Seq(
      "io.gatling" % "gatling-core" % version,
      "io.gatling" % "gatling-test-framework" % version,
      "io.gatling.highcharts" % "gatling-charts-highcharts" % version
    )
  }

  lazy val kamonAgent: ModuleID = "io.kamon" % "kanela-agent" % "1.0.18"

  lazy val openTelemetryAgent: ModuleID =
    "io.opentelemetry.javaagent" % "opentelemetry-javaagent" % JavaInstrumentVersion
}
