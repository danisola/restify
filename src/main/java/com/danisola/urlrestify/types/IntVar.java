package com.danisola.urlrestify.types;

public class IntVar extends AbstractVarType<Integer> {

    public static IntVar intVar(String id) {
        return new IntVar(id);
    }

    public static IntVar intVar(String id, String regex) {
        return new IntVar(id, regex);
    }

    private IntVar(String id) {
        super(id);
    }

    private IntVar(String id, String regex) {
        super(id, regex);
    }

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
