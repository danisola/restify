package com.danisola.urlrestify;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.types.FloatVar.floatVar;
import static com.danisola.urlrestify.types.IntVar.intVar;
import static com.danisola.urlrestify.types.StringVar.strVar;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RestUrlTest {

    @Test
    public void whenMultipleParametersAreRequestedThenItsValuesAreCorrect() throws MalformedURLException {
        RestParser parser = parser("/{}/{}?float={}&str={}&int={}",
                strVar("first"), strVar("second"), floatVar("float"), strVar("str"), intVar("int"));
        RestUrl restUrl = parser.parse("http://www.example.com/a/b?float=3.14&str=pi&int=3");
        assertThat((String) restUrl.variable("first"), is("a"));
        assertThat((String) restUrl.variable("second"), is("b"));
        assertThat((Float) restUrl.variable("float"), is(3.14f));
        assertThat((String) restUrl.variable("str"), is("pi"));
        assertThat((Integer) restUrl.variable("int"), is(3));
    }

    @Test
    public void whenExtraParametersRequestedThenSpecifiedOnesAreCorrect() throws MalformedURLException {
        RestParser parser = parser("/users?id={}", strVar("userId"));
        RestUrl vars = parser.parse("http://www.mail.com/users?cache_buster=4f0ce72&id=ben");
        assertThat((String) vars.variable("userId"), is("ben"));
    }

    @Test(expected = NullPointerException.class)
    public void whenNoUrlIsPassedThenExceptionIsThrown() {
        RestParser parser = parser("/country/{}", strVar("countryId"));
        parser.parse((URL) null);
    }

    @Test(expected = IllegalStateException.class)
    public void whenUnexistantVariableIsRequestedThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl restUrl = parser.parse("http://www.world.com/countries/uk");
        restUrl.variable("cityId");
    }

    @Test(expected = ClassCastException.class)
    public void whenWrongTypeIsRequestedThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl restUrl = parser.parse("http://www.world.com/countries/uk");
        Integer countryId = restUrl.variable("countryId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullKeyIsRequestedThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl restUrl = parser.parse("http://www.world.com/countries/uk");
        restUrl.variable(null);
    }
}
