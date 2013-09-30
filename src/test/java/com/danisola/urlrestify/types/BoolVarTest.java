package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.types.BoolVar.boolVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BoolVarTest {

    @Test
    public void whenValueIsTrueThenIsCorrectlyParsed() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=True");
        assertThat((Boolean) url.variable("isActive"), is(true));
    }

    @Test
    public void whenValueIsFalseThenIsCorrectlyParsed() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=FaLsE");
        assertThat((Boolean) url.variable("isActive"), is(false));
    }

    @Test
    public void whenValueIsIncorrectThenVariablesHaveErrors() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=maybe");
        assertThat(url, isInvalid());
    }
}
