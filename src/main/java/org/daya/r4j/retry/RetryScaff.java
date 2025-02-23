package org.daya.r4j.retry;

import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.daya.r4j.exception.ResilienceCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class RetryScaff {

    private static final Logger LOG = LoggerFactory.getLogger(RetryScaff.class);

    public void demo1_max2Attempts() throws Throwable {
        RetryService retryService = new RetryService();

        RetryConfig retryConfig = RetryConfig.custom().
                maxAttempts(2).
                build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);

        Retry retry = retryRegistry.retry("demo1");

        callMethod(retry, retryService);
    }

    public void demo2_retryOnRuntimeException() throws Throwable {
        RetryService retryService = new RetryService();

        RetryConfig retryConfig = RetryConfig.custom().
                retryExceptions(ResilienceCheckedException.class).
                maxAttempts(4).
                build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);

        Retry retry = retryRegistry.retry("demo2");

        callMethod(retry, retryService);
    }

    public void demo3_retryOnTrue() throws Throwable {
        RetryService retryService = new RetryService();

        RetryConfig retryConfig = RetryConfig.custom().
                retryOnResult(Predicate.isEqual(Boolean.TRUE)).
                maxAttempts(4).
                build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);

        Retry retry = retryRegistry.retry("demo3");

        callMethod(retry, retryService);
    }

    private static void callMethod(Retry retry, RetryService retryService) throws Throwable {
        for (int i = 1; i < 10; i++) {
            CheckedFunction<Integer, Boolean> func = Retry.decorateCheckedFunction(retry, retryService::validateBid);
            boolean response = func.apply(i);
            LOG.info("Response from Retry Service {}", response);
        }
    }
}
