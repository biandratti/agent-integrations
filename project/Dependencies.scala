import sbt._

object Dependencies {

  lazy val playDependencies: Seq[ModuleID] = {
    Seq(
      "com.softwaremill.macwire" %% "macros" % "2.6.2" % "provided",
      "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
    )
  }

  lazy val zioDependencies: Seq[ModuleID] = {
    Seq(
      "dev.zio" %% "zio" % "2.1.1",
      "dev.zio" %% "zio-logging" % "2.2.4",
      "dev.zio" %% "zio-logging-slf4j" % "2.2.4",
      "ch.qos.logback" % "logback-classic" % "1.5.8",
      "dev.zio" %% "zio-json" % "0.6.2",
      "io.d11" %% "zhttp" % "2.0.0-RC11",
      "dev.zio" %% "zio-test" % "2.1.1" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.1.1" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.1.1" % Test
    )
  }

  lazy val logstashDependencies: Seq[ModuleID] = {
    Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.17.2",
      "net.logstash.logback" % "logstash-logback-encoder" % "8.0"
    )
  }

  lazy val kamonDependencies: Seq[ModuleID] = {
    val kamonVersion = "2.7.4"
    Seq(
      "io.kamon" %% "kamon-bundle" % kamonVersion,
      "io.kamon" %% "kamon-prometheus" % kamonVersion,
      "io.kamon" %% "kamon-jaeger" % kamonVersion
    )
  }

  lazy val JavaInstrumentVersion = "2.8.0"

  lazy val openTelemetryDependencies = {
    val version = "1.42.1"
    Seq(
      "io.opentelemetry" % "opentelemetry-api" % version,
      "io.opentelemetry.instrumentation" % "opentelemetry-logback-appender-1.0" % s"$JavaInstrumentVersion-alpha" % "runtime"
    )
  }

  lazy val gatling = {
    val version = "3.12.0"
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
