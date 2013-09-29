package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public class RestUrl {

    private final VarType[] types;
    private final Object[] values;
    private final boolean error;
    private final String errorMessage;

    static RestUrl parseError(String errorMsg) {
        return new RestUrl(errorMsg);
    }

    static RestUrl parseResult(VarType[] types, Object[] values) {
        return new RestUrl(types, values);
    }

    private RestUrl(VarType[] types, Object[] values) {
        this.types = types;
        this.values = values;
        this.error = false;
        this.errorMessage = null;
    }

    private RestUrl(String errorMsg) {
        this.types = null;
        this.values = null;
        this.error = true;
        this.errorMessage = errorMsg;
    }

    public boolean hasErrors() {
        return error;
    }

    public String errorMessage() {
        return errorMessage;
    }

    public <T> T variable(String variableId) {
        checkArgumentNotNullOrEmpty(variableId);

        for (int i = 0; i < types.length; i++) {
            if (types[i].getId().equals(variableId)) {
                return (T) values[i];
            }
        }
        throw new IllegalStateException("Variable id '" + variableId + "' has not declared in the pattern");
    }
}
