package com.danisola.urlrestify.types;

public class DoubleVar extends AbstractVarType<Double> {

    public static DoubleVar doubleVar(String id) {
        return new DoubleVar(id);
    }

    private DoubleVar(String id) {
        super(id, "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
    }

    @Override
    public Double convert(String value) {
        return Double.parseDouble(value);
    }
}
