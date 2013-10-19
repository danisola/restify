package com.danisola.urlrestify;


import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.types.IntVar.intVar;
import static com.danisola.urlrestify.types.StrVar.strVar;

public class RestParserTest {

    @Test(expected = IllegalStateException.class)
    public void whenParameterValueHasNoVariableThenExceptionIsThrown() {
        parser("/countries/{}?city=lon", strVar("GB"));
    }

    @Test(expected = NullPointerException.class)
    public void whenNoTypesAreProvidedThenExceptionIsThrown() {
        parser("/country", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUrlContainsFragmentThenExceptionIsThrown() {
        parser("/country/{}#economy", strVar("countryId"));
    }

    @Test(expected = IllegalStateException.class)
    public void whenMoreVariablesThanTypesThenExceptionIsThrown() {
        parser("/country/{}/city/{}", strVar("countryId"));
    }

    @Test(expected = IllegalStateException.class)
    public void whenMoreTypesThanVariablesThenExceptionIsThrown() {
        parser("/country/{}", strVar("countryId"), strVar("cityId"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPatternEndsInSlashThenExceptionIsThrown() {
        parser("/country/{}/", strVar("countryId"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBracesAreNotClosedInPathThenExceptionIsThrown() {
        parser("/country/{/city", strVar("countryId"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBracesAreNotOpenedInPathThenExceptionIsThrown() {
        parser("/country/}city", strVar("countryId"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBracesAreNotClosedInParamsThenExceptionIsThrown() {
        parser("/country?countryId=}&population={}", strVar("countryId"), intVar("population"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBracesAreNotOpenedInParamsThenExceptionIsThrown() {
        parser("/country?countryId={&population={}", strVar("countryId"), intVar("population"));
    }
}
