package tw.com.wd.opentelemetry;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

public class ExampleConfiguration {
    /**
     * Initializes the OpenTelemetry SDK with a logging span exporter and the W3C Trace Context
     * propagator.
     *
     * @return A ready-to-use {@link OpenTelemetry} instance.
     */
    public static OpenTelemetry initOpenTelemetry() {
        SdkTracerProvider sdkTracerProvider =
                SdkTracerProvider.builder()
                        .addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create()))
                        .build();

        OpenTelemetrySdk sdk =
                OpenTelemetrySdk.builder()
                        .setTracerProvider(sdkTracerProvider)
                        .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                        .build();

        Runtime.getRuntime().addShutdownHook(new Thread(sdkTracerProvider::close));
        return sdk;
    }
}