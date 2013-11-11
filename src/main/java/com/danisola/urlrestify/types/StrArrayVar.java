package com.danisola.urlrestify.types;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

public class StrArrayVar extends AbstractVarType<String[]> {

    public static StrArrayVar strArrayVar(String id) {
        return new StrArrayVar(id);
    }

    private StrArrayVar(String id) {
        super(id);
    }

    @Override
    public String[] convert(String value) {
        return checkArgumentNotNullOrEmpty(value).split(",");
    }
}
