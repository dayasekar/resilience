package org.daya.r4j.retry;

import org.junit.jupiter.api.Test;

class RetryScaffTest {

    private RetryScaff testObject = new RetryScaff();

    @Test
    void demo1_max2Attempts() throws Throwable {
        testObject.demo1_max2Attempts();
    }

    @Test
    void demo2_retryOnRuntimeException() throws Throwable {
        testObject.demo2_retryOnRuntimeException();
    }

    @Test
    void demo3_retryOnTrue() throws Throwable {
        testObject.demo3_retryOnTrue();
    }
}