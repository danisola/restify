package com.danisola.restify.servlet;

import com.danisola.restify.servlet.utils.TestCallback;
import com.danisola.restify.servlet.utils.Method;
import com.danisola.restify.servlet.utils.TestServlet;
import com.danisola.restify.url.RestUrl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

import static com.danisola.restify.servlet.utils.Method.Delete;
import static com.danisola.restify.servlet.utils.Method.Get;
import static com.danisola.restify.servlet.utils.Method.Head;
import static com.danisola.restify.servlet.utils.Method.Options;
import static com.danisola.restify.servlet.utils.Method.Post;
import static com.danisola.restify.servlet.utils.Method.Put;
import static com.danisola.restify.servlet.utils.Method.Trace;
import static com.danisola.restify.url.types.IntVar.intVar;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RestServletTest {

    TestServlet servlet;

    HttpServletRequest request = mockRequest("/users/123");
    HttpServletResponse response = mockResponse();
    @Mock TestCallback callback;

    @Before
    public void setUp() {
        servlet = new TestServlet(callback, "/users/{}", intVar("userId"));
    }

    @Test
    public void whenUrlIsValidThenDeleteIsCalled() throws ServletException, IOException {
        servlet.doDelete(request, response);
        verify(callback).onMethodCalled(Delete);
    }

    @Test
    public void whenUrlIsValidThenGetIsCalled() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(callback).onMethodCalled(Get);
    }

    @Test
    public void whenUrlIsValidThenHeadIsCalled() throws ServletException, IOException {
        servlet.doHead(request, response);
        verify(callback).onMethodCalled(Head);
    }

    @Test
    public void whenUrlIsValidThenOptionsIsCalled() throws ServletException, IOException {
        servlet.doOptions(request, response);
        verify(callback).onMethodCalled(Options);
    }

    @Test
    public void whenUrlIsValidThenPostIsCalled() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(callback).onMethodCalled(Post);
    }

    @Test
    public void whenUrlIsValidThenPutIsCalled() throws ServletException, IOException {
        servlet.doPut(request, response);
        verify(callback).onMethodCalled(Put);
    }

    @Test
    public void whenUrlIsValidThenTraceIsCalled() throws ServletException, IOException {
        servlet.doTrace(request, response);
        verify(callback).onMethodCalled(Trace);
    }

    @Test
    public void whenUrlIsInvalidThenOnInvalidUrlIsCalled() throws ServletException, IOException {
        HttpServletRequest badRequest = mockRequest("/users/john"); // Servlet expects an int ID

        servlet.doDelete(badRequest, response);
        servlet.doGet(badRequest, response);
        servlet.doHead(badRequest, response);
        servlet.doOptions(badRequest, response);
        servlet.doPost(badRequest, response);
        servlet.doPut(badRequest, response);
        servlet.doTrace(badRequest, response);

        // The default doHead() method calls doGet() internally, so we get an extra onInvalidUrl() call
        verify(callback, times(Method.values().length + 1)).onInvalidUrl(any(RestUrl.class));
    }

    private HttpServletRequest mockRequest(String uri) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getProtocol()).thenReturn("HTTP/1.1");
        when(request.getRequestURI()).thenReturn(uri);

        Enumeration headerNames = mock(Enumeration.class);
        when(headerNames.hasMoreElements()).thenReturn(false);
        when(request.getHeaderNames()).thenReturn(headerNames);

        return request;
    }

    private HttpServletResponse mockResponse() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        try {
            when(response.getOutputStream()).thenReturn(outputStream);
        } catch (IOException e) {
            e.printStackTrace(); // Not going to happen
        }
        return response;
    }
}
