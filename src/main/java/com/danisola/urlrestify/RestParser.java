package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.danisola.urlrestify.ParsedUrl.parseParams;
import static com.danisola.urlrestify.RestUrl.invalidRestUrl;
import static com.danisola.urlrestify.RestUrl.restUrl;
import static com.danisola.urlrestify.preconditions.Preconditions.*;

public class RestParser {

    private static final String VAR_MARKER = "{}";
    private final Pattern pathPattern;
    private final VarType[] pathTypes;
    private final ParamVar[] paramVars;
    private final int numVariables;

    public static RestParser parser(String pattern, VarType ... types) {
        checkPattern(pattern);
        checkArgumentNotNullOrEmpty(types);

        final int numTypes = types.length;
        ParsedUrl parsedUrl = ParsedUrl.parseUrl(pattern);
        StringBuilder sb = new StringBuilder("^");
        sb.append(parsedUrl.getPath());

        // Replacing path placeholders for its names
        int pathVarsIndex = sb.indexOf(VAR_MARKER);
        int typesIndex = 0;
        while (pathVarsIndex >= 0) {
            checkState(typesIndex < numTypes, "There are more variables in the pattern than types");

            String groupPattern = types[typesIndex].getGroupPattern();
            sb.replace(pathVarsIndex, pathVarsIndex + VAR_MARKER.length(), groupPattern);

            typesIndex++;
            pathVarsIndex = sb.indexOf(VAR_MARKER, pathVarsIndex + groupPattern.length());
        }
        Pattern pathPattern = Pattern.compile(sb.append("$").toString());
        VarType[] pathTypes = Arrays.copyOfRange(types, 0, typesIndex);

        // Setting up parameters
        List<ParsedUrl.NameValuePair> params = parsedUrl.getParameters();
        ParamVar[] paramVars = new ParamVar[params.size()];
        int paramVarCounter = 0;
        for (int i = 0; i < paramVars.length; i++) {
            ParsedUrl.NameValuePair pair = params.get(i);

            sb.setLength(0);
            sb.append("^").append(pair.getValue());

            int paramVarsIndex = sb.indexOf(VAR_MARKER);
            int paramVarsCount = 0;
            while (paramVarsIndex >= 0) {
                String groupPattern = types[typesIndex + paramVarsCount].getGroupPattern();
                sb.replace(paramVarsIndex, paramVarsIndex + VAR_MARKER.length(), groupPattern);

                paramVarsCount++;
                paramVarsIndex = sb.indexOf(VAR_MARKER, paramVarsIndex + groupPattern.length());
                paramVarCounter++;
            }

            checkState(paramVarsCount > 0, "Parameter '%s' has no variables in its value", pair.getName());

            VarType[] paramTypes = Arrays.copyOfRange(types, typesIndex, typesIndex + paramVarsCount);
            Pattern paramValuePattern = Pattern.compile(sb.append("$").toString());
            paramVars[i] = new ParamVar(pair.getName(), paramTypes, paramValuePattern);
            typesIndex += paramVarsCount;
        }

        final int numVars = pathTypes.length + paramVarCounter;
        checkState(numVars == numTypes, "The number of variables (%d) and types (%d) do not match", numVars, numTypes);
        return new RestParser(pathPattern, pathTypes, paramVars, numTypes);
    }

    private static void checkPattern(String pattern) {
        checkArgumentNotNullOrEmpty(pattern);
        checkArgument(!pattern.endsWith("/"), "Patterns must not end with /");
        checkArgument(!pattern.contains("#"), "Patterns must not contain fragments (#)");
    }

    private RestParser(Pattern pathPattern, VarType[] pathTypes, ParamVar[] paramVars, int numVariables) {
        this.pathPattern = pathPattern;
        this.pathTypes = pathTypes;
        this.paramVars = paramVars;
        this.numVariables = numVariables;
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

        Matcher matcher = pathPattern.matcher(path);
        if (!matcher.matches()) {
            return invalidRestUrl("Path '" + path + "' does not match the pattern '" + pathPattern + "'");
        }

        Object[] varValues = new Object[numVariables];
        VarType[] varTypes = new VarType[numVariables];

        // Processing path variables
        int varIndex = 0;
        for (; varIndex < pathTypes.length; varIndex++) {
            VarType type = pathTypes[varIndex];
            varTypes[varIndex] = type;
            varValues[varIndex] = type.convert(matcher.group(type.getId()));
        }

        // Processing param variables
        List<ParsedUrl.NameValuePair> valuePairs = parseParams(queryString);
        for (int paramIndex = 0; paramIndex < paramVars.length; paramIndex++) {

            ParamVar paramVar = paramVars[paramIndex];
            ParsedUrl.NameValuePair valuePair = getValuePairWithName(paramVar.getName(), valuePairs);
            if (valuePair == null) {
                return invalidRestUrl("Parameter '" + paramVar.getName() + "' has not been found");
            }

            Matcher paramMatcher = paramVar.getValuePattern().matcher(valuePair.getValue());
            if (paramMatcher.matches()) {
                VarType[] paramTypes = paramVar.getTypes();
                for (int varParamIndex = 0; varParamIndex < paramTypes.length; varParamIndex++) {
                    VarType paramType = paramTypes[varParamIndex];
                    varTypes[varIndex] = paramType;
                    varValues[varIndex] = paramType.convert(paramMatcher.group(paramType.getId()));
                    varIndex++;
                }
            } else {
                return invalidRestUrl("Parameter '" + paramVar.getName() +
                        "' does not match the pattern '" + paramVar.getValuePattern() + "'");
            }
        }

        return restUrl(varTypes, varValues);
    }

    private static ParsedUrl.NameValuePair getValuePairWithName(String name, List<ParsedUrl.NameValuePair> params) {
        for (ParsedUrl.NameValuePair valuePair : params) {
            if (name.equals(valuePair.getName())) {
                return valuePair;
            }
        }
        return null;
    }
}
