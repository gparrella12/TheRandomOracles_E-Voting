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
 * This class represents a block containing the information about an Authority;
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
     * Constructor of <code>AuthorityBlock</code> class.
     *
     * @param a the authority for which the block are created.
     * @param proof proof of correctness of the Public Encryption Key.
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
        return "Authority Name=" + name + "\npublicEncKey=" + publicEncKey + "\nSchnorr Proof for publicEncKey=" + proof.toString() + "\npublicSigKey=" + publicSigKey + "\nCertificate=" + certificate + "\nsign= " + Utils.toHex(blockSignature);
    }

}
