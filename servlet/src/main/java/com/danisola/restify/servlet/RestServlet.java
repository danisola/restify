package com.danisola.restify.servlet;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import com.danisola.restify.url.types.VarType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.danisola.restify.url.RestParserFactory.parser;

public abstract class RestServlet extends HttpServlet {

    protected final RestParser parser;

    protected RestServlet(String pattern, VarType... types) {
        this.parser = parser(pattern, types);
    }

    @Override
    public final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Get) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Get) this).get(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doGet(req, resp);
            }
        }
    }

    @Override
    public final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Post) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Post) this).post(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doPost(req, resp);
            }
        }
    }

    @Override
    public final void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Put) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Put) this).put(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doPut(req, resp);
            }
        }
    }

    @Override
    public final void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Delete) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Delete) this).delete(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doDelete(req, resp);
            }
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Head) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Head) this).head(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doHead(req, resp);
            }
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Options) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Options) this).options(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doOptions(req, resp);
            }
        }
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this instanceof Trace) {
            RestUrl url = parse(req);
            if (url.isValid()) {
                ((Trace) this).trace(req, resp, url);
            } else {
                onInvalidUrl(url);
                super.doTrace(req, resp);
            }
        }
    }

    protected void onInvalidUrl(RestUrl url) {
        // Do nothing
    }

    private RestUrl parse(HttpServletRequest req) {
        return parser.parse(req.getRequestURI(), req.getQueryString());
    }
}
