package com.danisola.restify.url;

import com.danisola.restify.url.types.VarType;

import java.util.regex.Pattern;

class ParamVar {

    private final String name;
    private final VarType[] types;
    private final Pattern valuePattern;

    ParamVar(String name, VarType[] types, Pattern valuePattern) {
        this.name = name;
        this.types = types;
        this.valuePattern = valuePattern;
    }

    String getName() {
        return name;
    }

    VarType[] getTypes() {
        return types;
    }

    Pattern getValuePattern() {
        return valuePattern;
    }
}
