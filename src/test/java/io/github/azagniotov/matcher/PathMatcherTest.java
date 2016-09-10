package io.github.azagniotov.matcher;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathMatcherTest {

    @Test
    public void shouldMatchFollowingUrlPatterns() throws Exception {
        final PathMatcher pathMatcher = new PathMatcher();

        assertTrue(pathMatcher.isMatch("/resource/1", "/resource/1"));
        assertTrue(pathMatcher.isMatch("/resource/1", "/resource/*"));
        assertTrue(pathMatcher.isMatch("/resource/1/", "/resource/*/"));

        assertTrue(pathMatcher.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource/*"));
        assertTrue(pathMatcher.isMatch("/top-resource/999999/resource/8888888/sub-resource/77777777", "/top-resource/*/resource/*/sub-resource/*"));
        assertTrue(pathMatcher.isMatch("/this/is/protected/path/secret.html", "/*/*/*/*/secret.html"));
        assertTrue(pathMatcher.isMatch("/this/is/protected/path/secret.html", "/*/*/*/*/*.html"));
        assertTrue(pathMatcher.isMatch("/this/is/protected/path", "/*/*/*/*"));

        assertTrue(pathMatcher.isMatch("/some/directory/contract-one.pdf", "/some/directory/*.pdf"));
        assertTrue(pathMatcher.isMatch("/some/directory/contract-two.pdf", "/some/directory/*.pdf"));
        assertTrue(pathMatcher.isMatch("/some/directory/australia.pdf", "/some/directory/a*.pdf"));
        assertTrue(pathMatcher.isMatch("/some/directory/america.pdf", "/some/directory/a*.pdf"));

        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/partnership/legal.pdf"));
        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/legal.pdf"));
        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/amazon/**/*.pdf"));
        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**.pdf"));
        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership.pdf", "/com/amazon/**"));
        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "**"));
        assertTrue(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "**/*.pdf"));

        assertTrue(pathMatcher.isMatch("com/hello.jsp", "com/*.jsp"));
        assertTrue(pathMatcher.isMatch("com/example/product/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcher.isMatch("com/example/clients/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcher.isMatch("com/final/clients/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcher.isMatch("org/springframework/web/views/hello.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcher.isMatch("org/springframework/web/default.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcher.isMatch("org/springframework/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcher.isMatch("org/springframework/testing/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcher.isMatch("org/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcher.isMatch("org/springframework/default.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcher.isMatch("com/test.jsp", "com/t?st.jsp"));
        assertTrue(pathMatcher.isMatch("com/tast.jsp", "com/t?st.jsp"));
        assertTrue(pathMatcher.isMatch("com/txst.jsp", "com/t?st.jsp"));
    }

    @Test
    public void shouldNotMatchFollowingUrlPatterns() throws Exception {
        final PathMatcher pathMatcher = new PathMatcher();

        assertFalse(pathMatcher.isMatch("/resource/1/", "/resource/*"));
        assertFalse(pathMatcher.isMatch("/resource/1", "*"));
        assertFalse(pathMatcher.isMatch("resource/1", "/resource/*"));
        assertFalse(pathMatcher.isMatch("/resource", "/resource/*"));
        assertFalse(pathMatcher.isMatch("/resource/1", "/resource/*/"));
        assertFalse(pathMatcher.isMatch("/resource/1", "/resource/2"));
        assertFalse(pathMatcher.isMatch("/resource/1", "/comm*"));

        assertFalse(pathMatcher.isMatch("/resource/1/sub-resource/3", "/resource/*/*"));
        assertFalse(pathMatcher.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*"));
        assertFalse(pathMatcher.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource"));
        assertFalse(pathMatcher.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource*"));
        assertFalse(pathMatcher.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource/"));
        assertFalse(pathMatcher.isMatch("/this/is/protected/path/secret.html", "/*/*/*/secret.html"));

        assertFalse(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "*"));
        assertFalse(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/pa*ip/legal.pdf"));
        assertFalse(pathMatcher.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/pa**ip/legal.pdf"));
        assertFalse(pathMatcher.isMatch("/some/directory/contract-56789-pack.pdf", "/some/directory/contract*pack.pdf"));
        assertFalse(pathMatcher.isMatch("com/example/product/test.jsp", "com/**test.jsp"));

    }
}

