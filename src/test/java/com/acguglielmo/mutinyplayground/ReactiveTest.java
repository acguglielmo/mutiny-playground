package com.acguglielmo.mutinyplayground;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class ReactiveTest {

    @Test
    public void uniTest() {

        Uni.createFrom().item("Antonio")
            .onItem()
            .apply( e -> format("hello %s", e) )
            .subscribe()
            .with( e -> assertThat(e, equalTo("hello Antonio") ) );

    }

    @Test
    public void multiTest() {

        Multi.createFrom()
            .items("Antonio", "Guglielmo", "Test")
            .onItem()
            .apply(n -> String.format("hello %s", n))
            .subscribe()
            .with(
            		e -> assertThat(e, equalTo( "hello Antonio" ) ),
                    failure -> fail(failure)
            );


    }

}