package com.danisola.urlrestify.types;

public class IntArrayVar extends AbstractVarType<Integer[]> {

    public static IntArrayVar intArrayVar(String id) {
        return new IntArrayVar(id);
    }

    private IntArrayVar(String id) {
        super(id);
    }

    @Override
    public Integer[] convert(String value) {
        String[] split = value.split(",");
        Integer[] array = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        return array;
    }
}
