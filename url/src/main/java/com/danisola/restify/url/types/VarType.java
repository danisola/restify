package com.danisola.restify.url.types;


public interface VarType<T> {

    public String getId();

    public String getVarPattern();

    public T convert(String value) throws Exception;
}
