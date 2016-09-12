package io.github.azagniotov.matcher;

/**
 * Path matcher implementation for Ant-style path patterns. This implementation matches URLs using the following rules:
 * <p/>
 * '?' - matches one character
 * '*' - matches zero or more characters
 * '**' - matches zero or more directories in a path
 * <p>
 * The instances of this class can be configured via its {@link Builder} to:
 * (1) Use a custom path separator. The default is '/' character
 * (2) Ignore character case during comparison. The default is case-sensitive
 * <p>
 * The custom path separator & ignoring character case options were inspired by Spring's AntPathMatcher
 */
@SuppressWarnings("WeakerAccess")
public class AntPathMatcher {

    private static final char ASTERISK = '*';
    private static final char QUESTION = '?';
    private static final int ASCII_CASE_DIFFERENCE = 32;

    private final char pathSeparator;
    private final boolean ignoreCase;

    private AntPathMatcher(final char pathSeparator, boolean ignoreCase) {
        this.pathSeparator = pathSeparator;
        this.ignoreCase = ignoreCase;
    }

    public boolean isMatch(final String pattern, final String path) {
        if (pattern.isEmpty()) {
            return path.isEmpty();
        } else if (pattern.length() == 1 && pattern.charAt(0) == ASTERISK) {
            return path.isEmpty() || path.charAt(0) != pathSeparator && isMatch(pattern, path.substring(1));
        } else if (path.isEmpty() && pattern.charAt(0) == pathSeparator) {
            return doubleAsteriskMatch(pattern.substring(1), path);
        }

        final char patternStart = pattern.charAt(0);
        if (patternStart == ASTERISK) {
            if (doubleAsteriskMatch(pattern, path)) {
                return true;
            }

            int start = 0;
            while (start < path.length()) {
                if (isMatch(pattern.substring(1), path.substring(start))) {
                    return true;
                }
                start++;
            }
            return isMatch(pattern.substring(1), path.substring(start));
        }

        return !path.isEmpty() && (equal(path.charAt(0), patternStart) || patternStart == QUESTION)
                && isMatch(pattern.substring(1), path.substring(1));
    }

    private boolean equal(final char pathChar, final char patternChar) {
        if (ignoreCase) {
            return pathChar == patternChar ||
                    ((pathChar > patternChar) ?
                            pathChar == patternChar + ASCII_CASE_DIFFERENCE :
                            pathChar == patternChar - ASCII_CASE_DIFFERENCE);
        }
        return pathChar == patternChar;
    }

    private boolean doubleAsteriskMatch(final String pattern, final String path) {
        if (pattern.length() == 1 || pattern.charAt(1) != ASTERISK) {
            return false;
        } else if (pattern.length() == 2 || isMatch(pattern.substring(3), path)) {
            return true;
        }

        int pointer = 0;
        while (pointer < path.length() && (path.charAt(pointer) != pathSeparator)) {
            pointer++;
        }
        return (isMatch(pattern.substring(2), path.substring(pointer)) ||
                (pointer < path.length() && doubleAsteriskMatch(pattern, path.substring(pointer + 1))));

    }

    public static final class Builder {

        private char pathSeparator = '/';
        private boolean ignoreCase = false;

        public Builder() {

        }

        public Builder withPathSeparator(final char pathSeparator) {
            this.pathSeparator = pathSeparator;
            return this;
        }

        public Builder withIgnoreCase(final boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
            return this;
        }

        public AntPathMatcher build() {
            return new AntPathMatcher(pathSeparator, ignoreCase);
        }
    }
}
