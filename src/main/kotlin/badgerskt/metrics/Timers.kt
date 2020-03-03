package badgerskt.metrics

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.Timer
import java.time.Duration
import java.util.concurrent.TimeUnit

class Timers(registry: MetricRegistry) {
    val helloWorld: Timer = registry.timer("hello-world")
    val allBadgers: Timer = registry.timer("all-badgers")
    val individualBadger: Timer = registry.timer("individual-badger")

    companion object {
        // TODO if using arrow fx
        //  fun timeIO<A>(timer: Timer, io: IO<A>)

        fun <A> time(timer: Timer, f: () -> A): A {
            val start = System.nanoTime()
            val result = f.invoke()
            val end = System.nanoTime()
            val t = Duration.ofNanos(end - start)
            timer.update(t.toNanos(), TimeUnit.NANOSECONDS)
            return result
        }
    }

}