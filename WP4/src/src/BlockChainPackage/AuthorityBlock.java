package src.BlockChainPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import src.SchnorrNIZKP.SchnorrNIProof;

/**
 * This class represents a block containing the information about an Authority;
 * there will be an Authority Block for each Authority.
 *
 * @author fsonnessa
 */
public class AuthorityBlock implements Serializable {

    private String name;
    private BigInteger publicEncKey;
    private PublicKey publicSigKey;
    private X509Certificate certificate;
    private SchnorrNIProof proof;

    /**
     * Constructor of <code>AuthorityBlock</code> class.
     *
     * @param name name associated to the Authority.
     * @param publicEncKey public Encryption Key.
     * @param publicSigKey public Signature Key.
     * @param certificate certificate (N.B. for certificate we mean the lined
     * list of certificates down to the root (that is, the certification
     * authority).
     * @param proof proof of correctness of the vote.
     */
    public AuthorityBlock(String name, BigInteger publicEncKey, PublicKey publicSigKey, X509Certificate certificate, SchnorrNIProof proof) {
        this.name = name;
        this.publicEncKey = publicEncKey;
        this.publicSigKey = publicSigKey;
        this.certificate = certificate;
        this.proof = proof;
    }

    /**
     * Function used to get the public Encyprion key.
     *
     * @return a <code>BigInteger</code> representing the public Encryption Key.
     */
    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    /**
     * Function used to get the public Signature key.
     *
     * @return a <code>PublicKey</code> object representing the public Signature
     * Key.
     */
    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    /**
     * Function used to get the certificate.
     *
     * @return a <code>X509Certificate</code> object representing the
     * certificate.
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     * Function used to print the informations on the BlockChain (in our case,
     * on a file .txt)
     *
     * @return
     */
    @Override
    public String toString() {
        return "Authority Name=" + name + "\npublicEncKey=" + publicEncKey + "\nSchnorr Proof for publicEncKey=" + proof.toString() + "\npublicSigKey=" + publicSigKey + "\nCertificate=" + certificate;
    }

}
