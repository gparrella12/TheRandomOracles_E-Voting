/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

package src;

import java.math.BigInteger;
import java.security.Key;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gparrella
 */
public class AuthorityTest {
    private Authority a;
    public AuthorityTest() {
        a = new Authority("di maio");
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Authority.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "di maio";
        String result = a.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPublicEncKey method, of class Authority.
     */
    @Test
    public void testGetPublicEncKey() {
        System.out.println("getPublicEncKey");
        System.out.println(new BigInteger(a.getPublicEncKey().getEncoded()));
    }

    /**
     * Test of getPublicSigKey method, of class Authority.
     */
    @Test
    public void testGetPublicSigKey() {
        System.out.println("getPublicSigKey");
        System.out.println(new BigInteger(a.getPublicSigKey().getEncoded()));
    }

    /**
     * Test of getAuthorityCertificate method, of class Authority.
     */
    @Test
    public void testGetAuthorityCertificate() {
        System.out.println("getAuthorityCertificate");
        Authority instance = null;
        Object expResult = null;
        Object result = instance.getAuthorityCertificate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Authority.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(a.toString());
        
    }

}