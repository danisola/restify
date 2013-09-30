package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestUrl;
import com.danisola.urlrestify.RestParser;
import org.junit.Test;

import java.util.UUID;

import static com.danisola.urlrestify.RestParser.parser;
import static com.danisola.urlrestify.types.UuidVar.uuidVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UuidVarTest {

    @Test
    public void whenNegIntIsNotExpectedThenParse() {
        RestParser parser = parser("/users/{}", uuidVar("userId"));
        UUID uuid = UUID.randomUUID();
        RestUrl url = parser.parse("http://www.mail.com/users/" + uuid);
        assertThat((UUID) url.variable("userId"), is(uuid));
    }
}
