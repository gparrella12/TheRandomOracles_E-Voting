package src.BlockChainPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import src.AuthorityPackage.Authority;
import src.CryptographicTools.SchnorrNIZPK.SchnorrNIProof;
import src.CryptographicTools.SignatureScheme;
import src.Utils.Utils;

/**
 * This class represents a block containing information about an Authority;
 * there will be an Authority Block for each Authority.
 *
 * @author fsonnessa
 */
public class AuthorityBlock implements Serializable {

    private final String name;
    private final BigInteger publicEncKey;
    private final PublicKey publicSigKey;
    private final X509Certificate certificate;
    private final SchnorrNIProof proof;
    private final byte[] blockSignature;

    /**
     * Creates an authority block by passing:
     * <ul>
     * <li><code>the authority</code></li>
     * <li><code>the proof that the authority knows the secret key 
     * associated with its public </code></li>
     * </ul>
     *
     * @param a, the authority for which the block is created.
     * @param proof, the proof of knowledge of the secrete encryption key 
     * relative to the public encryption key .
     */
    public AuthorityBlock(Authority a, SchnorrNIProof proof) {
        this.name = a.getName();
        this.publicEncKey = a.getPublicEncKey();
        this.publicSigKey = a.getPublicSigKey();
        this.certificate = a.getCertificate();
        this.proof = proof;
        this.blockSignature = SignatureScheme.signMessage(a.getPrivateSigKey(), name.concat(publicEncKey.toString()).concat(this.publicSigKey.toString()).concat(this.proof.toString()).getBytes());
    }

    /**
     * This method returns the public encryption key.
     *
     * @return a <code>BigInteger</code> representing the public encryption key.
     */
    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    /**
     * This method returns the public signature key.
     *
     * @return a <code>PublicKey</code> object representing the public signature
     * key.
     */
    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    /**
     * This method returns the certificate.
     *
     * @return a <code>X509Certificate</code> object representing the
     * certificate.
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     * This method prints information of the Authority Block.     
     *
     * @return a <code>String</code> containing information of the Authority Block.  
     */
    @Override
    public String toString() {
        return "Authority Name=" + name + "\npublicEncKey=" + publicEncKey + "\nSchnorr Proof for publicEncKey=" + proof.toString() + "\npublicSigKey=" + publicSigKey + "\nCertificate=" + certificate + "\nsign= " + Utils.toHex(blockSignature);
    }

}
