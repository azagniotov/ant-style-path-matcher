package io.github.azagniotov.matcher;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PathMatcherArraysTest {

    @Test
    public void shouldMatchFollowingUrlPatterns() throws Exception {
        final PathMatcherArrays pathMatcherArrays = new PathMatcherArrays();

        assertTrue(pathMatcherArrays.isMatch("/resource/1", "/resource/1"));
        assertTrue(pathMatcherArrays.isMatch("/resource/1", "/resource/*"));
        assertTrue(pathMatcherArrays.isMatch("/resource/1/", "/resource/*/"));

        assertTrue(pathMatcherArrays.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource/*"));
        assertTrue(pathMatcherArrays.isMatch("/top-resource/999999/resource/8888888/sub-resource/77777777", "/top-resource/*/resource/*/sub-resource/*"));
        assertTrue(pathMatcherArrays.isMatch("/this/is/protected/path/secret.html", "/*/*/*/*/secret.html"));
        assertTrue(pathMatcherArrays.isMatch("/this/is/protected/path/secret.html", "/*/*/*/*/*.html"));
        assertTrue(pathMatcherArrays.isMatch("/this/is/protected/path", "/*/*/*/*"));

        assertTrue(pathMatcherArrays.isMatch("/some/directory/contract-one.pdf", "/some/directory/*.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/some/directory/contract-two.pdf", "/some/directory/*.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/some/directory/australia.pdf", "/some/directory/a*.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/some/directory/america.pdf", "/some/directory/a*.pdf"));

        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/partnership/legal.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/legal.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/amazon/**/*.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**.pdf"));
        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership.pdf", "/com/amazon/**"));
        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "**"));
        assertTrue(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "**/*.pdf"));

        assertTrue(pathMatcherArrays.isMatch("com/hello.jsp", "com/*.jsp"));
        assertTrue(pathMatcherArrays.isMatch("com/example/product/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcherArrays.isMatch("com/example/clients/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcherArrays.isMatch("com/final/clients/test.jsp", "com/**/test.jsp"));
        assertTrue(pathMatcherArrays.isMatch("org/springframework/web/views/hello.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcherArrays.isMatch("org/springframework/web/default.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcherArrays.isMatch("org/springframework/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcherArrays.isMatch("org/springframework/testing/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcherArrays.isMatch("org/servlet/bla.jsp", "org/**/servlet/bla.jsp"));
        assertTrue(pathMatcherArrays.isMatch("org/springframework/default.jsp", "org/springframework/**/*.jsp"));
        assertTrue(pathMatcherArrays.isMatch("com/test.jsp", "com/t?st.jsp"));
        assertTrue(pathMatcherArrays.isMatch("com/tast.jsp", "com/t?st.jsp"));
        assertTrue(pathMatcherArrays.isMatch("com/txst.jsp", "com/t?st.jsp"));
    }

    @Test
    public void shouldNotMatchFollowingUrlPatterns() throws Exception {
        final PathMatcherArrays pathMatcherArrays = new PathMatcherArrays();

        assertFalse(pathMatcherArrays.isMatch("/resource/1/", "/resource/*"));
        assertFalse(pathMatcherArrays.isMatch("/resource/1", "*"));
        assertFalse(pathMatcherArrays.isMatch("resource/1", "/resource/*"));
        assertFalse(pathMatcherArrays.isMatch("/resource", "/resource/*"));
        assertFalse(pathMatcherArrays.isMatch("/resource/1", "/resource/*/"));
        assertFalse(pathMatcherArrays.isMatch("/resource/1", "/resource/2"));
        assertFalse(pathMatcherArrays.isMatch("/resource/1", "/comm*"));

        assertFalse(pathMatcherArrays.isMatch("/resource/1/sub-resource/3", "/resource/*/*"));
        assertFalse(pathMatcherArrays.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*"));
        assertFalse(pathMatcherArrays.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource"));
        assertFalse(pathMatcherArrays.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource*"));
        assertFalse(pathMatcherArrays.isMatch("/top-resource/1/resource/2/sub-resource/3", "/top-resource/*/resource/*/sub-resource/"));
        assertFalse(pathMatcherArrays.isMatch("/this/is/protected/path/secret.html", "/*/*/*/secret.html"));

        assertFalse(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "*"));
        assertFalse(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/pa*ip/legal.pdf"));
        assertFalse(pathMatcherArrays.isMatch("/com/amazon/contracts/client/partnership/legal.pdf", "/com/**/pa**ip/legal.pdf"));
        assertFalse(pathMatcherArrays.isMatch("/some/directory/contract-56789-pack.pdf", "/some/directory/contract*pack.pdf"));
        assertFalse(pathMatcherArrays.isMatch("com/example/product/test.jsp", "com/**test.jsp"));

    }
}

