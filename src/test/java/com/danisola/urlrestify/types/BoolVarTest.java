package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestUrl;
import com.danisola.urlrestify.RestParser;
import org.junit.Test;

import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.types.BoolVar.boolVar;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class BoolVarTest {

    @Test
    public void whenValueIsTrueThenIsCorrectlyParsed() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl vars = parser.parse("http://www.mail.com/users?active=True");
        assertThat((Boolean) vars.variable("isActive"), is(true));
    }

    @Test
    public void whenValueIsFalseThenIsCorrectlyParsed() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl vars = parser.parse("http://www.mail.com/users?active=FaLsE");
        assertThat((Boolean) vars.variable("isActive"), is(false));
    }

    @Test
    public void whenValueIsIncorrectThenVariablesHaveErrors() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl vars = parser.parse("http://www.mail.com/users?active=maybe");
        assertThat(vars.hasErrors(), is(true));
        assertThat(vars.errorMessage(), notNullValue());
    }
}
