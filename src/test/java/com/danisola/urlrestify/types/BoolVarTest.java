package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.BoolVar.boolVar;
import static com.danisola.urlrestify.types.Opt.opt;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class BoolVarTest {

    @Test
    public void whenValueIsTrueThenIsCorrectlyParsed() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=True");
        assertThat(url, isValid());
        assertThat((Boolean) url.variable("isActive"), is(true));
    }

    @Test
    public void whenVariableIsEmptyThenValueIsFalse() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=");
        assertThat(url, isValid());
        assertThat((Boolean) url.variable("isActive"), is(false));
    }

    @Test
    public void whenValueIsFalseThenIsCorrectlyParsed() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=FaLsE");
        assertThat(url, isValid());
        assertThat((Boolean) url.variable("isActive"), is(false));
    }

    @Test
    public void whenValueIsIncorrectThenVariableIsFalse() {
        RestParser parser = parser("/users?active={}", boolVar("isActive"));
        RestUrl url = parser.parse("http://www.mail.com/users?active=maybe");
        assertThat(url, isValid());
        assertThat((Boolean) url.variable("isActive"), is(false));
    }

    @Test
    public void whenValueIsOptionalAndMissingThenVariableIsNull() {
        RestParser parser = parser("/users?active={}", opt(boolVar("isActive")));
        RestUrl url = parser.parse("http://www.mail.com/users");
        assertThat(url, isValid());
        assertThat(url.variable("isActive"), nullValue());
    }
}
