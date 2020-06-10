package com.acguglielmo.mutinyplayground;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public class ReactiveTest {

    private static final Logger LOGGER = LogManager.getLogger(ReactiveTest.class);
    
    @RegisterExtension
    ConsoleWatcherSupport extension = new ConsoleWatcherSupport(LogManager.getLogger(ReactiveTest.class));
    
    @Test
    public void uniTest() {
        
        Uni.createFrom().item("Antonio")
            .onItem()
            .apply(n -> String.format("hello %s", n))
            .subscribe()
            .with(LOGGER::info);
        
        assertThat(extension.getOutput(), equalTo("hello Antonio\n"));
    
    }
    
    @Test
    public void multiTest() {
        
        Multi.createFrom()
            .items("Antonio", "Guglielmo", "Test")
            .onItem()
            .apply(n -> String.format("hello %s", n))
            .subscribe()
            .with(
                    LOGGER::info,
                    failure -> LOGGER.error("Failed with " + failure.getMessage()));
        
        assertThat(extension.getOutput(), equalTo("hello Antonio\nhello Guglielmo\nhello Test\n"));
        
    }
    
}