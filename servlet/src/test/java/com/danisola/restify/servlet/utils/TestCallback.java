package com.danisola.restify.servlet.utils;

import com.danisola.restify.url.RestUrl;

public interface TestCallback {

    void onMethodCalled(Method method);

    void onInvalidUrl(RestUrl url);
}
