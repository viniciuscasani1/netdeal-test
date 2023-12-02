package com.example.demo.service;

import lombok.Getter;


@Getter
public  class CaseCounter {

    private int count = 0;
    private int auxIndex;
    private int consecutiveItems;
    private final int baseCalcConsecutiveItems;
    private final int baseCalcScore;

    public CaseCounter(int baseCalcConsecutiveItems, int baseCalcScore) {
        this.baseCalcConsecutiveItems = baseCalcConsecutiveItems;
        this.baseCalcScore = baseCalcScore;
    }

    public void count(int index) {
        if (auxIndex + 1 == index) {
            consecutiveItems++;
        }

        auxIndex = index;
        count++;
    }

    public int scoreConsecutiveItems(){
        return consecutiveItems * baseCalcConsecutiveItems;
    }


    public int scoreValue(int passwordLength){
        if (hasValue()) {
            return  (passwordLength - count) * baseCalcScore;
        }
        return 0;
    }

    public Boolean hasValue(){
        return count > 0;
    }
}
