package com.danisola.restify.url.types;

public class LongVar extends AbstractVarType<Long> {

    public static LongVar longVar(String id) {
        return new LongVar(id);
    }

    public static LongVar longVar(String id, String regex) {
        return new LongVar(id, regex);
    }

    private LongVar(String id) {
        super(id);
    }

    private LongVar(String id, String regex) {
        super(id, regex);
    }

    @Override
    public Long convert(String value) {
        return Long.parseLong(value);
    }
}
