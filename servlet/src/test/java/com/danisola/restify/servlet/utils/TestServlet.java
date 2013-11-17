package com.danisola.restify.servlet.utils;

import com.danisola.restify.servlet.*;
import com.danisola.restify.url.RestUrl;
import com.danisola.restify.url.types.VarType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends RestServlet implements Delete, Get, Head, Options, Post, Put, Trace {

    private final TestCallback callback;

    public TestServlet(TestCallback callback, String pattern, VarType... types) {
        super(pattern, types);

        this.callback = callback;
    }

    @Override
    public void delete(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Delete);
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Get);
    }

    @Override
    public void head(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Head);
    }

    @Override
    public void options(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Options);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Post);
    }

    @Override
    public void put(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Put);
    }

    @Override
    public void trace(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException {
        callback.onMethodCalled(Method.Trace);
    }

    @Override
    protected void onInvalidUrl(RestUrl url) {
        callback.onInvalidUrl(url);
    }
}
