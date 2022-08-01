package AuthorityPackage;

import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author gparrella
 */
public class AuthorityTest {

    private static List<Authority> authorities = new ArrayList<>();
    private Authority a;
    int i = 0;

    public AuthorityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        for (AuthorityName n : AuthorityName.values()) {
            authorities.add(new Authority(n.toString()));
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        a = authorities.get(i);
        i++;
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPrivateSigKey method, of class Authority.
     */
    @Test
    public void testGetPrivateSigKey() {
        System.out.println("getPrivateSigKey");
        System.out.println(a.getPrivateSigKey());
    }

    /**
     * Test of getPublicEncKey method, of class Authority.
     */
    @Test
    public void testGetPublicEncKey() {
        System.out.println("getPublicEncKey");
        System.out.println(a.getPublicEncKey());
        CyclicGroupParameters param = CyclicGroupParameters.getInstance();

        if (!a.getPublicEncKey().equals(param.getG().modPow(a.getPrivateEncKey().mod(param.getQ()), param.getP()))) {
            fail("PublicEncKey malformed");
        } else {
            System.out.println("\tPublic/Private Enc Key Well Formed");
        }
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
    public void testGetPublicSigKey() throws Exception {
        System.out.println("getPublicSigKey");
        System.out.println(a.getPublicSigKey());

        Signature signature = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
        // generate a signature
        signature.initSign(a.getPrivateSigKey(), new SecureRandom()); // initialize signature for sign with private key K.getPrivate() and a secure random source

        byte[] message = new byte[]{(byte) 'c', (byte) 'i', (byte) 'a', (byte) 'o'}; // this is the message to sign - a byte array

        signature.update(message);
        byte[] sigBytes = signature.sign();

        // verify signature
        // we create another instance of Signature to simulate a verifier that is possibly on another machine and does NOT have the secret-key
        Signature signature2 = Signature.getInstance("SHA256withECDSA");

        signature2.initVerify(a.getCertificate());

        byte[] message2 = new byte[]{(byte) 'c', (byte) 'i', (byte) 'a', (byte) 'o'};

        signature2.update(message2); // for verification use update+verify methods, first calling update with the message and the calling verify with the signature
        if (signature2.verify(sigBytes) == true) {
            System.out.println("signature verification succeeded.");
        } else {
            fail("signature verification failed.");
        }
    }

    /**
     * Test of getCertificate method, of class Authority.
     */
    @Test
    public void testGetCertificate() {
        System.out.println("getCertificate \n - Certificate Info");
        X509Certificate c = a.getCertificate();
        System.out.println("\tCertificate for: " + c.getSubjectX500Principal());
        System.out.println("\tSubject Public Key: " + c.getPublicKey());
        System.out.println("\tCertificate issued by: " + c.getIssuerX500Principal());
        System.out.println("\tThe certificate is valid from " + c.getNotBefore() + " to "
                + c.getNotAfter());
        System.out.println("\tCertificate SN# " + c.getSerialNumber());
        System.out.println("\tGenerated with " + c.getSigAlgName());
        System.out.println("\n");

        // Carica il certificato del ministero
        // Read the authority Certificate
        InputStream in = null;
        try {
            in = new FileInputStream("Certificati/Authorities/Ministero.crt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        // create the certificate factory 
        CertificateFactory fact = null;
        try {
            fact = CertificateFactory.getInstance("X.509");
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }
        X509Certificate certificate = null;
        try {
            // read the certificate
            certificate = (X509Certificate) fact.generateCertificate(in);
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }

        try {
            c.verify(certificate.getPublicKey());
            System.out.println("\tCertificate validation ok");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Error");
        }

    }

}
