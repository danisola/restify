package com.danisola.urlrestify.types;

public class LongVar extends AbstractVarType<Long> {

    public static LongVar longVar(String id) {
        return new LongVar(id);
    }

    public static LongVar longVar(String id, String pattern) {
        return new LongVar(id, pattern);
    }

    private LongVar(String id) {
        super(id);
    }

    private LongVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public Long convert(String value) {
        return Long.parseLong(value);
    }
}
