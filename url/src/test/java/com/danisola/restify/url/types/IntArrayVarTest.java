package com.danisola.restify.url.types;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import org.junit.Test;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.matchers.IsInvalid.isInvalid;
import static com.danisola.restify.url.matchers.IsValid.isValid;
import static com.danisola.restify.url.types.IntArrayVar.intArrayVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntArrayVarTest {

    @Test
    public void whenValueIsCorrectThenIntegerArrayIsReturned() {
        RestParser parser = parser("/users/update?ids={}", intArrayVar("userIds"));
        RestUrl url = parser.parse("http://www.mail.com/users/update?ids=-3,8,9&ts=9379");
        assertThat(url, isValid());
        assertThat((Integer[]) url.variable("userIds"), is(new Integer[] {-3, 8, 9}));
    }

    @Test
    public void whenSomeValueIsNotIntegerThenUrlIsInvalid() {
        RestParser parser = parser("/users/update?ids={}", intArrayVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/update?ids=3,a,9&ts=9379");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/update?ids={}", intArrayVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/update?ids=&ts=9379");
        assertThat(url, isInvalid());
    }
}
