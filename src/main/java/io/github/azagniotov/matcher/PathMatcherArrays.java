package io.github.azagniotov.matcher;

/**
 * Path matcher implementation for Ant-style path patterns. This implementation matches URLs using the following rules:
 * <p/>
 * '?' - matches one character
 * '*' - matches zero or more characters
 * '**' - matches zero or more directories in a path
 *
 * This implementation has space complexity of O(n), since it passes around char arrays and pointers
 * instead of creating sub-strings. {@link io.github.azagniotov.matcher.PathMatcherStrings}
 */
public class PathMatcherArrays {

    private static final char ASTERISK = '*';
    private static final char QUESTION = '?';

    public boolean isMatch(final String path, final String pattern) {
        return isMatch(path.toCharArray(), 0, pattern.toCharArray(), 0);
    }

    private boolean isMatch(final char[] path, final int pathPointer, final char[] pattern, final int patternPointer) {
        if (pattern.length == patternPointer) {
            return path.length == pathPointer;
        }

        final char patternStart = pattern[patternPointer];
        if (patternStart == ASTERISK) {

            if (doubleAsteriskMatch(path, pathPointer, pattern, patternPointer)) {
                return true;
            }

            int newPathPointer = movePointer(path, pathPointer);
            return isMatch(path, newPathPointer, pattern, patternPointer + 1);
        }

        return (pathPointer < path.length) && (path[pathPointer] == patternStart || patternStart == QUESTION)
                && isMatch(path, pathPointer + 1, pattern, patternPointer + 1);
    }

    private boolean doubleAsteriskMatch(final char[] path, final int pathPointer, final char[] pattern, final int patternPointer) {
        if (pattern.length - patternPointer == 1 || pattern[patternPointer + 1] != ASTERISK) {
            return false;
        } else if (pattern.length - patternPointer == 2 || isMatch(path, pathPointer, pattern, patternPointer + 3)) {
            return true;
        }

        int newPathPointer = movePointer(path, pathPointer);
        return (isMatch(path, newPathPointer, pattern, patternPointer + 2) ||
                (newPathPointer < path.length && doubleAsteriskMatch(path, newPathPointer + 1, pattern, patternPointer)));

    }

    private int movePointer(final char[] path, final int pathPointer) {
        int pointer = pathPointer;
        while (pointer < path.length && (path[pointer] != '/' && path[pointer] != '.')) {
            pointer++;
        }
        return pointer;
    }
}

