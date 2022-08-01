package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class provides methods to make it easier to load a
 * <code>.crt certificate</code> and a <code>.p8 private signature key</code>
 * from a file.
 *
 * If the load is done several times, only the last one will be stored.
 *
 * @author fsonnessa
 */
public class EasyLoadFromFile {

    private static X509Certificate lastCrt = null;
    private static PrivateKey lastPrivSigKey = null;

    /**
     * This method loads the .crt certificate, in X509 format, from the file
     * with the specified name
     *
     * @param filename name of the file
     * @return a <code>X509Certificate</code> object representing the loaded
     * X509Certificate
     */
    public static X509Certificate loadCrt(String filename) {

        //Open the file that contains the certificate
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
     * This method returns the last loaded X509Certificate
     *
     * @return a <code>X509Certificate</code> object representing the last
     * loaded X509Certificate
     */
    public static X509Certificate getLastCrt() {
        return lastCrt;
    }

    /**
     * This method reads from a certificate, contained in a file named
     * <code>filename</code>, the private signature key
     *
     * @param filename the name of the file
     * @return a <code>PrivateKey</code> object representing the private
     * signature key
     */
    public static PrivateKey loadPrivSigKey(String filename) {
        PrivateKey key = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
            key = kf.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
        lastPrivSigKey = key;

        return key;
    }

    /**
     * This method returns the last loaded Private Signature Key
     *
     * @return a <code>PrivateKey</code> object representing the last loaded
     * Private Signature Key
     */
    public static PrivateKey getLastPrivSigKey() {
        return lastPrivSigKey;
    }

}
