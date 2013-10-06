package com.danisola.urlrestify;


import org.junit.Test;

import static com.danisola.urlrestify.types.IntVar.posIntVar;
import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.types.StrVar.strVar;

public class RestParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenWrongUrlIsProvidedThenExceptionIsThrown() {
        RestParser parser = parser("/leagues/{}/teams/{}/players/{}", strVar("leagueId"), strVar("teamId"), posIntVar("playerId"));
        parser.parse("www.football.com/leagues/seattle/teams/trebuchet/players/21");
    }

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
}
