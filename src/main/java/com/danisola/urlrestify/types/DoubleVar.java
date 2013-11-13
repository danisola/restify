package com.danisola.urlrestify.types;

public class DoubleVar extends AbstractVarType<Double> {

    public static DoubleVar doubleVar(String id) {
        return new DoubleVar(id);
    }

    public static DoubleVar doubleVar(String id, String regex) {
        return new DoubleVar(id, regex);
    }

    private DoubleVar(String id) {
        super(id);
    }

    private DoubleVar(String id, String regex) {
        super(id, regex);
    }

    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }
}
