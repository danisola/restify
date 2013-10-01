package com.danisola.urlrestify.types;


public interface VarType<T> {

    public String getId();

    public String getGroupPattern();

    public T convert(String value);
}
