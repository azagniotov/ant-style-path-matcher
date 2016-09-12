package io.github.azagniotov.matcher;

/**
 * Path matcher implementation for Ant-style path patterns. This implementation matches URLs using the following rules:
 * <p/>
 * '?' - matches one character
 * '*' - matches zero or more characters
 * '**' - matches zero or more directories in a path
 */
public class AntPathMatcher {

    private static final char ASTERISK = '*';
    private static final char QUESTION = '?';
    public static final char PATH_SEPARATOR = '/';

    public boolean isMatch(final String pattern, final String path) {
        if (pattern.isEmpty()) {
            return path.isEmpty();
        } else if (pattern.length() == 1 && pattern.charAt(0) == ASTERISK) {
            return path.isEmpty() || path.charAt(0) != PATH_SEPARATOR && isMatch(pattern, path.substring(1));
        } else if (path.isEmpty() && pattern.charAt(0) == PATH_SEPARATOR) {
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

        return !path.isEmpty() && (path.charAt(0) == patternStart || patternStart == QUESTION)
                && isMatch(pattern.substring(1), path.substring(1));
    }

    private boolean doubleAsteriskMatch(final String pattern, final String path) {
        if (pattern.length() == 1 || pattern.charAt(1) != ASTERISK) {
            return false;
        } else if (pattern.length() == 2 || isMatch(pattern.substring(3), path)) {
            return true;
        }

        int pointer = movePointer(path);
        return (isMatch(pattern.substring(2), path.substring(pointer)) ||
                (pointer < path.length() && doubleAsteriskMatch(pattern, path.substring(pointer + 1))));

    }

    private int movePointer(final String path) {
        int pointer = 0;
        while (pointer < path.length() && (path.charAt(pointer) != PATH_SEPARATOR)) {
            pointer++;
        }
        return pointer;
    }
}
