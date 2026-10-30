package com.back;

import java.util.Arrays;

public class Calc {
    private static boolean isDebug = true;

    public static int run(String expression) {
        return _run(expression, 0);
    }

    private static void printRsOnDebugMode(int rs, int depth) {
        if (isDebug) {
            System.out.println(" ".repeat(depth) + "rs[" + depth + "] = " + rs);
        }
    }

    public static int _run(String expression, int depth) {
        expression = expression.trim();

        if (isDebug) {
            System.out.println(" ".repeat(depth) + "expr[" + depth + "] { raw : " + expression);
        }

        expression = removeUnnecessaryBrackets(expression);
        expression = transformMinusOuterBracketToPlus(expression);
        expression = expression.replace(" - ", " + -");

        if (isDebug) {
            System.out.println(", clean : " + expression + " }");
        }

        if (expression.contains("(")) {
            String[] expressionBits = splitTwoPartsBy(expression, '+');

            if  (expressionBits != null) {
                int rs = _run(expressionBits[0], depth + 1) + _run(expressionBits[1], depth + 1);

                printRsOnDebugMode(rs, depth);

                return rs;
            }

            expressionBits = splitTwoPartsBy(expression, '*');

            if (expressionBits != null) {
                int rs = _run(expressionBits[0], depth + 1) * _run(expressionBits[1], depth + 1);

                printRsOnDebugMode(rs, depth);

                return rs;
            }
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }

        if (expression.contains(" * ") && expression.contains(" + ")) {
            String[] expressionBits = expression.split(" \\+ ");

            int rs = Arrays.stream(expressionBits)
                    .map(exprBit -> _run(exprBit, depth + 1))
                    .reduce(Integer::sum)
                    .orElse(0);

            printRsOnDebugMode(rs, depth);

            return rs;
        }

        if (expression.contains(" * ")) {
            String[] expressionBits = expression.split(" \\* ");

            int rs = Arrays.stream(expressionBits)
                    .map(Integer::parseInt)
                    .reduce((a, b) -> a * b)
                    .orElse(0);

            printRsOnDebugMode(rs, depth);

            return rs;
        }

        String[] expressionBits = expression.split(" \\+ ");

        int rs = Arrays.stream(expressionBits)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElse(0);

        printRsOnDebugMode(rs, depth);

        return rs;
    }

    private static String transformMinusOuterBracketToPlus(String expression) {
        if (expression.startsWith("-(") && expression.endsWith(")")) {
            return expression.substring(1, expression.length()) + " * -1";
        }
        return expression;
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
