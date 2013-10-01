package com.danisola.urlrestify.preconditions;

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

    public static <T> T[] checkArgumentNotNullOrEmpty(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Argument cannot be null or empty");
        }
        return array;
    }

    /**
     * Ensures that the given argument is not null
     *
     * @param argument an object reference
     * @param errorMessage the exception message to use if the check fails
     * @throws NullPointerException if {@code argument} is null
     */
    public static <T> T checkNotNull(T argument, String errorMessage) {
        if (argument == null) {
            throw new NullPointerException(errorMessage);
        }
        return argument;
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
