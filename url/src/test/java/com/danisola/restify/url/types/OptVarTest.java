package com.danisola.restify.url.types;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import org.junit.Test;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.matchers.IsValid.isValid;
import static com.danisola.restify.url.types.LongVar.longVar;
import static com.danisola.restify.url.types.Opt.opt;
import static com.danisola.restify.url.types.StrVar.strVar;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OptVarTest {

    @Test
    public void whenVariableIsInUrlThenValueIsReturned() {
        RestParser parser = parser("/news?ts={}", opt(longVar("ts")));
        RestUrl url = parser.parse("http://www.mytimes.com/news?ts=23372036854775807");
        assertThat(url, isValid());
        assertThat((Long) url.variable("ts"), is(23372036854775807L));
    }

    @Test
    public void whenValueInParametersIsNotDeclaredThenNullIsReturned() {
        RestParser parser = parser("/news?ts={}", opt(longVar("ts")));
        RestUrl url = parser.parse("http://www.mytimes.com/news?ts=");
        assertThat(url, isValid());
        assertThat(url.variable("ts"), nullValue());
    }

    @Test
    public void whenValueInPathIsNotDeclaredThenNullIsReturned() {
        RestParser parser = parser("/countries/{}?ts={}", opt(strVar("country")), opt(longVar("ts")));
        RestUrl url = parser.parse("http://www.world.com/countries/?ts=2389423");
        assertThat(url, isValid());
        assertThat(url.variable("country"), nullValue());
        assertThat((Long) url.variable("ts"), is(2389423L));
    }

    @Test
    public void whenVariableIsNotInUrlThenNullIsReturned() {
        RestParser parser = parser("/news?ts={}", opt(longVar("ts")));
        RestUrl url = parser.parse("http://www.mytimes.com/news");
        assertThat(url, isValid());
        assertThat(url.variable("ts"), nullValue());
    }
}
