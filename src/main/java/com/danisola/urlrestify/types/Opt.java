package com.danisola.urlrestify.types;

public class Opt<T> implements VarType<T> {

    public static <T> Opt<T> opt(VarType<T> varType) {
        return new Opt<>(varType);
    }

    private final VarType<T> varType;

    private Opt(VarType<T> varType) {
        this.varType = varType;
    }

    @Override
    public String getId() {
        return varType.getId();
    }

    @Override
    public String getVarPattern() {
        return varType.getVarPattern();
    }

    @Override
    public T convert(String value) throws Exception {
        if ("".equals(value)) {
            return null;
        }
        return varType.convert(value);
    }
}
