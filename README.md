# Ant-style Path Matcher
Consise & efficient path matcher implementation for Ant-style path patterns

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

**Note**: a pattern and a path must both be absolute or must both be relative in order for the two to match. Therefore it is recommended that users of this implementation to sanitize patterns in order to prefix them with `/` as it makes sense in the context in which they're used.

##### Credits
* Part of this README description has been kindly borrowed from http://docs.spring.io/.
* Path matcher configuration options have inspired by Spring's `AntPathMatcher`
