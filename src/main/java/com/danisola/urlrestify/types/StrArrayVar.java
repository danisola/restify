package com.danisola.urlrestify.types;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public class StrArrayVar extends AbstractVarType<String[]> {

    public static StrArrayVar strArrayVar(String id) {
        return new StrArrayVar(id);
    }

    public static StrArrayVar strArrayVar(String id, String regex) {
        return new StrArrayVar(id, regex);
    }

    private StrArrayVar(String id) {
        super(id);
    }

    private StrArrayVar(String id, String regex) {
        super(id);
    }

    @Override
    public String[] convert(String value) {
        return checkArgumentNotNullOrEmpty(value).split(",");
    }
}
