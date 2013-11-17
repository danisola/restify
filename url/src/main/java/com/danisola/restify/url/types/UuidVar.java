package com.danisola.restify.url.types;

import java.util.UUID;

public class UuidVar extends AbstractVarType<UUID> {

    public static UuidVar uuidVar(String id) {
        return new UuidVar(id);
    }

    public static UuidVar uuidVar(String id, String regex) {
        return new UuidVar(id, regex);
    }

    private UuidVar(String id) {
        super(id);
    }

    private UuidVar(String id, String regex) {
        super(id, regex);
    }

    @Override
    public UUID convert(String value) {
        return UUID.fromString(value);
    }
}
