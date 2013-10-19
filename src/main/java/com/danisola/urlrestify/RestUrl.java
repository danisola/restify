package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public class RestUrl {

    private final VarType[] types;
    private final Object[] values;
    private final String errorMessage;

    static RestUrl invalidRestUrl(String errorMsg) {
        return new RestUrl(errorMsg);
    }

    static RestUrl restUrl(VarType[] types, Object[] values) {
        return new RestUrl(types, values);
    }

    private RestUrl(VarType[] types, Object[] values) {
        this.types = types;
        this.values = values;
        this.errorMessage = null;
    }

    private RestUrl(String errorMsg) {
        this.types = null;
        this.values = null;
        this.errorMessage = errorMsg;
    }

    public boolean isValid() {
        return errorMessage == null;
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
        throw new IllegalStateException("Variable id '" + variableId + "' is not declared in the pattern");
    }
}
