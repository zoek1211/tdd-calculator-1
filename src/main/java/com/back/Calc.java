package com.back;

import java.util.Arrays;

public class Calc {
    public static int run(String expression) {
        expression = removeUnnecessaryBrackets(expression);

        if (expression.contains(" * ") && expression.contains(" + ")) {
            String[] expressionBits = expression.split(" \\+ ");

            int sum = Arrays.stream(expressionBits)
                    .map(Calc::run) // run 재귀호출
                    .reduce(Integer::sum)
                    .orElse(0);
            return sum;
        }

        if (expression.contains(" * ")) {
            String[] expressionBits = expression.split(" \\* ");

            int product = Arrays.stream(expressionBits)
                .map(Integer::parseInt)
                .reduce((a, b) -> a * b)
                .orElse(0);

            return product;
        }

        expression = expression.replace(" - ", " + -");

        String[] expressionBits = expression.split(" \\+ ");

        int sum = Arrays.stream(expressionBits)
                .mapToInt(Integer::parseInt)
                .sum();

        return sum;
    }

    private static String removeUnnecessaryBrackets(String expression) {
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return expression.substring(1, expression.length() - 1);
        }
        return expression;
    }
}
