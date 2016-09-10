package io.github.azagniotov.matcher;

/**
 * PathMatcher implementation for Ant-style path patterns. This implementation matches URLs using the following rules:
 * <p>
 * '?' - matches one character
 * '*' - matches zero or more characters
 * '**' - matches zero or more directories in a path
 */
public class PathMatcher {

    private static final char ASTERISK = '*';

    public boolean isMatch(final String path, final String pattern) {
        if (pattern.isEmpty()) {
            return path.isEmpty();
        }

        final char patternStart = pattern.charAt(0);
        if (patternStart == ASTERISK) {

            if (pattern.length() > 1 && doubleAsteriskMatch(path, pattern)) {
                return true;
            }

            int pointer = movePointer(path);
            return isMatch(path.substring(pointer), pattern.substring(1));
        }

        return !path.isEmpty() && (path.charAt(0) == patternStart || patternStart == '?') && isMatch(path.substring(1), pattern.substring(1));
    }

    private boolean doubleAsteriskMatch(final String path, final String pattern) {
        if (pattern.charAt(1) != ASTERISK) {
            return false;
        } else if (pattern.length() == 2 || isMatch(path, pattern.substring(3))) {
            return true;
        }

        int pointer = movePointer(path);
        return (isMatch(path.substring(pointer), pattern.substring(2)) ||
                (pointer < path.length() && doubleAsteriskMatch(path.substring(pointer + 1), pattern)));

    }

    private int movePointer(final String endpoint) {
        int pointer = 0;
        while (pointer < endpoint.length() && (endpoint.charAt(pointer) != '/' && endpoint.charAt(pointer) != '.')) {
            pointer++;
        }
        return pointer;
    }
}

