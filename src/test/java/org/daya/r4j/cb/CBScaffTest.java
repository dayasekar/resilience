package org.daya.r4j.cb;

import org.junit.jupiter.api.Test;

class CBScaffTest {

    private CBScaff testObject = new CBScaff();

    @Test
    void demo1_max2Attempts() throws Throwable {
        testObject.demo1_max2Attempts();
    }

    @Test
    void demo2_retryOnRuntimeException() {
    }

    @Test
    void demo3_retryOnTrue() {
    }
}