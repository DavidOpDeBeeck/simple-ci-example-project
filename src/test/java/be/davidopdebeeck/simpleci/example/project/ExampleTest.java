package be.davidopdebeeck.simpleci.example.project;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ExampleTest {

    @Test
    public void exampleTest() {
        assertThat(2 + 2, equalTo(4));
    }

    @Test
    public void exampleFailingTest() {
        assertThat(2 + 3, equalTo(4));
    }
}
