package com.danisola.urlrestify.types;

public class DoubleVar extends AbstractVarType<Double> {

    public static DoubleVar doubleVar(String id) {
        return new DoubleVar(id);
    }

    private DoubleVar(String id) {
        super(id);
    }

    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }
}
