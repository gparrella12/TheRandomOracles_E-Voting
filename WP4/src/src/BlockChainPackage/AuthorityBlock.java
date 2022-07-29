package src.BlockChainPackage;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import src.SchnorrNIZKP.SchnorrNIProof;

/**
 *
 * @author fsonnessa
 */
public class AuthorityBlock implements Serializable{
    
    private String name;
    private BigInteger publicEncKey;
    private PublicKey publicSigKey;
    private X509Certificate certificate;
    private SchnorrNIProof proof;

    public AuthorityBlock(String name, BigInteger publicEncKey, PublicKey publicSigKey, X509Certificate certificate, SchnorrNIProof proof) {
        this.name = name;
        this.publicEncKey = publicEncKey;
        this.publicSigKey = publicSigKey;
        this.certificate = certificate;
        this.proof = proof;
    }

    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    public PublicKey getPublicSigKey() {
        return publicSigKey;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    @Override
    public String toString() {
        return "(" + "name=" + name + ", publicEncKey=" + publicEncKey + ", publicSigKey=" + publicSigKey + ", certificate=" + certificate + ')';
    }

}
