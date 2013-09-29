package com.danisola.urlrestify.types;

public class BoolVar extends AbstractVarType<Boolean> {

    public static BoolVar boolVar(String id) {
        return new BoolVar(id);
    }

    private BoolVar(String id) {
        super(id, "(?i:true|false)");
    }

    @Override
    public Boolean convert(String value) {
        return Boolean.parseBoolean(value);
    }
}
