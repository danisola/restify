package com.danisola.restify.url.types;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import org.junit.Test;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.matchers.IsInvalid.isInvalid;
import static com.danisola.restify.url.matchers.IsValid.isValid;
import static com.danisola.restify.url.types.FloatVar.floatVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FloatVarTest {

    @Test
    public void whenKeyIsWellSetThenValueIsCorrect() {
        RestParser parser = parser("/users/{}", floatVar("userId"));
        Float userId = Float.MAX_VALUE;
        RestUrl url = parser.parse("http://www.mail.com/users/" + userId);
        assertThat(url, isValid());
        assertThat((Float) url.variable("userId"), is(userId));
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", floatVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/");
        assertThat(url, isInvalid());
    }
}
