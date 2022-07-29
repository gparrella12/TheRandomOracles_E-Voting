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
 *
 * @author fsonnessa
 */
public class CertificateLoader {

    private static X509Certificate lastCrt = null;

    /**
     *
     * @param filename
     * @return
     */
    public static X509Certificate loadCrtFromFile(String filename) {
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
     *
     * @return
     */
    public static X509Certificate getLastCrt() {
        return lastCrt;
    }

    /**
     *
     * @param filename
     * @return
     */
    public static PrivateKey loadSkFromFile(String filename) {
        PrivateKey key = null;
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
            key = kf.generatePrivate(spec);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return key;
    }

}
