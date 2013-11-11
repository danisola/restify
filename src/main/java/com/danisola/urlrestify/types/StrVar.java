package com.danisola.urlrestify.types;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public class StrVar extends AbstractVarType<String> {

    public static StrVar strVar(String id) {
        return new StrVar(id);
    }

    public static StrVar strVar(String id, String regex) {
        return new StrVar(id, regex);
    }

    private StrVar(String id) {
        super(id);
    }

    private StrVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public String convert(String value) {
        return checkArgumentNotNullOrEmpty(value);
    }
}
