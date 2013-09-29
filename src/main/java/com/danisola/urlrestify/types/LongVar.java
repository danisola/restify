package com.danisola.urlrestify.types;

public class LongVar extends AbstractVarType<Long> {

    public static LongVar longVar(String id) {
        return new LongVar(id, "[-]?\\d+");
    }

    public static LongVar posLongVar(String id) {
        return new LongVar(id, "\\d+");
    }

    private LongVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public Long convert(String value) {
        return Long.parseLong(value);
    }
}
