package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PasswordService {

    private static final int BASE_CATEGORY = 2;
    private static final int BASE_CONSECUTIVE_ITEMS = 2;
    private static final int BASE_LENGTH = 4;
    private static final int MIN_LENGTH = 8;
    private static final int MIN_CATEGORIES = 3;
    private static final int BASE_CALC_SCORE_SYMBOL = 6;
    private static final int BASE_CALC_SCORE_NUMBER = 4;
    private static final int BASE_CALC_SCORE_CASE_LETTER = 2;

    private final AlphaSequentialService alphaSequentialService;
    private final NumberSequentialService numberSequentialService;
    private final SymbolSequentialService symbolSequentialService;

    public PasswordService(AlphaSequentialService alphaSequentialService, NumberSequentialService numberSequentialService, SymbolSequentialService symbolSequentialService) {
        this.alphaSequentialService = alphaSequentialService;
        this.numberSequentialService = numberSequentialService;
        this.symbolSequentialService = symbolSequentialService;
    }

    public int calculatePasswordStrength(String password) {

        int nRepChar = 0;
        int nRepInc = 0;
        int nUnqChar;

        CaseCounter lowerCaseCounter = new CaseCounter(BASE_CONSECUTIVE_ITEMS, BASE_CALC_SCORE_CASE_LETTER);
        CaseCounter upperCaseCounter = new CaseCounter(BASE_CONSECUTIVE_ITEMS, BASE_CALC_SCORE_CASE_LETTER);
        SpecialCounter digitCounter = new SpecialCounter(BASE_CONSECUTIVE_ITEMS, BASE_CALC_SCORE_NUMBER);
        SpecialCounter symbolCounter = new SpecialCounter(BASE_CONSECUTIVE_ITEMS, BASE_CALC_SCORE_SYMBOL);

        char[] charArray = password.toCharArray();
        int passwordLength = password.length();
        for (int index = 0, charArrayLength = charArray.length; index < charArrayLength; index++) {
            char ch = charArray[index];
            if (Character.isUpperCase(ch)) {
                upperCaseCounter.count(index);
            } else if (Character.isLowerCase(ch)) {
                lowerCaseCounter.count(index);
            } else if (Character.isDigit(ch)) {
                digitCounter.count(index, passwordLength - 1);
            } else {
                symbolCounter.count(index, passwordLength - 1);
            }

            boolean bCharExists = false;

            for (int b = 0; b < passwordLength; b++) {
                if (password.charAt(index) == password.charAt(b) && index != b) {
                    bCharExists = true;

                    nRepInc += Math.abs(passwordLength / (b - index));
                }
            }

            if (bCharExists) {
                nRepChar++;
                nUnqChar = passwordLength - nRepChar;
                nRepInc = (nUnqChar != 0) ? (int) Math.ceil(nRepInc / nUnqChar) : (int) Math.ceil(nRepInc);
            }
        }

        int passwordScore = additions(upperCaseCounter, lowerCaseCounter, digitCounter, symbolCounter, passwordLength);

        passwordScore = deductions(password, passwordScore, digitCounter, symbolCounter, upperCaseCounter, lowerCaseCounter, passwordLength, nRepChar, nRepInc);

        return Math.min(100, passwordScore);
    }

    private int additions(CaseCounter upperCaseCounter, CaseCounter lowerCaseCounter, SpecialCounter digitCounter, SpecialCounter symbolCounter, int passwordLength) {
        int categoriesCount = checkCategoriesCount(upperCaseCounter, lowerCaseCounter, digitCounter, symbolCounter);

        int passwordScore = 0;

        int scoreMinimumRequirements = checkMinimumRequirements(passwordLength, MIN_LENGTH, categoriesCount);
        log.info("Minumum Requirements: +{}", scoreMinimumRequirements);
        passwordScore += scoreMinimumRequirements;

        int lengthScore = passwordLength * BASE_LENGTH;
        log.info("Password Length: +{}", lengthScore);
        passwordScore += lengthScore;

        int upperCaseScore = upperCaseCounter.scoreValue(passwordLength);
        log.info("UpperCase: +{}", upperCaseScore);
        passwordScore += upperCaseScore;

        int lowerCaseScore = lowerCaseCounter.scoreValue(passwordLength);
        log.info("LowerCase: +{}", lowerCaseScore);
        passwordScore += lowerCaseScore;

        int numberScore = digitCounter.scoreValue();
        log.info("Numbers: +{}", numberScore);
        passwordScore += numberScore;

        int symbols = symbolCounter.scoreValue();
        log.info("symbols: +{}", symbols);
        passwordScore += symbols;

        int middleNumberSymbolScore = (symbolCounter.getMiddleDigitCount() + digitCounter.getMiddleDigitCount()) * 2;
        log.info("Middle Numbers or Symbols: +{}", middleNumberSymbolScore);
        passwordScore += middleNumberSymbolScore;
        return passwordScore;
    }

    private int deductions(String password, int passwordScore, SpecialCounter digitCounter, SpecialCounter symbolCounter, CaseCounter upperCaseCounter, CaseCounter lowerCaseCounter, int passwordLength, int nRepChar, int nRepInc) {
        passwordScore -= checkHasOnlyDigitOrLetter(digitCounter.getCount(), symbolCounter.getCount(), upperCaseCounter.getCount(), lowerCaseCounter.getCount(), passwordLength);

        int discountRepChar = 0;
        if (nRepChar > 0) {
            discountRepChar = nRepInc;
        }
        log.info("Repeat Characters (Case Insensitive): -{}", discountRepChar);
        passwordScore -= discountRepChar;

        int discountConsecutiveUpper = upperCaseCounter.scoreConsecutiveItems();
        log.info("Consecutive Uppercase Letters: -{}", discountConsecutiveUpper);
        passwordScore -= discountConsecutiveUpper;

        int discountConsecutiveLower = lowerCaseCounter.scoreConsecutiveItems();
        log.info("Consecutive LowerCase Letters: -{}", discountConsecutiveLower);
        passwordScore -= discountConsecutiveLower;

        int discountConsecutiveNumber = digitCounter.scoreConsecutiveItems();
        log.info("Consecutive Numbers: -{}", discountConsecutiveNumber);
        passwordScore -= discountConsecutiveNumber;

        passwordScore -= alphaSequentialService.countSequentialItems(password);

        passwordScore -= numberSequentialService.countSequentialItems(password);

        passwordScore -= symbolSequentialService.countSequentialItems(password);
        return passwordScore;
    }
    private int checkCategoriesCount(CaseCounter upperCaseCounter, CaseCounter lowerCaseCounter, SpecialCounter digitCounter, SpecialCounter symbolCounter) {
        int categoriesCount = 0;
        if (upperCaseCounter.hasValue()) categoriesCount++;
        if (lowerCaseCounter.hasValue()) categoriesCount++;
        if (digitCounter.hasValue()) categoriesCount++;
        if (symbolCounter.hasValue()) categoriesCount++;
        return categoriesCount;
    }

    private int checkHasOnlyDigitOrLetter(int digitCount, int symbolCount, int uppercaseCount, int lowercaseCount, int passwordLength) {
        if (digitCount + symbolCount == 0 || uppercaseCount + lowercaseCount + symbolCount == 0) {
            log.info("Only Digit or letter: " + passwordLength);
            return passwordLength;
        }
        return 0;
    }

    private int checkMinimumRequirements(int passwordLength, int minLength, int categoriesCount) {
        if (passwordLength >= minLength) {

            if (categoriesCount >= MIN_CATEGORIES) {
                categoriesCount++;
                return categoriesCount * BASE_CATEGORY;
            }
        }
        return 0;
    }


}
