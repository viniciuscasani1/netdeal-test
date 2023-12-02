package com.example.demo.service;

import lombok.Getter;


@Getter
public class SpecialCounter extends CaseCounter {

    private int middleDigitCount = 0;

    public SpecialCounter(int baseCalcConsecutiveItems, int baseCalcScore) {
        super(baseCalcConsecutiveItems, baseCalcScore);
    }


    public void count(int index, int passwordLength) {
        if (index > 0 && index < passwordLength) {
            middleDigitCount++;
        }
        super.count(index);
    }

    public int scoreValue(){
        return getBaseCalcScore() * getCount();
    }
}
