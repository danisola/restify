package com.danisola.restify.url.types;

public class FloatVar extends AbstractVarType<Float> {

    public static FloatVar floatVar(String id) {
        return new FloatVar(id);
    }

    public static FloatVar floatVar(String id, String regex) {
        return new FloatVar(id, regex);
    }

    private FloatVar(String id) {
        super(id);
    }

    private FloatVar(String id, String regex) {
        super(id, regex);
    }

    @Override
    public Float convert(String value) {
        return Float.parseFloat(value);
    }
}
