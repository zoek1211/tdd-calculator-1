package com.back;

import java.util.Arrays;

public class Calc {
    private static boolean isDebug = true;

    public static int run(String expression) {
        return _run(expression, 0);
    }

    public static int _run(String expression, int depth) {
        expression = expression.trim();

        if (isDebug) {
            System.out.println(" ".repeat(depth) + "expr[" + depth + "] { raw : " + expression);
        }

        expression = removeUnnecessaryBrackets(expression);
        expression = expression.replace(" - ", " + -");

        if (isDebug) {
            System.out.println(", clean : " + expression + " }");
        }

        if (expression.contains("(")) {
            String[] expressionBits = splitTwoPartsBy(expression, '+');

            if  (expressionBits != null) {
                return _run(expressionBits[0], depth + 1) + _run(expressionBits[1], depth + 1);
            }

            expressionBits = splitTwoPartsBy(expression, '*');

            if (expressionBits != null) {
                return _run(expressionBits[0], depth + 1) * _run(expressionBits[1], depth + 1);
            }
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }

        if (expression.contains(" * ") && expression.contains(" + ")) {
            String[] expressionBits = expression.split(" \\+ ");

            return Arrays.stream(expressionBits)
                    .map(exprBit -> _run(exprBit, depth + 1))
                    .reduce(Integer::sum)
                    .orElse(0);
        }

        if (expression.contains(" * ")) {
            String[] expressionBits = expression.split(" \\* ");

            return Arrays.stream(expressionBits)
                    .map(Integer::parseInt)
                    .reduce((a, b) -> a * b)
                    .orElse(0);
        }

        String[] expressionBits = expression.split(" \\+ ");

        return Arrays.stream(expressionBits)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElse(0);
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
