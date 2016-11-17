package com.indix.tautologyVerifier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mani on 11/18/16.
 */
public class TautologyVerifierTest {

    @Test
    public void simpleSuccess() {
        TautologyVerifier tautologyVerifier = new TautologyVerifier();
        String expression = "(a|!a)";
        boolean isTautology = tautologyVerifier.isTautology(expression);
        Assert.assertEquals(true, isTautology);
    }

    @Test
    public void simpleFail() {
        TautologyVerifier tautologyVerifier = new TautologyVerifier();
        String expression = "(a&!a)";
        boolean isTautology = tautologyVerifier.isTautology(expression);
        Assert.assertEquals(false, isTautology);
    }

    @Test
    public void complexSuccess() {
        TautologyVerifier tautologyVerifier = new TautologyVerifier();
        String expression = "(a & (!b | b)) |    (!a & (!  b | b))";
        boolean isTautology = tautologyVerifier.isTautology(expression);
        Assert.assertEquals(true, isTautology);
    }

    @Test
    public void complexFail() {
        TautologyVerifier tautologyVerifier = new TautologyVerifier();
        String expression = "(a & (!b | b)) |  (c) & (!a & (!  b | b))";
        boolean isTautology = tautologyVerifier.isTautology(expression);
        Assert.assertEquals(false, isTautology);
    }
}