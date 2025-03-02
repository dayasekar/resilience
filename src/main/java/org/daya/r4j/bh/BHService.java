package org.daya.r4j.bh;

import org.daya.r4j.retry.RetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BHService {

    private static final Logger LOG = LoggerFactory.getLogger(RetryService.class);

    public boolean validateBid(int bidAmount) throws Exception {
        LOG.info("Bid Amount {}", bidAmount);
        if (bidAmount % 2 == 0) {
            return false;
        }
        return true;
    }
}
