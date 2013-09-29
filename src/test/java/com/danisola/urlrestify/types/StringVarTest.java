package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestUrl;
import com.danisola.urlrestify.RestParser;
import org.junit.Test;

import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.types.StringVar.regexStrVar;
import static com.danisola.urlrestify.types.StringVar.strVar;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class StringVarTest {

    @Test
    public void whenKeyIsWellSetThenValueIsCorrect() {
        RestParser parser = parser("/country/{}/cities", strVar("countryId"));
        RestUrl vars = parser.parse("http://www.world.com/country/99/cities");
        assertThat((String) vars.variable("countryId"), is("99"));
    }

    @Test
    public void whenRegexDoesNotMatchThenVarsHasErrors() {
        RestParser parser = parser("/users/{}", regexStrVar("userId", "\\d{2}-\\d{3}"));
        RestUrl vars = parser.parse("http://www.service.com/users/john");
        assertThat(vars.hasErrors(), is(true));
        assertThat(vars.errorMessage(), notNullValue());
    }

    @Test
    public void whenRegexMatchThenValueIsCorrect() {
        RestParser parser = parser("/users/{}/emails", regexStrVar("userId", "\\d{2}-\\d{3}"));
        RestUrl vars = parser.parse("http://www.service.com/users/34-834/emails");
        assertThat((String) vars.variable("userId"), is("34-834"));
    }
}
