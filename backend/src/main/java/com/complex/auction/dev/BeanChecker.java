package com.complex.auction.dev;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class BeanChecker { // Class used for debugging

    private static final Logger logger = LogManager.getLogger(BeanChecker.class);

    public BeanChecker(PasswordEncoder encoder) {
        logger.info("PasswordEncoder bean loaded: {}", encoder.getClass().getSimpleName());
    }
}
