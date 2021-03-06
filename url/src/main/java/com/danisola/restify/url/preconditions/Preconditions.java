package com.danisola.restify.url.preconditions;

/**
 * Set of static methods to verify arguments and states partially inspired by
 * Guava Preconditions class (http://code.google.com/p/guava-libraries).
 */
public class Preconditions {

    private Preconditions() {
        // Non-instantiable
    }

    public static String checkArgumentNotNullOrEmpty(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("Argument cannot be null or empty");
        }
        return string;
    }

    /**
     * Ensures the truth of an expression
     *
     * @param expression a boolean expression
     * @param errorMessageFormat the exception message format to use if the check fails
     * @param args Arguments referenced by the format specifiers in the format string
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(boolean expression, String errorMessageFormat, Object ... args) {
        if (!expression) {
            throw new IllegalStateException(String.format(errorMessageFormat, args));
        }
    }

    /**
     * Ensures the truth of an expression involving an argument passed to a method or function
     *
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
