package src.ElGamalHomomorphic;

import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author franc
 */
public class ElGamalCipherText implements Serializable{

    private final BigInteger u; // u = g^r, con r randomness
    private final BigInteger v; // v = g^m * y^r, con y PK

    /**
     *
     * @param u
     * @param v
     */
    public ElGamalCipherText(BigInteger u, BigInteger v) {
        this.u = u;
        this.v = v;

    }

    /**
     *
     * @return
     */
    public BigInteger getU() {
        return u;
    }

    /**
     *
     * @return
     */
    public BigInteger getV() {
        return v;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + "u=" + u + ", v=" + v + ')';
    }

}
