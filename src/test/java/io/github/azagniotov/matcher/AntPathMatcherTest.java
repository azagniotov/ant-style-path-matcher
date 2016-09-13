package io.github.azagniotov.matcher;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Part of these test case have been kindly borrowed from https://github.com/spring-projects/spring-framework:
 * ../org/springframework/util/AntPathMatcherTests.java
 */
public class AntPathMatcherTest {

    @Test
    public void isMatchWithCaseSensitiveWithDefaultPathSeparator() throws Exception {

        final AntPathMatcher pathMatcher = new AntPathMatcher.Builder().build();

        // test exact matching
        assertTrue(pathMatcher.match("test", "test"));
        assertTrue(pathMatcher.match("/test", "/test"));
        assertTrue(pathMatcher.match("http://example.org", "http://example.org")); // SPR-14141
        assertFalse(pathMatcher.match("/test.jpg", "test.jpg"));
        assertFalse(pathMatcher.match("test", "/test"));
        assertFalse(pathMatcher.match("/test", "test"));

        // test matching with ?'s
        assertTrue(pathMatcher.match("t?st", "test"));
        assertTrue(pathMatcher.match("??st", "test"));
        assertTrue(pathMatcher.match("tes?", "test"));
        assertTrue(pathMatcher.match("te??", "test"));
        assertTrue(pathMatcher.match("?es?", "test"));
        assertFalse(pathMatcher.match("tes?", "tes"));
        assertFalse(pathMatcher.match("tes?", "testt"));
        assertFalse(pathMatcher.match("tes?", "tsst"));

        // test matching with *'s
        assertTrue(pathMatcher.match("*", "test"));
        assertTrue(pathMatcher.match("test*", "test"));
        assertTrue(pathMatcher.match("test*", "testTest"));
        assertTrue(pathMatcher.match("test/*", "test/Test"));
        assertTrue(pathMatcher.match("test/*", "test/t"));
        assertTrue(pathMatcher.match("test/*", "test/"));
        assertTrue(pathMatcher.match("*test*", "AnothertestTest"));
        assertTrue(pathMatcher.match("*test", "Anothertest"));
        assertTrue(pathMatcher.match("*.*", "test."));
        assertTrue(pathMatcher.match("*.*", "test.test"));
        assertTrue(pathMatcher.match("*.*", "test.test.test"));
        assertTrue(pathMatcher.match("test*aaa", "testblaaaa"));
        assertFalse(pathMatcher.match("test*", "tst"));
        assertFalse(pathMatcher.match("test*", "tsttest"));
        assertFalse(pathMatcher.match("test*", "test/"));
        assertFalse(pathMatcher.match("test*", "test/t"));
        assertFalse(pathMatcher.match("test/*", "test"));
        assertFalse(pathMatcher.match("*test*", "tsttst"));
        assertFalse(pathMatcher.match("*test", "tsttst"));
        assertFalse(pathMatcher.match("*.*", "tsttst"));
        assertFalse(pathMatcher.match("test*aaa", "test"));
        assertFalse(pathMatcher.match("test*aaa", "testblaaab"));

        // test matching with ?'s and /'s
        assertTrue(pathMatcher.match("/?", "/a"));
        assertTrue(pathMatcher.match("/?/a", "/a/a"));
        assertTrue(pathMatcher.match("/a/?", "/a/b"));
        assertTrue(pathMatcher.match("/??/a", "/aa/a"));
        assertTrue(pathMatcher.match("/a/??", "/a/bb"));
        assertTrue(pathMatcher.match("/?", "/a"));

        // test matching with **'s
        assertTrue(pathMatcher.match("/**", "/testing/testing"));
        assertTrue(pathMatcher.match("/*/**", "/testing/testing"));
        assertTrue(pathMatcher.match("/**/*", "/testing/testing"));
        assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla"));
        assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla/bla"));
        assertTrue(pathMatcher.match("/**/test", "/bla/bla/test"));
        assertTrue(pathMatcher.match("/bla/**/**/bla", "/bla/bla/bla/bla/bla/bla"));
        assertTrue(pathMatcher.match("/bla*bla/test", "/blaXXXbla/test"));
        assertTrue(pathMatcher.match("/*bla/test", "/XXXbla/test"));
        assertFalse(pathMatcher.match("/bla*bla/test", "/blaXXXbl/test"));
        assertFalse(pathMatcher.match("/*bla/test", "XXXblab/test"));
        assertFalse(pathMatcher.match("/*bla/test", "XXXbl/test"));

        assertFalse(pathMatcher.match("/????", "/bala/bla"));
        assertFalse(pathMatcher.match("/**/*bla", "/bla/bla/bla/bbb"));

        assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing/"));
        assertTrue(pathMatcher.match("/*bla*/**/bla/*", "/XXXblaXXXX/testing/testing/bla/testing"));
        assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing"));
        assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing.jpg"));

        assertTrue(pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing/"));
        assertTrue(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing"));
        assertTrue(pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing"));
        assertFalse(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing/testing"));

        assertFalse(pathMatcher.match("/x/x/**/bla", "/x/x/x/"));

        assertTrue(pathMatcher.match("/foo/bar/**", "/foo/bar")) ;

        assertTrue(pathMatcher.match("", ""));

        assertTrue(pathMatcher.match("/foo/bar/**", "/foo/bar")) ;
        assertTrue(pathMatcher.match("/resource/1", "/resource/1"));
        assertTrue(pathMatcher.match("/resource/*", "/resource/1"));
        assertTrue(pathMatcher.match("/resource/*/", "/resource/1/"));
        assertTrue(pathMatcher.match("/top-resource/*/resource/*/sub-resource/*", "/top-resource/1/resource/2/sub-resource/3"));
        assertTrue(pathMatcher.match("/top-resource/*/resource/*/sub-resource/*", "/top-resource/999999/resource/8888888/sub-resource/77777777"));
        assertTrue(pathMatcher.match("/*/*/*/*/secret.html", "/this/is/protected/path/secret.html"));
        assertTrue(pathMatcher.match("/*/*/*/*/*.html", "/this/is/protected/path/secret.html"));
        assertTrue(pathMatcher.match("/*/*/*/*", "/this/is/protected/path"));
        assertTrue(pathMatcher.match("org/springframework/**/*.jsp", "org/springframework/web/views/hello.jsp"));
        assertTrue(pathMatcher.match("org/springframework/**/*.jsp", "org/springframework/web/default.jsp"));
        assertTrue(pathMatcher.match("org/springframework/**/*.jsp", "org/springframework/default.jsp"));
        assertTrue(pathMatcher.match("org/**/servlet/bla.jsp", "org/springframework/servlet/bla.jsp"));
        assertTrue(pathMatcher.match("org/**/servlet/bla.jsp", "org/springframework/testing/servlet/bla.jsp"));
        assertTrue(pathMatcher.match("org/**/servlet/bla.jsp", "org/servlet/bla.jsp"));
    }

    @Test
    public void isMatchWithCustomSeparator() throws Exception {
        final AntPathMatcher pathMatcher = new AntPathMatcher.Builder().withPathSeparator('.').build();

        assertTrue(pathMatcher.match(".foo.bar.**", ".foo.bar")) ;
        assertTrue(pathMatcher.match(".resource.1", ".resource.1"));
        assertTrue(pathMatcher.match(".resource.*", ".resource.1"));
        assertTrue(pathMatcher.match(".resource.*.", ".resource.1."));
        assertTrue(pathMatcher.match("org.springframework.**.*.jsp", "org.springframework.web.views.hello.jsp"));
        assertTrue(pathMatcher.match("org.springframework.**.*.jsp", "org.springframework.web.default.jsp"));
        assertTrue(pathMatcher.match("org.springframework.**.*.jsp", "org.springframework.default.jsp"));
        assertTrue(pathMatcher.match("org.**.servlet.bla.jsp", "org.springframework.servlet.bla.jsp"));
        assertTrue(pathMatcher.match("org.**.servlet.bla.jsp", "org.springframework.testing.servlet.bla.jsp"));
        assertTrue(pathMatcher.match("org.**.servlet.bla.jsp", "org.servlet.bla.jsp"));
        assertTrue(pathMatcher.match("http://example.org", "http://example.org"));

        // test matching with ?'s and .'s
        assertTrue(pathMatcher.match(".?", ".a"));
        assertTrue(pathMatcher.match(".?.a", ".a.a"));
        assertTrue(pathMatcher.match(".a.?", ".a.b"));
        assertTrue(pathMatcher.match(".??.a", ".aa.a"));
        assertTrue(pathMatcher.match(".a.??", ".a.bb"));
        assertTrue(pathMatcher.match(".?", ".a"));

        // test matching with **'s
        assertTrue(pathMatcher.match(".**", ".testing.testing"));
        assertTrue(pathMatcher.match(".*.**", ".testing.testing"));
        assertTrue(pathMatcher.match(".**.*", ".testing.testing"));
        assertTrue(pathMatcher.match(".bla.**.bla", ".bla.testing.testing.bla"));
        assertTrue(pathMatcher.match(".bla.**.bla", ".bla.testing.testing.bla.bla"));
        assertTrue(pathMatcher.match(".**.test", ".bla.bla.test"));
        assertTrue(pathMatcher.match(".bla.**.**.bla", ".bla.bla.bla.bla.bla.bla"));
        assertFalse(pathMatcher.match(".bla*bla.test", ".blaXXXbl.test"));
        assertFalse(pathMatcher.match(".*bla.test", "XXXblab.test"));
        assertFalse(pathMatcher.match(".*bla.test", "XXXbl.test"));
    }

    @Test
    public void isMatchWithIgnoreCase() throws Exception {
        final AntPathMatcher pathMatcher = new AntPathMatcher.Builder().withIgnoreCase().build();

        assertTrue(pathMatcher.match("/foo/bar/**", "/FoO/baR")) ;
        assertTrue(pathMatcher.match("org/springframework/**/*.jsp", "ORG/SpringFramework/web/views/hello.JSP"));
        assertTrue(pathMatcher.match("org/**/servlet/bla.jsp", "Org/SERVLET/bla.jsp"));
        assertTrue(pathMatcher.match("/?", "/A"));
        assertTrue(pathMatcher.match("/?/a", "/a/A"));
        assertTrue(pathMatcher.match("/a/??", "/a/Bb"));
        assertTrue(pathMatcher.match("/?", "/a"));
        assertTrue(pathMatcher.match("/**", "/testing/teSting"));
        assertTrue(pathMatcher.match("/*/**", "/testing/testing"));
        assertTrue(pathMatcher.match("/**/*", "/tEsting/testinG"));
        assertTrue(pathMatcher.match("http://example.org", "HtTp://exAmple.org"));
        assertTrue(pathMatcher.match("HTTP://EXAMPLE.ORG", "HtTp://exAmple.org"));
    }

    @Test
    public void isMatchWithIgnoreCaseWithCustomPathSeparator() throws Exception {
        final AntPathMatcher pathMatcher = new AntPathMatcher.Builder()
                .withIgnoreCase()
                .withPathSeparator('.').build();

        assertTrue(pathMatcher.match(".foo.bar.**", ".FoO.baR")) ;
        assertTrue(pathMatcher.match("org.springframework.**.*.jsp", "ORG.SpringFramework.web.views.hello.JSP"));
        assertTrue(pathMatcher.match("org.**.servlet.bla.jsp", "Org.SERVLET.bla.jsp"));
        assertTrue(pathMatcher.match(".?", ".A"));
        assertTrue(pathMatcher.match(".?.a", ".a.A"));
        assertTrue(pathMatcher.match(".a.??", ".a.Bb"));
        assertTrue(pathMatcher.match(".?", ".a"));
        assertTrue(pathMatcher.match(".**", ".testing.teSting"));
        assertTrue(pathMatcher.match(".*.**", ".testing.testing"));
        assertTrue(pathMatcher.match(".**.*", ".tEsting.testinG"));
        assertTrue(pathMatcher.match("http:..example.org", "HtTp:..exAmple.org"));
        assertTrue(pathMatcher.match("HTTP:..EXAMPLE.ORG", "HtTp:..exAmple.org"));
    }
}
