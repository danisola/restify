package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.IntArrayVar.intArrayVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IntArrayVarTest {

    @Test
    public void whenValueIsCorrectThenIntegerArrayIsReturned() {
        RestParser parser = parser("/users/update?ids={}", intArrayVar("userIds"));
        RestUrl url = parser.parse("http://www.mail.com/users/update?ids=-3,8,9&ts=9379");
        assertThat(url, isValid());
        assertThat((Integer[]) url.variable("userIds"), is(new Integer[] {-3, 8, 9}));
    }

    @Test
    public void whenSomeValueIsNotIntegerThenUrlIsInvalid() {
        RestParser parser = parser("/users/update?ids={}", intArrayVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/update?ids=3,a,9&ts=9379");
        assertThat(url, isInvalid());
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/update?ids={}", intArrayVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/update?ids=&ts=9379");
        assertThat(url, isInvalid());
    }
}
