package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.danisola.urlrestify.ParsedUrl.parseParams;
import static com.danisola.urlrestify.RestUrl.parseError;
import static com.danisola.urlrestify.RestUrl.parseResult;
import static com.danisola.urlrestify.preconditions.Preconditions.*;

public class RestParser {

    private static final String VAR_MARKER = "{}";
    private final Pattern pathPattern;
    private final VarType[] pathTypes;
    private final String[] paramNames;
    private final VarType[] paramTypes;

    public static RestParser parser(String pattern, VarType ... types) {
        checkPattern(pattern);
        checkArgumentNotNullOrEmpty(types);

        ParsedUrl parsedUrl = ParsedUrl.parseUrl(pattern);
        List<ParsedUrl.NameValuePair> params = parsedUrl.getParameters();
        StringBuilder pathPatternBuilder = new StringBuilder("^");
        pathPatternBuilder.append(parsedUrl.getPath());

        // Replacing path placeholders for its names
        int varsIndex = pathPatternBuilder.indexOf(VAR_MARKER);
        int typesIndex = 0;
        while (varsIndex >= 0) {
            checkState(typesIndex < types.length, "There are more variables in the pattern than types");

            String groupPattern = types[typesIndex].getGroupPattern();
            pathPatternBuilder.replace(varsIndex, varsIndex + VAR_MARKER.length(), groupPattern);

            typesIndex++;
            varsIndex = pathPatternBuilder.indexOf(VAR_MARKER, varsIndex + groupPattern.length());
        }

        // Setting up parameters
        VarType[] pathTypes = Arrays.copyOfRange(types, 0, typesIndex);
        VarType[] paramTypes = Arrays.copyOfRange(types, typesIndex, types.length);
        checkState(pathTypes.length + params.size() == types.length, "The number of variables and types do not match");
        String[] paramNames = new String[paramTypes.length];

        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = params.get(i).getName();
        }

        pathPatternBuilder.append("$");
        return new RestParser(Pattern.compile(pathPatternBuilder.toString()), pathTypes, paramNames, paramTypes);
    }

    private static void checkPattern(String pattern) {
        checkArgumentNotNullOrEmpty(pattern);
        checkArgument(!pattern.endsWith("/"), "Patterns must not end with /");
        checkArgument(!pattern.contains("#"), "Patterns must not contain fragments (#)");
    }

    private RestParser(Pattern pathPattern, VarType[] pathTypes, String[] paramNames, VarType[] paramTypes) {
        this.pathPattern = pathPattern;
        this.pathTypes = pathTypes;
        this.paramNames = paramNames;
        this.paramTypes = paramTypes;
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
            return parseError("Url is null");
        }

        Matcher matcher = pathPattern.matcher(path);
        if (!matcher.find()) {
            return parseError("Path '" + path + "' does not match the pattern '" + pathPattern + "'");
        }

        int numVars = pathTypes.length + paramTypes.length;
        Object[] varValues = new Object[numVars];
        VarType[] varTypes = new VarType[numVars];

        // Processing path variables
        for (int i = 0; i < pathTypes.length; i++) {
            VarType type = pathTypes[i];
            varTypes[i] = type;
            varValues[i] = type.convert(matcher.group(type.getId()));
        }

        // Processing param variables
        List<ParsedUrl.NameValuePair> parameters = parseParams(queryString);
        for (int i = 0; i < paramNames.length; i++) {

            String paramName = paramNames[i];
            for (int j = 0; j < parameters.size(); j++) {

                ParsedUrl.NameValuePair valuePair = parameters.get(j);
                if (paramName.equals(valuePair.getName())) {

                    int varPos = pathTypes.length + i;
                    VarType type = paramTypes[i];
                    if (type.matches(valuePair.getValue())) {
                        varTypes[varPos] = type;
                        varValues[varPos] = type.convert(valuePair.getValue());
                    } else {
                        return parseError("Parameter '" + paramName+ "' does not match the pattern '" +
                                type.getGroupPattern() + "'");
                    }
                    break;
                }
            }
        }

        return parseResult(varTypes, varValues);
    }
}
