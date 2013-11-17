package com.danisola.restify.url;

import com.danisola.restify.url.types.Opt;
import com.danisola.restify.url.types.VarType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.danisola.restify.url.ParsedUrl.parseParams;
import static com.danisola.restify.url.RestUrl.invalidRestUrl;
import static com.danisola.restify.url.RestUrl.restUrl;

/**
 * A URL parser that produces {@link ParsedUrl}. It does not throw any exceptions in case the parsed URLs are found to
 * be incorrect, but the resulting {@link RestUrl} will be invalid.
 *
 * <p>Instances of this class are immutable and thus thread-safe.</p>
 */
public class RestParser {

    private final Pattern pathPattern;
    private final VarType[] pathTypes;
    private final ParamVar[] paramVars;
    private final int numVars;

    RestParser(Pattern pathPattern, VarType[] pathTypes, ParamVar[] paramVars, int numVars) {
        this.pathPattern = pathPattern;
        this.pathTypes = pathTypes;
        this.paramVars = paramVars;
        this.numVars = numVars;
    }

    /**
     * Parses the given fully qualified URL.
     *
     * @return the {@link RestUrl} that represents the given URL.
     */
    public RestUrl parse(String url) {
        if (url == null) {
            return RestUrl.invalidRestUrl("Url is null");
        }

        try {
            return parse(new URL(url));
        } catch (MalformedURLException e) {
            return RestUrl.invalidRestUrl("Url is malformed");
        }
    }

    /**
     * Parses the given URL.
     *
     * @return the {@link RestUrl} that represents the given URL.
     */
    public RestUrl parse(URL url) {
        if (url == null) {
            return RestUrl.invalidRestUrl("Url is null");
        }
        return parse(url.getPath(), url.getQuery());
    }

    /**
     * Parses the given path and query.
     *
     * @return the {@link RestUrl} that represents the given URL.
     */
    public RestUrl parse(String path, String queryString) {
        if (path == null) {
            return RestUrl.invalidRestUrl("Url is null");
        }

        RestUrl restUrl = RestUrl.restUrl(new VarType[numVars], new Object[numVars]);
        int varIndex = 0;

        // Processing path variables
        Matcher pathMatcher = pathPattern.matcher(path);
        if (!pathMatcher.matches()) {
            return RestUrl.invalidRestUrl("Path '" + path + "' does not match the pattern '" + pathPattern + "'");
        }

        for (VarType type : pathTypes) {
            try {
                varIndex = extract(pathMatcher, type, varIndex, restUrl);
            } catch (Exception ex) {
                return RestUrl.invalidRestUrl("Invalid value for variable '" + type.getId() + "': " + pathMatcher.group(type.getId()));
            }
        }

        // Processing param variables
        List<NameValuePair> valuePairs = parseParams(queryString);
        for (ParamVar paramVar : paramVars) {
            String paramName = paramVar.getName();
            NameValuePair valuePair = getValuePairWithName(paramName, valuePairs);
            if (valuePair == null) {
                if (areOptional(paramVar.getTypes())) {
                    varIndex = setNull(paramVar.getTypes(), varIndex, restUrl);
                    continue;
                } else {
                    return RestUrl.invalidRestUrl("Parameter '" + paramName + "' was not set");
                }
            }

            String paramValue = valuePair.getValue();
            Matcher paramMatcher = paramVar.getValuePattern().matcher(paramValue);
            if (!paramMatcher.matches()) {
                return RestUrl.invalidRestUrl("Parameter '" + paramName + "=" + paramValue + "' does not match pattern");
            }
            for (VarType paramType : paramVar.getTypes()) {
                try {
                    varIndex = extract(paramMatcher, paramType, varIndex, restUrl);
                } catch (Exception ex) {
                    return RestUrl.invalidRestUrl("Invalid value for variable '" + paramName + "': " + paramValue);
                }
            }
        }

        return restUrl;
    }

    private NameValuePair getValuePairWithName(String name, List<NameValuePair> params) {
        for (NameValuePair valuePair : params) {
            if (name.equals(valuePair.getName())) {
                return valuePair;
            }
        }
        return null;
    }

    private boolean areOptional(VarType[] varTypes) {
        for (VarType varType : varTypes) {
            if (!(varType instanceof Opt)) {
                return false;
            }
        }
        return true;
    }

    private int extract(Matcher matcher, VarType type, int varIndex, RestUrl restUrl) throws Exception {
        String value = matcher.group(type.getId());
        restUrl.values[varIndex] = type.convert(value);
        restUrl.types[varIndex] = type;
        return ++varIndex;
    }

    private int setNull(VarType[] types, int varIndex, RestUrl restUrl) {
        for (VarType type : types) {
            restUrl.values[varIndex] = null;
            restUrl.types[varIndex] = type;
            varIndex++;
        }
        return varIndex;
    }
}
