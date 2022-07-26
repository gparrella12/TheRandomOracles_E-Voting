package src;
import src.ElGamalPair;
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

    public Authority() {
        ElGamalPair pair = new ElGamalPair(5);
        privateEncKey = pair.getSkValue();
        publicEncKey = pair.getPkValue();
    }

    public BigInteger getPublicEncKey() {
        return publicEncKey;
    }

    public Object getPublicSigKey() {
        return publicSigKey;
    }

    public Object getCertificate() {
        return certificate;
    }

}
