package com.danisola.urlrestify.types;

public class StringVar extends AbstractVarType<String> {

    public static StringVar regexStrVar(String id, String regex) {
        return new StringVar(id, regex);
    }

    public static StringVar strVar(String id) {
        return new StringVar(id, "[^/\\?\\#]+");
    }

    private StringVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public String convert(String value) {
        return value;
    }
}
