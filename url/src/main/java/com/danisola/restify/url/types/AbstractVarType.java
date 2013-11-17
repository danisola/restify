package com.danisola.restify.url.types;

import static com.danisola.restify.url.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public abstract class AbstractVarType<T> implements VarType<T> {

    private final String id;
    private final String varPatternStr;

    protected AbstractVarType(String id) {
        this(id, ".*?");
    }

    protected AbstractVarType(String id, String pattern) {
        this.id = checkArgumentNotNullOrEmpty(id);
        this.varPatternStr = String.format("(?<%s>%s)", id, checkArgumentNotNullOrEmpty(pattern));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getVarPattern() {
        return varPatternStr;
    }
}
