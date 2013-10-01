package com.danisola.urlrestify.types;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public abstract class AbstractVarType<T> implements VarType<T> {

    private final String id;
    private final String groupPatternStr;

    protected AbstractVarType(String id, String pattern) {
        this.id = checkArgumentNotNullOrEmpty(id);
        checkArgumentNotNullOrEmpty(pattern);
        this.groupPatternStr = String.format("(?<%s>%s)", id, pattern);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getGroupPattern() {
        return groupPatternStr;
    }
}
