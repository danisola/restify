# URL-RESTify #

URL-RESTify is a lightweight library (21KB!) to easily validate and access the variables of any RESTful URL.

## Usage ##

Usage example within a Servlet (consider using [Servlet-RESTify](../servlet/)!):

```
public class MyServlet extends HttpServlet {

  RestParser parser = parser("/countries/{}/cities?population={}",
          strVar("countryId"), intVar("population"));

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    RestUrl url = parser.parse(req.getRequestURI(), req.getQueryString());
    if (!url.isValid()) {
      resp.setStatus(SC_BAD_REQUEST); // Use url.errorMessage() to debug
      return;
    }

    String countryId = url.variable("countryId");
    Integer population = url.variable("population");
    // Your code here
  }
}
```

Example composing variables and using regexes:

```
  RestParser parser = parser("/battleship/shoot?square={}{}",
          strVar("char", "[A-L]"), intVar("num", "[0-9]"));

  RestUrl url = parser.parse("http://www.games.com/battleship/shoot?square=B7");
  String xCoord = url.variable("char"); // B
  Integer yCoord = url.variable("num"); // 7
```

Example with String array:

```
  RestParser parser = parser("/users/{}?fields={}",
          intVar("userId"), strArrayVar("fields"));

  RestUrl url = parser.parse("http://www.network.com/users/31416?fields=name,age");
  Integer userId = url.variable("userId"); // 31416
  String[] fields = url.variable("fields"); // ["name", "age"]
```

Example with optional variables:

```
  RestParser parser = parser("/cities?capital={}?",
          opt(boolVar("isCapital")));

  RestUrl url = parser.parse("http://www.country.com/cities");
  Boolean isCapitalQuery = url.variable("isCapital"); // null
```

See more usage examples in the [tests](src/test/java/com/danisola/restify/url/).