package com.danisola.restify.url;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.types.IntVar.intVar;
import static com.danisola.restify.url.types.StrVar.strVar;

public class UrlRestifyBenchmark extends SimpleBenchmark {

    public RestParser timeCreatingParser(int reps) {
        RestParser parser = null;
        for (int i = 0; i < reps; i++) {
            parser = parser("/leagues/{}/teams/{}/players?age={}", strVar("leagueId"), strVar("teamId"),
                    intVar("playerAge"));
        }
        return parser;
    }

    public String timeParsing(int reps) {
        RestParser parser = parser("/leagues/{}/teams/{}/players?age={}", strVar("leagueId"), strVar("teamId"),
                intVar("playerAge"));
        String dummy = null;
        for (int i = 0; i < reps; i++) {
            RestUrl vars = parser.parse("http://www.sports.com/leagues/seattle/teams/trebuchet/players?age=21");
            dummy = vars.variable("leagueId");
        }
        return dummy;
    }

    public static void main(String[] args) {
        Runner.main(UrlRestifyBenchmark.class, new String[]{"--measurementType", "TIME"});
    }
}