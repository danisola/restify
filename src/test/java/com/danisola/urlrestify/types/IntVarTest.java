package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.IntVar.intVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntVarTest {

    @Test
    public void whenNegIntIsCorrectThenIntegerIsReturned() {
        RestParser parser = parser("/users/{}", intVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/-2397");
        assertThat(url, isValid());
        assertThat((Integer) url.variable("userId"), is(-2397));
    }

    /*@Test
    public void whenNegIntIsNotExpectedThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", posIntVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/-2397");
        assertThat(url, isInvalid());
    }*/

    @Test
    public void whenValueIsTooBigThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", intVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/493483292384928439432397");
        assertThat(url, isInvalid());
    }
}
