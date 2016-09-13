# Ant-style Path Matcher
Consise & efficient path matcher implementation for an Ant-style path patterns

The mapping matches URLs using the following rules:
* `?` matches one character
* `*` matches zero or more characters
* `**` matches zero or more **_directories_** in a path

#### _Examples_

* `com/t?st.jsp` - matches `com/test.jsp` but also `com/tast.jsp` or `com/txst.jsp`
* `com/*.jsp` - matches all `.jsp` files in the `com` directory
* `com/**/test.jsp` - matches all `test.jsp` files underneath the `com` path
* `org/springframework/**/*.jsp` - matches all `.jsp` files underneath the `org/springframework` path
* `org/**/servlet/bla.jsp` - matches `org/springframework/servlet/bla.jsp` but also `org/springframework/testing/servlet/bla.jsp` and `org/servlet/bla.jsp`

#### _Configuration_
The instances of path matcher can be configured via its `Builder` to:

1. Use a custom path separator. The default is `/` character
2. Ignore character case during comparison. The default is `false` - do not ignore
3. Match start. Determines whether the pattern at least matches as far as the given base path goes, assuming that a full path may then match as well. The default is `false` - do a full match
4. Specify whether to trim tokenized paths. The default is `false` - do not trim


**Note**: a pattern and a path must both be absolute or must both be relative in order for the two to match. Therefore it is recommended that users of this implementation to sanitize patterns in order to prefix them with `/` as it makes sense in the context in which they're used.


##### Credits
* Part of this README description has been kindly borrowed from http://docs.spring.io/.
* Path matcher configuration options have been inspired by Spring's `AntPathMatcher`
* The unit test cases have been kindly borrowed from Spring's `AntPathMatcherTests` in order to achieve path matcher behaviour parity
