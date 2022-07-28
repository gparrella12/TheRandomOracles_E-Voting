package src.AuthorityPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import src.ElGamalHomomorphic.ElGamalKeyPair;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author Ernesto
 */
public class Authority {

    private String name;
    private final BigInteger privateEncKey;
    private final BigInteger publicEncKey;
    private final PrivateKey privateSigKey;
    private final PublicKey publicSigKey;
    private X509Certificate certificate;

    //nel costruttore creo la coppia (sk,pk);   
    public Authority(String name) {
        this.name = name;
        ElGamalKeyPair encPair = new ElGamalKeyPair();
        this.privateEncKey = encPair.getSecretKey();
        this.publicEncKey = encPair.getPublicKey();

        // Read the authority Certificate
        InputStream in = null;
        try {
            in = new FileInputStream("Certificati/" + name + ".crt");
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

        try {
            // read the certificate
            this.certificate = (X509Certificate) fact.generateCertificate(in);
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }
        this.publicSigKey = this.certificate.getPublicKey();
        PrivateKey key = null;
        try {
            key = get("Certificati/" + name + ".p8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.privateSigKey = key;
    }

    public static PrivateKey get(String filename)
            throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        PKCS8EncodedKeySpec spec
                = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("ECDSA");
        return kf.generatePrivate(spec);
    }

    public PrivateKey getPrivateSigKey() {
        return privateSigKey;
    }

    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    public BigInteger getPrivateEncKey() {
        return privateEncKey;
    }

    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public String getName() {
        return name;
    }
}
