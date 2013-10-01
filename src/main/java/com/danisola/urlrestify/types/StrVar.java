package com.danisola.urlrestify.types;

public class StrVar extends AbstractVarType<String> {

    public static StrVar regexStrVar(String id, String regex) {
        return new StrVar(id, regex);
    }

    public static StrVar strVar(String id) {
        return new StrVar(id, "[^/\\#]+");
    }

    private StrVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public String convert(String value) {
        return value;
    }
}
