package com.danisola.urlrestify.types;

import java.util.regex.Pattern;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public abstract class AbstractVarType<T> implements VarType<T> {

    private final String id;
    private final String groupPatternStr;
    private Pattern groupPattern;

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

    @Override
    public boolean matches(String value) {
        if (groupPattern == null) {
            groupPattern = Pattern.compile(groupPatternStr);
        }
        return groupPattern.matcher(value).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractVarType that = (AbstractVarType) o;
        if (groupPattern != null ? !groupPattern.equals(that.groupPattern) : that.groupPattern != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return groupPattern != null ? groupPattern.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id='" + id + "'}";
    }
}
