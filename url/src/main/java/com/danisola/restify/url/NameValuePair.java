package com.danisola.restify.url;

import static com.danisola.restify.url.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

class NameValuePair {

    private final String name;
    private final String value;

    public NameValuePair(String name, String value) {
        this.name = checkArgumentNotNullOrEmpty(name);
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
