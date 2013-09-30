package com.danisola.urlrestify.matchers;

import com.danisola.urlrestify.RestUrl;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class IsInvalid extends TypeSafeMatcher<RestUrl> {

    @Override
    public boolean matchesSafely(RestUrl restUrl) {
        return !restUrl.isValid() && restUrl.errorMessage() != null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("is invalid");
    }

    public static IsInvalid isInvalid() {
        return new IsInvalid();
    }

    private IsInvalid() {
    }
}
