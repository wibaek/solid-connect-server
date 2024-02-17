package com.example.solidconnection.type;

import com.example.solidconnection.custom.exception.CustomException;

import java.util.Arrays;

import static com.example.solidconnection.custom.exception.ErrorCode.INVALID_TEST_TYPE;

public enum LanguageTestType {
    TOEFL_IBT, TOEFL_ITP, TOEIC, IELTS, NEW_HSK, JLPT, DUOLINGO, CEFR, DELF, TCF, TEF, DALF;

    public static LanguageTestType getLanguageTestTypeForString(String name) {
        return Arrays.stream(LanguageTestType.values())
                .filter(lt -> lt.toString().equals(name))
                .findFirst()
                .orElseThrow(() -> new CustomException(INVALID_TEST_TYPE, name));
    }
}