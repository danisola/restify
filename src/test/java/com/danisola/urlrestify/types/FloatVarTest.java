package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestUrl;
import com.danisola.urlrestify.RestParser;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.matchers.IsValid.isValid;
import static com.danisola.urlrestify.types.FloatVar.floatVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FloatVarTest {

    @Test
    public void whenKeyIsWellSetThenValueIsCorrect() {
        RestParser parser = parser("/users/{}", floatVar("userId"));
        Float userId = Float.MAX_VALUE;
        RestUrl url = parser.parse("http://www.mail.com/users/" + userId);
        assertThat(url, isValid());
        assertThat((Float) url.variable("userId"), is(userId));
    }
}
