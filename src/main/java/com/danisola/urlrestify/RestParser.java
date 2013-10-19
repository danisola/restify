package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.danisola.urlrestify.ParsedUrl.parseParams;
import static com.danisola.urlrestify.RestUrl.invalidRestUrl;
import static com.danisola.urlrestify.RestUrl.restUrl;
import static com.danisola.urlrestify.preconditions.Preconditions.checkNotNull;

/**
 * A URL parser that produces {@link ParsedUrl}.
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

    public RestUrl parse(String stringUrl) {
        checkNotNull(stringUrl, "Url cannot be null");
        try {
            return parse(new URL(stringUrl));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public RestUrl parse(URL url) {
        checkNotNull(url, "Url cannot be null");
        return parse(url.getPath(), url.getQuery());
    }

    public RestUrl parse(CharSequence path, String queryString) {
        if (path == null) {
            return invalidRestUrl("Url is null");
        }

        // Processing path variables
        Matcher matcher = pathPattern.matcher(path);
        if (!matcher.matches()) {
            return invalidRestUrl("Path '" + path + "' does not match the pattern '" + pathPattern + "'");
        }

        Object[] varValues = new Object[numVars];
        VarType[] varTypes = new VarType[numVars];
        int varIndex = 0;
        for (; varIndex < pathTypes.length; varIndex++) {
            VarType type = pathTypes[varIndex];
            varTypes[varIndex] = type;
            try {
                varValues[varIndex] = type.convert(matcher.group(type.getId()));
            } catch (Exception ex) {
                return invalidRestUrl("Invalid value for variable '" + type.getId() + "' in '" + path + "'");
            }
        }

        // Processing param variables
        List<NameValuePair> valuePairs = parseParams(queryString);
        for (int paramIndex = 0; paramIndex < paramVars.length; paramIndex++) {

            ParamVar paramVar = paramVars[paramIndex];
            NameValuePair valuePair = getValuePairWithName(paramVar.getName(), valuePairs);
            if (valuePair == null) {
                return invalidRestUrl("Parameter '" + paramVar.getName() + "' has not been found");
            }

            Matcher paramMatcher = paramVar.getValuePattern().matcher(valuePair.getValue());
            if (paramMatcher.matches()) {
                VarType[] paramTypes = paramVar.getTypes();
                for (int varParamIndex = 0; varParamIndex < paramTypes.length; varParamIndex++) {
                    VarType paramType = paramTypes[varParamIndex];
                    varTypes[varIndex] = paramType;
                    try {
                        varValues[varIndex] = paramType.convert(paramMatcher.group(paramType.getId()));
                    } catch (Exception ex) {
                        return invalidRestUrl("Invalid parameter value '" + paramVar.getName() +
                                "=" + valuePair.getValue() + "'");
                    }
                    varIndex++;
                }
            } else {
                return invalidRestUrl("Parameter '" + paramVar.getName() +
                        "' does not match the pattern '" + paramVar.getValuePattern() + "'");
            }
        }

        return restUrl(varTypes, varValues);
    }

    private NameValuePair getValuePairWithName(String name, List<NameValuePair> params) {
        for (NameValuePair valuePair : params) {
            if (name.equals(valuePair.getName())) {
                return valuePair;
            }
        }
        return null;
    }
}
