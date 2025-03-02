package org.daya.r4j.bh;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.core.functions.CheckedFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BHScaff {

    private static final Logger LOG = LoggerFactory.getLogger(BHScaff.class);

    public void demo1_max2Attempts() throws Throwable {
        BHService bhService = new BHService();

        BulkheadConfig bhConfig = BulkheadConfig.custom().
                maxConcurrentCalls(1).
                maxWaitDuration(Duration.ofSeconds(1)).
                build();

        BulkheadRegistry bhRegistry = BulkheadRegistry.of(bhConfig);

        Bulkhead bulkhead = bhRegistry.bulkhead("demo1");

        callMethod(bulkhead, bhService);
    }

    private static void callMethod(Bulkhead bulkhead, BHService bhService) throws Throwable {
        for (int i = 1; i < 10; i++) {
            CheckedFunction<Integer, Boolean> func = Bulkhead.decorateCheckedFunction(bulkhead, bhService::validateBid);
            boolean response = func.apply(i);
            LOG.info("Response from Bulkhead Service {}", response);
        }
    }
}
