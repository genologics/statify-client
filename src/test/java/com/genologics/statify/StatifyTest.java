package com.genologics.statify;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Adam Jordens
 */
public class StatifyTest {
    @Test
    public void testLog() {
        Statify statify = new Statify("http://localhost:8000");

        // logging is not yet implemented
        Assert.assertTrue(statify.log("key", "value"));
        Assert.assertTrue(statify.log("key", 1L));
    }
}
