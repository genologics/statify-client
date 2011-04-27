package com.genologics.statify;

import org.testng.annotations.Test;

/**
 * @author Adam Jordens
 */
public class StatifyTest {
    @Test
    public void testLog() {
        Statify statify = new Statify("some-guid", "localhost");
        statify.log("key", "value");
        statify.log("key", 1L);
    }
}
