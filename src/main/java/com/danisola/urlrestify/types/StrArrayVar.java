package com.danisola.urlrestify.types;

public class StrArrayVar extends AbstractVarType<String[]> {

    public static StrArrayVar strArrayVar(String id) {
        return new StrArrayVar(id, "[^/,\\#]+(,[^/,\\#]+)*");
    }

    private StrArrayVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public String[] convert(String value) {
        return value.split(",");
    }
}
