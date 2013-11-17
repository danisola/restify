package com.danisola.restify.url.types;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import org.junit.Test;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.matchers.IsInvalid.isInvalid;
import static com.danisola.restify.url.matchers.IsValid.isValid;
import static com.danisola.restify.url.types.DoubleVar.doubleVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DoubleVarTest {

    @Test
    public void whenKeyIsWellSetThenValueIsCorrect() {
        RestParser parser = parser("/users/{}", doubleVar("userId"));
        Double userId = Double.MAX_VALUE;
        RestUrl url = parser.parse("http://www.mail.com/users/" + userId);
        assertThat(url, isValid());
        assertThat((Double) url.variable("userId"), is(userId));
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", doubleVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/");
        assertThat(url, isInvalid());
    }
}
