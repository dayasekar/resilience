package org.daya.r4j.retry;

import org.daya.r4j.exception.ResilienceCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RetryService {

    private static final Logger LOG = LoggerFactory.getLogger(RetryService.class);

    public boolean validateBid(int bidAmount) throws Exception {
        LOG.info("Bid Amount {}", bidAmount);
        if (bidAmount % 2 == 0) {
            throw new ResilienceCheckedException();
        }
        return true;
    }
}
