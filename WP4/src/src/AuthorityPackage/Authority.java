package src.AuthorityPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
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
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class contains the implementation of an authority.
 * 
 * @author Ernesto
 */
public class Authority implements Serializable{

    private String name;
    private final BigInteger privateEncKey;
    private final BigInteger publicEncKey;
    private final PrivateKey privateSigKey;
    private final PublicKey publicSigKey;
    private X509Certificate certificate;

    /**
     * Creates an authority  by passing:
     * <ul>
     * <li><code>name, the name of the authority</code></li>
     * </ul>
     * 
     * A Key Pair for an ElGamal Encryption Scheme is created.
     * A valid certificate, previously generated, is imported and from it
     * are read the public and private signature keys.
     * @param name
     */
    public Authority(String name) {
        this.name = name;

        //Create the pair (sk,pk);   
        ElGamalKeyPair encPair = new ElGamalKeyPair();
        this.privateEncKey = encPair.getSecretKey();
        this.publicEncKey = encPair.getPublicKey();

        //Read the authority Certificate
        InputStream in = null;
        try {
            in = new FileInputStream("Certificati/Authorities/" + name + ".crt");
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
            this.certificate = (X509Certificate) fact.generateCertificate(in);
        } catch (CertificateException ex) {
            ex.printStackTrace();
        }
        this.publicSigKey = this.certificate.getPublicKey();
        PrivateKey key = null;
        try {
            key = readPrivateSigKey("Certificati/Authorities/" + name + ".p8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.privateSigKey = key;        
    }

    /**
     * This method read from a certificate, 
     * contained in a file named <code>filename</code>,
     * the private signature key
     *      
     * @param filename
     * @return the private signature key 
     * @throws Exception
     */
    public static PrivateKey readPrivateSigKey(String filename) throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
        return kf.generatePrivate(spec);
    }

    /**
     * This method returns the the public encryption key of the authority
     *
     * @return the public encryption key of the authority
     */
    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    /**
     * This method returns the private encryption key of the authority
     * 
     * @return the private encryption key of the authority
     */
    public BigInteger getPrivateEncKey() {
        return privateEncKey;
    }

    /**
     * This method returns the public signature key of the authority
     * 
     * @return the public signature key of the authority
     */
    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    /**
     * This method returns the private signature key of the authority
     * 
     * @return the private signature key
     */
    public PrivateKey getPrivateSigKey() {
        return privateSigKey;
    }

    /**
     *  This method returns the certificate of the authority
     * 
     * @return the certificate of the authority
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     *
     * @return the name of the authority
     */
    public String getName() {
        return name;
    }
}
