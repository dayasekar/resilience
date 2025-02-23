package org.daya.r4j.cb;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.daya.r4j.exception.ResilienceCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Predicate;

@Component
public class CBScaff {

    private static final Logger LOG = LoggerFactory.getLogger(CBScaff.class);

    public void demo1_max2Attempts() throws Throwable {
        CBService CBService = new CBService();

        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom().
                failureRateThreshold(100).
//                recordExceptions(ResilienceCheckedException.class).
                waitDurationInOpenState(Duration.ofSeconds(60)).
                build();

        CircuitBreakerRegistry cbRegistry = CircuitBreakerRegistry.of(cbConfig);

        CircuitBreaker cb = cbRegistry.circuitBreaker("demo1");

        callMethod(cb, CBService);
    }

    public void demo2_retryOnRuntimeException() throws Throwable {
        CBService CBService = new CBService();

        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom().
                failureRateThreshold(20).
                build();

        CircuitBreakerRegistry cbRegistry = CircuitBreakerRegistry.of(cbConfig);

        CircuitBreaker cb = cbRegistry.circuitBreaker("demo2");

        callMethod(cb, CBService);
    }

    public void demo3_retryOnTrue() throws Throwable {
        CBService CBService = new CBService();

        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom().
                failureRateThreshold(20).
                build();

        CircuitBreakerRegistry cbRegistry = CircuitBreakerRegistry.of(cbConfig);

        CircuitBreaker cb = cbRegistry.circuitBreaker("demo3");

        callMethod(cb, CBService);
    }

    private static void callMethod(CircuitBreaker cb, CBService CBService) throws Throwable {
        for (int i = 1; i < 10; i++) {
            CheckedFunction<Integer, Boolean> func = CircuitBreaker.decorateCheckedFunction(cb, CBService::validateBid);
            boolean response = func.apply(i);
            LOG.info("Response from Retry Service {}", response);
        }
    }
}
