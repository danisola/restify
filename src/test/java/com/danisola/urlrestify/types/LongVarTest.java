package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestUrl;
import com.danisola.urlrestify.RestParser;
import org.junit.Test;

import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.types.LongVar.longVar;
import static com.danisola.urlrestify.types.LongVar.posLongVar;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LongVarTest {

    @Test
    public void whenNegLongIsCorrectThenLongIsReturned() {
        RestParser parser = parser("/users/{}", longVar("userId"));
        RestUrl vars = parser.parse("http://www.mail.com/users/-9223372036854775807");
        assertThat((Long) vars.variable("userId"), is(-9223372036854775807L));
    }

    @Test
    public void whenPosLongIsIncorrectThenErrorIsReturned() {
        RestParser parser = parser("/users/{}", posLongVar("userId"));
        RestUrl vars = parser.parse("http://www.mail.com/users/-9223372036854775807");
        assertThat(vars.hasErrors(), is(true));
        assertThat(vars.errorMessage(), notNullValue());
    }

    @Test
    public void whenPosLongIsCorrectThenLongIsReturned() {
        RestParser parser = parser("/users/{}", posLongVar("userId"));
        RestUrl vars = parser.parse("http://www.mail.com/users/9223372036854775807");
        assertThat((Long) vars.variable("userId"), is(9223372036854775807L));
    }
}
