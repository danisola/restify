package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.IntVar.intVar;
import static com.danisola.urlrestify.types.StrArrayVar.strArrayVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StrArrayVarTest {

    @Test
    public void whenValueIsCorrectThenStringArrayIsReturned() {
        RestParser parser = parser("/users/{}?fields={}", intVar("userId"), strArrayVar("fields"));
        RestUrl url = parser.parse("http://www.network.com/users/435?fields=name,age&ts=9379");
        assertThat(url, isValid());
        assertThat((String[]) url.variable("fields"), is(new String[] {"name", "age"}));
    }

    @Test
    public void whenValuesAreNullIsNotWellFormattedThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}?fields={}", intVar("userId"), strArrayVar("fields"));
        RestUrl url = parser.parse("http://www.network.com/users/435?fields=,age&ts=9379");
        assertThat(url, isValid());
        assertThat((String[]) url.variable("fields"), is(new String[]{"", "age"}));
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}?fields={}", intVar("userId"), strArrayVar("fields"));
        RestUrl url = parser.parse("http://www.network.com/users/435?fields=&ts=9379");
        assertThat(url, isInvalid());
    }
}
