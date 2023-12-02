package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class NumberSequentialService extends SequentialService {
    @Override
    protected String getType() {
        return "Number";
    }

    @Override
    public String getSequence() {
        return "01234567890";
    }
}
