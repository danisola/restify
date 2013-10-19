package com.danisola.urlrestify;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;

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
