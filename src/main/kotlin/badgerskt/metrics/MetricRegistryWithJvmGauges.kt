package badgerskt.metrics

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.jvm.CachedThreadStatesGaugeSet
import com.codahale.metrics.jvm.GarbageCollectorMetricSet
import com.codahale.metrics.jvm.MemoryUsageGaugeSet
import java.util.concurrent.TimeUnit

object MetricRegistryWithJvmGauges {
    fun metricRegistry(): MetricRegistry {
        val metricsRegistry = MetricRegistry()
        metricsRegistry.register("gc", GarbageCollectorMetricSet())
        metricsRegistry.register("threads", CachedThreadStatesGaugeSet(10, TimeUnit.SECONDS))
        metricsRegistry.register("memory",  MemoryUsageGaugeSet())
        return metricsRegistry
    }
}