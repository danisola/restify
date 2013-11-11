package com.danisola.urlrestify.types;

public class IntVar extends AbstractVarType<Integer> {

    public static IntVar intVar(String id) {
        return new IntVar(id);
    }

    private IntVar(String id) {
        super(id);
    }

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
