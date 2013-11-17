# Servlet-RESTify #

Servlet-RESTify is a lightweight library to easily validate and access the variables of any RESTful URL.

## Usage

To create your Servlets using Servlet-RESTify, you only have to extend [RestServlet]
(src/main/java/com/danisola/restify/servlet/RestServlet.java) and implement all the methods you want to serve.

In the constructor you have to provide the pattern and variables of the URL that your Servlet serves. The syntax is
the same used in [URL-RESTify](../url/), so you can use the same types, regexes, etc.

Here is an example Servlet that implements the Get and Post methods:

```java
public class UserServlet extends RestServlet implements Get, Post {

  public UserServlet() {
    super("/users/{}", longVar("userId"));
  }

  @Override
  public void get(HttpServletRequest req, HttpServletResponse res, RestUrl url) {
    Long userId = url.variable("userId");
    // Retrieve the user
  }

  @Override
  public void post(HttpServletRequest req, HttpServletResponse res, RestUrl url) {
    Long userId = url.variable("userId");
    // Update the user
  }
}
```

Those methods will only be called if the Servlet is hit and the URL matches the pattern and variables that you have
specified in the constructor. If you want to debug why a URL is considered invalid,
just override the optional `onInvalidUrl` method:


```java
public class ResourceServlet extends RestServlet implements Get {

  public ResourceServlet() {
    super("/resources/{}", uuidVar("resourceId"));
  }

  @Override
  public void get(HttpServletRequest req, HttpServletResponse res, RestUrl url) {
    UUID resourceId = url.variable("resourceId");
    // Retrieve the resource
  }

  @Override
  protected void onInvalidUrl(RestUrl url) {
    System.out.println("Invalid URL: " + url.errorMessage());
  }
}
```