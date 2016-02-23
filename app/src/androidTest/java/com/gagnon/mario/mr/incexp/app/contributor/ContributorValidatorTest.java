package com.gagnon.mario.mr.incexp.app.contributor;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.IllegalFormatException;

import static org.junit.Assert.*;

/**
 * Created by mario on 2/20/2016.
 */
public class ContributorValidatorTest extends TestCase {

//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConstructorNullParameter() {

        // Preparation
        thrown.expect(NullPointerException.class);
        //thrown.expectMessage("abc");
        ContributorValidator contributorValidator = new ContributorValidator(null);

        //boolean hasFail = true;
        // Execution

        // Verification
        //assertFalse("Should have thrown NullPointerException", hasFail);

    }
}