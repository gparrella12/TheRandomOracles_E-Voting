package src.Authority;

import src.ElGamalHomomorphic.ElGamalKeyPair;
import java.math.BigInteger;

/**
 *
 * @author Ernesto
 */

public class Authority {

    private String name;
    private BigInteger privateEncKey;
    private BigInteger publicEncKey;
    private Object privateSigKey;
    private Object publicSigKey;
    private Object certificate;

    //nel costruttore creo la coppia (sk,pk);   
    public Authority(String name) {
        this.name = name;
        ElGamalKeyPair pair = new ElGamalKeyPair();
        this.privateEncKey = pair.getSecretKey();
        this.publicEncKey = pair.getPublicKey();
    }

    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    public BigInteger getPrivateEncKey() {
        return privateEncKey;
    }

    public Object getPublicSigKey() {
        return publicSigKey;
    }

    public Object getCertificate() {
        return certificate;
    }

}
