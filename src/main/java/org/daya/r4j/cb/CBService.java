package org.daya.r4j.cb;

import org.daya.r4j.exception.ResilienceCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CBService {

    private static final Logger LOG = LoggerFactory.getLogger(CBService.class);

    public boolean validateBid(int bidAmount) throws Exception {
        LOG.info("Bid Amount {}", bidAmount);
        if (bidAmount > 2) {
            throw new ResilienceCheckedException();
        }
        return true;
    }
}
