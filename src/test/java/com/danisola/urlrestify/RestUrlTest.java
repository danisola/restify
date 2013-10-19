package com.danisola.urlrestify;

import org.junit.Test;

import java.net.URL;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.types.FloatVar.floatVar;
import static com.danisola.urlrestify.types.IntVar.intVar;
import static com.danisola.urlrestify.types.IntVar.posIntVar;
import static com.danisola.urlrestify.types.StrVar.regexStrVar;
import static com.danisola.urlrestify.types.StrVar.strVar;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class RestUrlTest {

    @Test
    public void whenMultipleParametersAreRequestedThenItsValuesAreCorrect() {
        RestParser parser = parser("/{}/{}?float={}&str={}&int={}",
                strVar("first"), strVar("second"), floatVar("float"), strVar("str"), intVar("int"));
        RestUrl url = parser.parse("http://www.example.com/a/b?float=3.14&str=pi&int=3");
        assertThat((String) url.variable("first"), is("a"));
        assertThat((String) url.variable("second"), is("b"));
        assertThat((Float) url.variable("float"), is(3.14f));
        assertThat((String) url.variable("str"), is("pi"));
        assertThat((Integer) url.variable("int"), is(3));
    }

    @Test
    public void whenCompositePathsAreDefinedThenItsValuesAreCorrect() {
        RestParser parser = parser("/servers/{}-{}/restart",
                strVar("environment"), intVar("id"));
        RestUrl url = parser.parse("http://www.company.com/servers/prod-3/restart");
        assertThat((String) url.variable("environment"), is("prod"));
        assertThat((Integer) url.variable("id"), is(3));
    }

    @Test
    public void whenCompositeParamsAreDefinedThenItsValuesAreCorrect() {
        RestParser parser = parser("/battleship/shoot?square={}{}",
                regexStrVar("char", "[A-L]"), intVar("num"));
        RestUrl url = parser.parse("http://www.games.com/battleship/shoot?square=B7");
        assertThat((String) url.variable("char"), is("B"));
        assertThat((Integer) url.variable("num"), is(7));
    }

    @Test
    public void whenNoVariablesAreProvidedThenOnlyThePathIsConsidered() {
        RestParser parser = parser("/users");
        RestUrl url = parser.parse("http://www.social-network.com/users");
        assertTrue(url.isValid());
    }

    @Test
    public void whenThereAreExtraParametersThenSpecifiedOnesAreCorrect() {
        RestParser parser = parser("/users?id={}", strVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users?cache_buster=4f0ce72&id=ben");
        assertThat((String) url.variable("userId"), is("ben"));
    }

    @Test
    public void whenVariablesDoNotMatchTheWholeValueThenUrlIsInvalid() {
        RestParser parser = parser("/friends?within={}", intVar("kms"));
        RestUrl url = parser.parse("http://www.network.com/friends?within=4K");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenWrongUrlIsProvidedThenUrlIsInvalid() {
        RestParser parser = parser("/leagues/{}/teams/{}/players/{}", strVar("leagueId"), strVar("teamId"), posIntVar("playerId"));
        RestUrl url = parser.parse("www.football.com/leagues/seattle/teams/trebuchet/players/21");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenNoUrlIsPassedThenUrlIsInvalid() {
        RestParser parser = parser("/country/{}", strVar("countryId"));
        RestUrl url = parser.parse((URL) null);
        assertThat(url, isInvalid());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUnexistantVariableIsRequestedThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl url = parser.parse("http://www.world.com/countries/gb");
        url.variable("cityId");
    }

    @Test(expected = ClassCastException.class)
    public void whenWrongTypeIsRequestedThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl url = parser.parse("http://www.world.com/countries/gb");
        Integer countryId = url.variable("countryId");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullKeyIsRequestedThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl url = parser.parse("http://www.world.com/countries/gb");
        url.variable(null);
    }

    @Test(expected = IllegalStateException.class)
    public void whenAccessingVariablesAndUrlIsInvalidThenExceptionIsThrown() {
        RestParser parser = parser("/countries/{}", strVar("countryId"));
        RestUrl url = parser.parse("http://www.world.com/");
        assertThat(url, isInvalid());
        url.variable("countryId");
    }
}
