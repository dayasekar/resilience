package org.daya.r4j.boot.controller;

import org.daya.r4j.retry.RetryScaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetryEntry {

    @Autowired
    private RetryScaff retryScaff;

    @RequestMapping("/retry/demo1_max2Attempts")
    public void demo1_max2Attempts() throws Throwable {
        retryScaff.demo1_max2Attempts();
    }

    @RequestMapping("/retry/demo2_retryOnRuntimeException")
    public void demo2_retryOnRuntimeException() throws Throwable {
        retryScaff.demo2_retryOnRuntimeException();
    }
}
