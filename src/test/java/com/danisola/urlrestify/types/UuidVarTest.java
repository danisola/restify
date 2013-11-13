package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestParser;
import com.danisola.urlrestify.RestUrl;
import org.junit.Test;

import java.util.UUID;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsInvalid.isInvalid;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.UuidVar.uuidVar;
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
