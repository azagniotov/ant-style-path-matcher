# Ant-style Path Matcher
Consise, recursive  & efficient path matcher implementation for an Ant-style path pattern matching algorithm. There are two
implementations available:

1. [AntPathMatcher](../master/src/main/java/io/github/azagniotov/matcher/AntPathMatcher.java)
2. [AntPathMatcherArrays](../master/src/main/java/io/github/azagniotov/matcher/AntPathMatcherArrays.java) that does not create substrings during matching

The matcher matches URLs using the following rules:
* `?` matches one character
* `*` matches zero or more characters
* `**` matches zero or more **_directories_** in a path

#### _Complexity_
The matching algorithm of [AntPathMatcherArrays](../master/src/main/java/io/github/azagniotov/matcher/AntPathMatcherArrays.java) uses a `O(N)` space complexity, since the algorithm does not create
substrings (unlike [AntPathMatcher](../master/src/main/java/io/github/azagniotov/matcher/AntPathMatcher.java)) and recurses by moving pointers on the original char arrays

#### _Examples_

* `com/t?st.jsp` - matches `com/test.jsp` but also `com/tast.jsp` or `com/txst.jsp`
* `com/*.jsp` - matches all `.jsp` files in the `com` directory
* `com/**/test.jsp` - matches all `test.jsp` files underneath the `com` path
* `org/springframework/**/*.jsp` - matches all `.jsp` files underneath the `org/springframework` path
* `org/**/servlet/bla.jsp` - matches `org/springframework/servlet/bla.jsp` but also `org/springframework/testing/servlet/bla.jsp` and `org/servlet/bla.jsp`

#### _Configuration_
The instances of this path matcher can be configured via its `Builder` to:

1. Use a custom path separator. The default is `/` character
2. Ignore character case during comparison. The default is `false` - do not ignore
3. Match start. Determines whether the pattern at least matches as far as the given base path goes, assuming that a full path may then match as well. The default is `false` - do a full match
4. Specify whether to trim tokenized paths. The default is `false` - do not trim

#### _Testing_
The matcher has been thoroughly tested. The unit test cases have been kindly borrowed from Spring's `AntPathMatcherTests` in order to achieve matcher behaviour parity, you can refer to [AntPathMatcherTest](../master/src/test/java/io/github/azagniotov/matcher/AntPathMatcherTest.java) to view the test cases

#### _Credits_
* Part of this README description has been kindly borrowed from Spring's `AntPathMatcher`
* The path matcher configuration options have been inspired by Spring's `AntPathMatcher`

#### _License_
MIT
