package AuthorityPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import CryptographicTools.ElGamalHomomorphic.ElGamalKeyPair;
import Utils.EasyLoadFromFile;



/**
 * This class contains the implementation of an authority.
 *
 * @author Ernesto
 */
public class Authority implements Serializable {

    private final String name;
    private final BigInteger privateEncKey;
    private final BigInteger publicEncKey;
    private final PrivateKey privateSigKey;
    private final PublicKey publicSigKey;
    private final X509Certificate certificate;

    /**
     * Creates an authority by passing:
     * <ul>
     * <li><code>name, the name of the authority</code></li>
     * </ul>
     *
     * A Key Pair for an ElGamal Encryption Scheme is created. A valid
     * certificate, previously generated, is imported and from it are read the
     * public and private signature keys.
     *
     * @param name Name of the authority
     */
    public Authority(String name) {
        this.name = name;

        //Create the pair (sk,pk);   
        ElGamalKeyPair encPair = new ElGamalKeyPair();
        this.privateEncKey = encPair.getSecretKey();
        this.publicEncKey = encPair.getPublicKey();
        this.certificate = EasyLoadFromFile.loadCrt("Certificati/Authorities/" + name + ".crt");
        this.publicSigKey = this.certificate.getPublicKey();
        this.privateSigKey = EasyLoadFromFile.loadPrivSigKey("Certificati/Authorities/" + name + ".p8");
    }

    /**
     * This method returns the the public encryption key of the authority.
     *
     * @return a <code>BigInteger</code> representing the public encryption key
     * of the authority.
     */
    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    /**
     * This method returns the private encryption key of the authority.
     *
     * @return a <code>BigInteger</code> representing the private encryption key
     * of the authority.
     */
    public BigInteger getPrivateEncKey() {
        return privateEncKey;
    }

    /**
     * This method returns the public signature key of the authority.
     *
     * @return a <code>PublicKey</code> object representing the public signature
     * key of the authority.
     */
    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    /**
     * This method returns the private signature key of the authority.
     *
     * @return a <code>PrivateKey</code> object representing the private
     * signature key.
     */
    public PrivateKey getPrivateSigKey() {
        return privateSigKey;
    }

    /**
     * This method returns the certificate of the authority.
     *
     * @return a <code>X509Certificate</code> object representing the
     * certificate of the authority.
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     *
     * @return a <code>String</code> representing the name of the authority.
     */
    public String getName() {
        return name;
    }
}
