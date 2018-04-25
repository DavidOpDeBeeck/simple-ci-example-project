package be.davidopdebeeck.simpleci.example.project;

import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ExampleTest {

    @Test
    public void exampleTest() throws InterruptedException {
        assertThat(2 + 2, equalTo(4));
        sleep(10000);
    }
}
