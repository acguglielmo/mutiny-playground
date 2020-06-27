package com.acguglielmo.mutinyplayground;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
            .items("Antonio", "Carlos", "Guglielmo", "Neto")
            .onItem().apply( n -> format("my friend %s", n) ).cache()
            .onItem().apply( n -> format("Hello %s", n) )
            .onCompletion().continueWith("All of you are Welcome!")
            .subscribe()
            .with( System.out::println );

        Multi.createFrom().items("a", "b", "c")
            .onItem().invoke(item -> System.out.println("Received item " + item))
            .onFailure().invoke(failure -> System.out.println("Failed with " + failure.getMessage()))
            .onCompletion().invoke(() -> System.out.println("Completed"))
            .on().subscribed(subscription -> System.out.println("We are subscribed!"))
            .on().cancellation(() -> System.out.println("Downstream has cancelled the interaction"))
            .on().request(n -> System.out.println("Downstream requested " + n + " items"))
            .subscribe().with(item -> { });


    }

    @Test
    public void documentationExampleTest() {

        Multi.createFrom().items("a", "b", "c")
		    .onItem().invoke( item -> System.out.println("Received item " + item) )
		    .onFailure().invoke( failure -> System.out.println("Failed with " + failure.getMessage() ) )
		    .onCompletion().invoke( () -> System.out.println("Completed") )
		    .on().subscribed(subscription -> System.out.println("We are subscribed!") )
		    .on().cancellation( () -> System.out.println("Downstream has cancelled the interaction") )
		    .on().request( n -> System.out.println("Downstream requested " + n + " items") )
		    .subscribe().with( item -> { } );

    }

}