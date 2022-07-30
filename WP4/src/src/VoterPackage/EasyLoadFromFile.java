package src.VoterPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class provides methods to make it easier to load a .crt certificate and 
 * .p8 private key from a file.
 * Keep the last certificate and the last loaded key for later.
 * 
 * @author fsonnessa
 */
public class EasyLoadFromFile {

    private static X509Certificate lastCrt = null;
    private static PrivateKey lastKey = null;

    /**
     * Upload the .crt certificate in X509 format from the specified file
     * @param filename
     * @return
     */
    public static X509Certificate loadCrt(String filename) {
        //Read the authority Certificate
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        //Create the certificate factory 
        CertificateFactory fact = null;
        try {
            fact = CertificateFactory.getInstance("X.509");
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }

        try {
            //Read the certificate
            lastCrt = (X509Certificate) fact.generateCertificate(in);
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }

        return lastCrt;
    }

    /**
     * Return last loaded X509Certificate
     * @return
     */
    public static X509Certificate getLastCrt() {
        return lastCrt;
    }

    /**
     * Load the private key from the specified .p8 file
     * @param filename
     * @return
     */
    public static PrivateKey loadKey(String filename) {
        PrivateKey key = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
            key = kf.generatePrivate(spec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        lastKey = key;
        
        return key;
    }

    /**
     * Return last loaded PrivateKey
     * @return
     */
    public static PrivateKey getLastKey() {
        return lastKey;
    }

}
