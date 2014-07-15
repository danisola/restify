package com.danisola.restify.url.types;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import org.junit.Test;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.matchers.IsInvalid.isInvalid;
import static com.danisola.restify.url.matchers.IsValid.isValid;
import static com.danisola.restify.url.types.StrVar.strVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StrVarTest {

    @Test
    public void whenKeyIsWellSetThenValueIsCorrect() {
        RestParser parser = parser("/country/{}/cities", strVar("countryId"));
        RestUrl url = parser.parse("http://www.world.com/country/99/cities");
        assertThat(url, isValid());
        assertThat((String) url.variable("countryId"), is("99"));
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsValid() {
        RestParser parser = parser("/country/{}/cities", strVar("countryId"));
        RestUrl url = parser.parse("http://www.world.com/country//cities");
        assertThat(url, isValid());
        assertThat((String) url.variable("countryId"), is(""));
    }

    @Test
    public void whenRegexDoesNotMatchThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", strVar("userId", "\\d{2}-\\d{3}"));
        RestUrl url = parser.parse("http://www.service.com/users/john");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenRegexMatchThenValueIsCorrect() {
        RestParser parser = parser("/users/{}/emails", strVar("userId", "\\d{2}-\\d{3}"));
        RestUrl url = parser.parse("http://www.service.com/users/34-834/emails");
        assertThat(url, isValid());
        assertThat((String) url.variable("userId"), is("34-834"));
    }
}
