package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.LongVar.longVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LongVarTest {

    @Test
    public void whenNegLongIsCorrectThenLongIsReturned() {
        RestParser parser = parser("/users/{}", longVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/-9223372036854775807");
        assertThat(url, isValid());
        assertThat((Long) url.variable("userId"), is(-9223372036854775807L));
    }

    @Test
    public void whenLongIsIncorrectThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", longVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/-92233720368.54775807");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", longVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/");
        assertThat(url, isInvalid());
    }
}
