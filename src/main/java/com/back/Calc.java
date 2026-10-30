package com.back;

import java.util.Arrays;

public class Calc {
    public static int run(String expression) {
        expression = expression.trim();
        expression = removeUnnecessaryBrackets(expression);
        expression = expression.replace(" - ", " + -");

        if (expression.contains("(")) {
            String[] expressionBits = splitTwoPartsBy(expression, '+');

            if  (expressionBits != null) {
                int sum = Arrays.stream(expressionBits)
                        .map(Calc::run) // run 재귀호출
                        .reduce(Integer::sum)
                        .orElse(0);
                return sum;
            }

            expressionBits = splitTwoPartsBy(expression, '*');

            if (expressionBits != null) {
                int product = Arrays.stream(expressionBits)
                        .map(Calc::run)
                        .reduce((a, b) -> a * b)
                        .orElse(0);

                return product;
            }
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }

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

        String[] expressionBits = expression.split(" \\+ ");

        int sum = Arrays.stream(expressionBits)
                .mapToInt(Integer::parseInt)
                .sum();

        return sum;
    }

    private static String[] splitTwoPartsBy(String expression, char splitBy) {
        int bracketDepth = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (bracketDepth == 0 && c == splitBy) {
                return new String[] {
                        expression.substring(0, i), expression.substring(i + 1)
                };
            } else if (c == '(') {
                bracketDepth++;
            } else if (c == ')') {
                bracketDepth--;
            }
        }
        return null;
    }

    private static String removeUnnecessaryBrackets(String expression) {
        if (expression.startsWith("(") && expression.endsWith(")")) {
            return removeUnnecessaryBrackets(expression.substring(1, expression.length() - 1));
        }
        return expression;
    }
}
