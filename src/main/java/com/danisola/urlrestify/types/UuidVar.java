package com.danisola.urlrestify.types;

import java.util.UUID;

public class UuidVar extends AbstractVarType<UUID> {

    public static UuidVar uuidVar(String id) {
        return new UuidVar(id);
    }

    private UuidVar(String id) {
        super(id);
    }

    @Override
    public UUID convert(String value) {
        return UUID.fromString(value);
    }
}
