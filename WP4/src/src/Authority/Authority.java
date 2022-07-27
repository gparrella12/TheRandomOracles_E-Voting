package src.Authority;
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
        this.name=name;        
        ElGamalPair pair = new ElGamalPair();
        this.privateEncKey = pair.getSkValue();
        this.publicEncKey = pair.getPkValue();
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
