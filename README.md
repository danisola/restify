# URL-RESTify #

URL-RESTify is a lightweight library (16KB!) to easily validate and access the variables of any RESTful URL.

## Usage ##

Usage example within a Servlet:

```
public class MyServlet extends HttpServlet {

  RestParser parser = parser("/countries/{}/cities?population={}",
          strVar("countryId"), intVar("population"));

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    RestUrl restUrl = parser.parse(req.getRequestURL(), req.getQueryString());
    if (!restUrl.isValid()) {
      resp.setStatus(SC_BAD_REQUEST); // Use restUrl.errorMessage() to debug
      return;
    }

    String countryId = restUrl.variable("countryId");
    Integer population = restUrl.variable("population");
    // Your code here
  }
}
```

Example composing variables and using a regex:

```
  RestParser parser = parser("/battleship/shoot?square={}{}",
          regexStrVar("char", "[A-L]"), intVar("num"));

  RestUrl url = parser.parse("http://www.games.com/battleship/shoot?square=B7");
  String xCoord = url.variable("char"); // B
  Integer yCoord = url.variable("num"); // 7
```

See more usage examples in the [tests](https://bitbucket.org/danisola/url-restify/src/master/src/test/java/com/danisola/urlrestify/RestUrlTest.java).

## FAQ ##

* **Why would I use URL-RESTify instead of Spring Framework or Jersey?** Sometimes we're forced to use plain Servlets or we just don't need all the features of a full-blown framework. Examples: when dealing with legacy web services or when creating projects for Google App Engine.
* **Which variable types can I use?** Boolean, Double, Float, Integer, Long, String and UUID. It is also very easy to force String variables to match a given regex.
* **Can I add more types?** Yes, you just have to extend AbstractVarType. Check this [package](https://bitbucket.org/danisola/url-restify/src/master/src/main/java/com/danisola/urlrestify/types) for examples.
* **Is it fast?** This depends on many variables (hardware, JVM, etc.), but I would say that is fast enough. These are the [microbenchmark](https://bitbucket.org/danisola/url-restify/src/master/src/test/java/com/danisola/urlrestify/UrlRestifyBenchmark.java) results on my development machine:

```
     benchmark   us linear runtime
CreatingParser 6.81 ==============================
       Parsing 1.67 =======
```


## How to build ##

You require the following:

* Latest stable  JDK 7
* Latest stable Apache Maven

Just download the code and do `mvn clean install`. You will find the jar file inside `target/`.
