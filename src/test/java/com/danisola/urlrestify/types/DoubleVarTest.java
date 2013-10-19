package com.danisola.urlrestify.types;

import com.danisola.urlrestify.RestUrl;
import com.danisola.urlrestify.RestParser;
import org.junit.Test;

import static com.danisola.urlrestify.RestParserFactory.parser;
import static com.danisola.urlrestify.types.DoubleVar.doubleVar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DoubleVarTest {

    @Test
    public void whenKeyIsWellSetThenValueIsCorrect() {
        RestParser parser = parser("/users/{}", doubleVar("userId"));
        Double userId = Double.MAX_VALUE;
        RestUrl url = parser.parse("http://www.mail.com/users/" + userId);
        assertThat((Double) url.variable("userId"), is(userId));
    }
}
