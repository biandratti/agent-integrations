import io.opentelemetry.api.GlobalOpenTelemetry
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.logs.GlobalLoggerProvider
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.logs.SdkLoggerProvider
import io.opentelemetry.sdk.logs.`export`.BatchLogRecordProcessor
import io.opentelemetry.sdk.resources.Resource
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.`export`.BatchSpanProcessor
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes

import java.util.concurrent.TimeUnit

object OpenTelemetryLoader {

  def apply(
      jaegerEndpoint: String,
      logRecordEndpoint: String
  ): OpenTelemetrySdk = {
    // Export traces to Jaeger over OTLP
    val jaegerOtlpExporter: OtlpGrpcSpanExporter =
      OtlpGrpcSpanExporter
        .builder()
        .setEndpoint(jaegerEndpoint)
        .setTimeout(30, TimeUnit.SECONDS)
        .build()
    val serviceNameResource: Resource = Resource.create(
      Attributes.of(ResourceAttributes.SERVICE_NAME, "app3")
    )

    // Set to process the spans by the Jaeger Exporter
    val tracerProvider: SdkTracerProvider = SdkTracerProvider
      .builder()
      .addSpanProcessor(BatchSpanProcessor.builder(jaegerOtlpExporter).build())
      .setResource(Resource.getDefault().merge(serviceNameResource))
      .build()

    val sdk: OpenTelemetrySdk = OpenTelemetrySdk
      .builder()
      .setTracerProvider(
        tracerProvider
        // SdkTracerProvider.builder().setSampler(Sampler.alwaysOn()).build()
      )
      .setLoggerProvider(
        SdkLoggerProvider
          .builder()
          .setResource(
            Resource
              .getDefault()
              .toBuilder()
              .put(ResourceAttributes.SERVICE_NAME, "app3")
              .build()
          )
          .addLogRecordProcessor(
            BatchLogRecordProcessor
              .builder(
                OtlpGrpcLogRecordExporter
                  .builder()
                  .setEndpoint(logRecordEndpoint)
                  .build()
              )
              .build()
          )
          .build()
      )
      .build()
    GlobalOpenTelemetry.set(sdk)
    GlobalLoggerProvider.set(sdk.getSdkLoggerProvider())
    sdk
  }
}
