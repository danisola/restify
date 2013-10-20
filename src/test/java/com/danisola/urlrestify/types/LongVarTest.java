package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.LongVar.longVar;
import static com.danisola.urlrestify.types.LongVar.posLongVar;
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
    public void whenPosLongIsIncorrectThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", posLongVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/-9223372036854775807");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenPosLongIsCorrectThenLongIsReturned() {
        RestParser parser = parser("/users/{}", posLongVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/9223372036854775807");
        assertThat(url, isValid());
        assertThat((Long) url.variable("userId"), is(9223372036854775807L));
    }
}
