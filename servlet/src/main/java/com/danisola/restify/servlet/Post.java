package com.danisola.restify.servlet;

import com.danisola.restify.url.RestUrl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Post {

    void post(HttpServletRequest req, HttpServletResponse res, RestUrl url) throws ServletException, IOException;
}
