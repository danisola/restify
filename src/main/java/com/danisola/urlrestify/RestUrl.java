package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;
import static com.danisola.urlrestify.preconditions.Preconditions.checkState;

/**
 * A REST representation of an URL. Allows to get access to the variables defined in the {@link RestParser} that has
 * generated it.
 *
 * <p>Instances of this class are immutable and thus thread-safe.</p>
 */
public class RestUrl {

    final String errorMessage;
    final VarType[] types;
    final Object[] values;

    static RestUrl invalidRestUrl(String errorMsg) {
        return new RestUrl(null, null, errorMsg);
    }

    static RestUrl restUrl(VarType[] types, Object[] values) {
        return new RestUrl(types, values, null);
    }

    private RestUrl(VarType[] types, Object[] values, String errorMsg) {
        this.types = types;
        this.values = values;
        this.errorMessage = errorMsg;
    }

    /**
     * Returns true if it matches the pattern and variable types of the {@link RestParser} that has generated this
     * {@link RestUrl}.
     */
    public boolean isValid() {
        return errorMessage == null;
    }

    /**
     * <p>If {@link #isValid()} is false, returns a message explaining why the parsed URL did not match the pattern
     * and variables of the {@link RestParser} that has generated this {@link RestUrl}.</p>
     *
     * <p>If {@link #isValid()} is true, returns null</p>
     */
    public String errorMessage() {
        return errorMessage;
    }

    /**
     * Returns the given variable with the specified return type. The variable id and type must match the declared
     * ones in the {@link RestParser} that has generated this {@link RestUrl}.
     *
     * <p>This method throws exceptions to enforce a correct usage of the API.</p>
     *
     * @throws IllegalArgumentException if variableId is null, empty or not defined in the {@link RestParser}
     * @throws IllegalStateException if the URL is invalid ({@link #isValid()} is false)
     * @throws ClassCastException if the returned variable has not the same type as the declared variable in {@link RestParser}
     */
    public <T> T variable(String variableId) {
        checkArgumentNotNullOrEmpty(variableId);
        checkState(isValid(), "Variables are not accessible for invalid URLs");

        for (int i = 0; i < types.length; i++) {
            if (types[i].getId().equals(variableId)) {
                return (T) values[i];
            }
        }
        throw new IllegalArgumentException("Variable id '" + variableId + "' is not declared in the pattern");
    }
}
