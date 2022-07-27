package src.Authority;

import java.math.BigInteger;
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
    Authority a = new Authority("ministero");
    public AuthorityTest() {
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
     * Test of getPublicEncKey method, of class Authority.
     */
    @Test
    public void testGetPublicEncKey() {
        System.out.println("getPublicEncKey");
        System.out.println(a.getPublicEncKey());
    }

    /**
     * Test of getPrivateEncKey method, of class Authority.
     */
    @Test
    public void testGetPrivateEncKey() {
        System.out.println("getPrivateEncKey");
        System.out.println(a.getPrivateEncKey());
    }

    /**
     * Test of getPublicSigKey method, of class Authority.
     */
    @Test
    public void testGetPublicSigKey() {
        System.out.println("getPublicSigKey");
        System.out.println(a.getPublicSigKey());
    }

    /**
     * Test of getCertificate method, of class Authority.
     */
    @Test
    public void testGetPrivateSigKey() {
        System.out.println("getPrivateSigKey");
        System.out.println(a.getPrivateSigKey());
    }

}