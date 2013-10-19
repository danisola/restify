package com.danisola.urlrestify;

import com.danisola.urlrestify.types.VarType;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.danisola.urlrestify.preconditions.Preconditions.checkArgument;
import static com.danisola.urlrestify.preconditions.Preconditions.checkArgumentNotNullOrEmpty;
import static com.danisola.urlrestify.preconditions.Preconditions.checkState;

/**
 * Generates instances of {@link RestParser}.
 */
public class RestParserFactory {

    private static final String VAR_MARKER = "{}";
    private static final Pattern MARKER_VALIDATOR = Pattern.compile(".*?(\\{[^\\}]|[^\\{]\\}).*?");

    /**
     * Generates a {@link RestParser} given a pattern and variable types. See usage examples in <a
     * href="https://bitbucket.org/danisola/url-restify/">https://bitbucket.org/danisola/url-restify/</a>.
     *
     * <p>This method throws exceptions when the pattern or the variables are not correctly defined to enforce a
     * correct usage of the API. However, cannot protect from all possible malformed patterns,
     * make sure to test them.</p>
     *
     * @throws IllegalArgumentException if the pattern is malformed or if the pattern and types do not match
     * @throws NullPointerException if the pattern or types are null
     */
    public static RestParser parser(String pattern, VarType... types) {
        checkPattern(pattern);

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
        List<NameValuePair> params = parsedUrl.getParameters();
        ParamVar[] paramVars = new ParamVar[params.size()];
        int paramVarCounter = 0;
        for (int i = 0; i < paramVars.length; i++) {
            NameValuePair pair = params.get(i);

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
        checkArgument(!MARKER_VALIDATOR.matcher(pattern).matches(), "Braces must be closed: {}");
    }

    private RestParserFactory() {
        // Non-instantiable
    }
}
