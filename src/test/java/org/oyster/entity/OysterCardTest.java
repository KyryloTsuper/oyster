package org.oyster.entity;

import junit.framework.Assert;
import org.junit.Test;

public class OysterCardTest {

    private OysterCard card;

    @Test
    public void testAddFunds() {
        double expected = 41.5;
        card = new OysterCard(30.);
        card.addFunds(11.5);
        Assert.assertEquals(expected, card.getBalance());
    }

    @Test
    public void testReduceFunds() {
        double expected = 18.5;
        card = new OysterCard(30.);
        card.reduceFunds(11.5);
        Assert.assertEquals(expected, card.getBalance());
    }
}