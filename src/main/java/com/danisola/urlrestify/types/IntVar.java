package com.danisola.urlrestify.types;

public class IntVar extends AbstractVarType<Integer> {

    public static IntVar intVar(String id) {
        return new IntVar(id, "[-]?\\d+");
    }

    public static IntVar posIntVar(String id) {
        return new IntVar(id, "\\d+");
    }

    private IntVar(String id, String pattern) {
        super(id, pattern);
    }

    @Override
    public Integer convert(String value) {
        return Integer.parseInt(value);
    }
}
