package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class SymbolSequentialService extends SequentialService {
    @Override
    protected String getType() {
        return "Symbol";
    }

    @Override
    public String getSequence() {
        return ")!@#$%^&*()";
    }
}
