package com.danisola.restify.url.types;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import org.junit.Test;

import java.util.UUID;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.matchers.IsInvalid.isInvalid;
import static com.danisola.restify.url.matchers.IsValid.isValid;
import static com.danisola.restify.url.types.UuidVar.uuidVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UuidVarTest {

    @Test
    public void whenNegIntIsNotExpectedThenParse() {
        RestParser parser = parser("/users/{}", uuidVar("userId"));
        UUID uuid = UUID.randomUUID();
        RestUrl url = parser.parse("http://www.mail.com/users/" + uuid);
        assertThat(url, isValid());
        assertThat((UUID) url.variable("userId"), is(uuid));
    }

    @Test
    public void whenVariableIsEmptyThenUrlIsInvalid() {
        RestParser parser = parser("/users/{}", uuidVar("userId"));
        RestUrl url = parser.parse("http://www.mail.com/users/");
        assertThat(url, isInvalid());
    }
}
