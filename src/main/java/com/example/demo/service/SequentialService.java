package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SequentialService {

    public final static int BASE_CALC_SEQUENTIAL = 3;

    public int countSequentialItems(String password) {
        int consecutiveCount = 0;
        for (int i = 0; i < getSequence().length() - 2; i++) {
            String sequence = getSequence().substring(i, i + 3);
            String reverseSequence = new StringBuilder(sequence).reverse().toString();

            if (password.toLowerCase().contains(sequence) || password.toLowerCase().contains(reverseSequence)) {
                consecutiveCount++;

            }

        }
        consecutiveCount *= BASE_CALC_SEQUENTIAL;
        log.info("Sequential {} (3+): -{}", getType(), consecutiveCount);
        return consecutiveCount;
    }

    protected abstract String getType();

    public abstract String getSequence();
}
