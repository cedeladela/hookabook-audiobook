package cedeladela.hookabook.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class RatingCounterMetrics {

    private final Counter requestsCounter;

    public RatingCounterMetrics(MeterRegistry meterRegistry) {
        this.requestsCounter = Counter.builder("requests.total")
                .description("Total number of requests")
                .register(meterRegistry);
    }

    public void incrementRequestCount() {
        requestsCounter.increment();
    }
}
