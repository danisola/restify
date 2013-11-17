package com.danisola.restify.url.matchers;

import com.danisola.restify.url.RestUrl;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsValid extends TypeSafeMatcher<RestUrl> {

    @Override
    public boolean matchesSafely(RestUrl restUrl) {
        return restUrl.isValid() && restUrl.errorMessage() == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is valid");
    }

    public static IsValid isValid() {
        return new IsValid();
    }

    private IsValid() {
    }
}
