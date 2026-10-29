package com.back;

import java.util.Arrays;

public class Calc {
    public static int run(String expression) {
        expression = expression.replaceAll(" - ", " + -");

        String[] expressionBits = expression.split(" \\+ ");

        int sum = Arrays.stream(expressionBits)
                .map(Integer::parseInt)
                .reduce((a, b) -> a + b)
                .orElse(0);

        return sum;
    }
}
