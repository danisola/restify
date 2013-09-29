package com.danisola.urlrestify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;
import static com.danisola.urlrestify.preconditions.Preconditions.checkState;

class ParsedUrl {

    private final String path;
    private final List<NameValuePair> parameters;

    private ParsedUrl(String path, List<NameValuePair> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public String getPath() {
        return path;
    }

    public List<NameValuePair> getParameters() {
        return parameters;
    }

    public static ParsedUrl parseUrl(String url) {
        String path = null;
        String queryString = null;

        int questionIndex = url.indexOf("?");
        if (questionIndex >= 0) {
            path = url.substring(0, questionIndex);
            queryString = url.substring(questionIndex + 1);
        } else {
            path = url;
        }

        return new ParsedUrl(path, parseParams(queryString));
    }

    public static List<NameValuePair> parseParams(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }

        List<NameValuePair> pairs = new ArrayList<>();
        int startIndex = 0;
        while (startIndex < query.length()) {
            int equalsIndex = query.indexOf("=", startIndex);
            checkState(equalsIndex > 0, "Parameters are not well formatted: " + query.substring(startIndex));
            String paramName = query.substring(startIndex, equalsIndex);
            String paramValue;
            int andIndex = query.indexOf("&", equalsIndex);
            if (andIndex > equalsIndex) {
                paramValue = query.substring(equalsIndex + 1, andIndex);
                startIndex = andIndex + 1;
            } else {
                paramValue = query.substring(equalsIndex + 1);
                startIndex = query.length();
            }
            pairs.add(new NameValuePair(paramName, paramValue));
        }
        return pairs;
    }

    public static class NameValuePair {

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

        @Override
        public String toString() {
            return "NameValuePair{name='" + name + "\', value='" + value + "\'}";
        }
    }
}
