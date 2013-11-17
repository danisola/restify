# RESTify

RESTify is a set of lightweight libraries to easily validate and access the variables of any RESTful URL. All them
have no external dependencies (not even logging!), so you only have to add the jar to the classpath and you are ready to go.

Currently RESTify has two sub-projects: URL-RESTify and Servlet-RESTify.


## URL-RESTify

Core library to deal with URLs directly, packed in 21KB! Here's an example of how you can use it:

```java
RestParser parser = parser("/{}/{}", strVar("countryId", "[a-z]{2}"), intVar("cityId"));

RestUrl url = parser.parse("http://www.world.com/uk/452");
String countryId = url.variable("countryId"); // "uk"
Integer cityId = url.variable("cityId"); // 452
```

See more examples and information [here](url/).

## Servlet-RESTify

Small wrapper around URL-Restify to simplify its usage in a Servlet environment. Example usage:

```java
public class CityServlet extends RestServlet implements Get {

  public CityServlet() {
    super("/{}/{}", strVar("countryId", "[a-z]{2}"), intVar("cityId"));
  }
  
  @Override
  public void get(HttpServletRequest req, HttpServletResponse res, RestUrl url) {
    String countryId = url.variable("countryId");
    Integer cityId = url.variable("cityId");
    // Your code here
  }
}
```

See more examples and information [here](servlet/).

## FAQ

* **Why would I use RESTify instead of Spring Framework or Jersey?** Sometimes we're forced to use plain Servlets or
 we just don't need all the features of a full-blown framework. Examples: when dealing with legacy web services or
 when creating projects for Google App Engine.
* **Which variable types can I use?** Boolean, Double, Float, Integer, Long, String, UUID, String[] and Integer[].
 It is also very easy to force variables to match a given regex.
* **Can I add more types?** Yes, you just have to extend AbstractVarType. Check this
 [package](url/src/main/java/com/danisola/restify/url/types) for examples.
* **Are they fast?** This depends on many variables (hardware, JVM, etc.), but I would say that is fast enough. These
 are the [microbenchmark](url/src/test/java/com/danisola/restify/url/UrlRestifyBenchmark.java) results on my development machine:

```
     benchmark   us linear runtime
CreatingParser 9.70 ==============================
       Parsing 1.47 ====
```

## How to build

You require the following:

* Latest stable  JDK 7
* Latest stable Apache Maven

Just download the code and do `mvn clean package`. You will find the jar file inside `target/` directory of each
sub-project.
