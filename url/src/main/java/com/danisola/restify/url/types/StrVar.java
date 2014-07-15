package com.danisola.restify.url.types;

import static com.danisola.restify.url.preconditions.Preconditions.checkArgument;

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
        checkArgument(value != null, "Argument cannot be null");
        return value;
    }
}
