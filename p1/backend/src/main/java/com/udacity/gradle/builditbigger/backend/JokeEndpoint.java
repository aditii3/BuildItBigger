package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.example.android.jokeslib.MyJokes;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(

        name = "jokeslib",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)

public class JokeEndpoint {
    @ApiMethod(name = "getRandomJoke")
    public Joke getRandomJoke() {
        return new Joke(MyJokes.getJoke());
    }


}
