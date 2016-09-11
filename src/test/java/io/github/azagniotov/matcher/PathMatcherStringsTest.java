package io.github.azagniotov.matcher;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathMatcherStringsTest {

    @Test
    public void shouldMatchFollowingUrlPatterns() throws Exception {
        final PathMatcherStrings pathMatcherStrings = new PathMatcherStrings();

        assertTrue(pathMatcherStrings.isMatch("/resource/1", "/resource/1"));
        assertTrue(pathMatcherStrings.isMatch("/resource/1", "/resource/*"));
        assertTrue(pathMatcherStrings.isMatch("/resource/1/", "/resource/*/"));

        assertTrue(pathMatcherStrings.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource/*"));
        assertTrue(pathMatcherStrings.isMatch("/top-resource/999999/resource/8888888/sub-resource/77777777", "/top-resource/*/resource/*/sub-resource/*"));
        assertTrue(pathMatcherStrings.isMatch("/this/is/protected/path/secret.html", "/*/*/*/*/secret.html"));
        assertTrue(pathMatcherStrings.isMatch("/this/is/protected/path/secret.html", "/*/*/*/*/*.html"));
        assertTrue(pathMatcherStrings.isMatch("/this/is/protected/path", "/*/*/*/*"));

        assertTrue(pathMatcherStrings.isMatch("/some/directory/contract-one.pdf", "/some/directory/*.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/some/directory/contract-two.pdf", "/some/directory/*.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/some/directory/australia.pdf", "/some/directory/a*.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/some/directory/america.pdf", "/some/directory/a*.pdf"));

        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/partnership/legal.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/legal.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/amazon/**/*.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**.pdf"));
        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership.pdf", "/com/amazon/**"));
        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "**"));
        assertTrue(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "**/*.pdf"));

        assertTrue(pathMatcherStrings.isMatch("com/hello.jsp", "com/*.jsp"));
        assertTrue(pathMatcherStrings.isMatch("com/example/product/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcherStrings.isMatch("com/example/clients/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcherStrings.isMatch("com/final/clients/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcherStrings.isMatch("org/springframework/web/views/hello.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcherStrings.isMatch("org/springframework/web/default.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcherStrings.isMatch("org/springframework/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcherStrings.isMatch("org/springframework/testing/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcherStrings.isMatch("org/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcherStrings.isMatch("org/springframework/default.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcherStrings.isMatch("com/test.jsp", "com/t?st.jsp"));
        assertTrue(pathMatcherStrings.isMatch("com/tast.jsp", "com/t?st.jsp"));
        assertTrue(pathMatcherStrings.isMatch("com/txst.jsp", "com/t?st.jsp"));
    }

    @Test
    public void shouldNotMatchFollowingUrlPatterns() throws Exception {
        final PathMatcherStrings pathMatcherStrings = new PathMatcherStrings();

        assertFalse(pathMatcherStrings.isMatch("/resource/1/", "/resource/*"));
        assertFalse(pathMatcherStrings.isMatch("/resource/1", "*"));
        assertFalse(pathMatcherStrings.isMatch("resource/1", "/resource/*"));
        assertFalse(pathMatcherStrings.isMatch("/resource", "/resource/*"));
        assertFalse(pathMatcherStrings.isMatch("/resource/1", "/resource/*/"));
        assertFalse(pathMatcherStrings.isMatch("/resource/1", "/resource/2"));
        assertFalse(pathMatcherStrings.isMatch("/resource/1", "/comm*"));

        assertFalse(pathMatcherStrings.isMatch("/resource/1/sub-resource/3", "/resource/*/*"));
        assertFalse(pathMatcherStrings.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*"));
        assertFalse(pathMatcherStrings.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource"));
        assertFalse(pathMatcherStrings.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource*"));
        assertFalse(pathMatcherStrings.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource/"));
        assertFalse(pathMatcherStrings.isMatch("/this/is/protected/path/secret.html", "/*/*/*/secret.html"));

        assertFalse(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "*"));
        assertFalse(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/pa*ip/legal.pdf"));
        assertFalse(pathMatcherStrings.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/pa**ip/legal.pdf"));
        assertFalse(pathMatcherStrings.isMatch("/some/directory/contract-56789-pack.pdf", "/some/directory/contract*pack.pdf"));
        assertFalse(pathMatcherStrings.isMatch("com/example/product/test.jsp", "com/**test.jsp"));

    }
}

