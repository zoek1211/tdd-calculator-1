package com.back;

import java.util.Arrays;

public class Calc {
    public static int run(String expression) {
        if (expression.contains(" * ")) {
            String[] expressionBits = expression.split(" \\* ");

            int product = Arrays.stream(expressionBits)
                .map(Integer::parseInt)
                .reduce((a, b) -> a * b)
                .orElse(0);

            return product;
        }

        expression = expression.replaceAll(" - ", " + -");

        String[] expressionBits = expression.split(" \\+ ");

        int sum = Arrays.stream(expressionBits).mapToInt(Integer::parseInt).sum();

        return sum;
    }
}
