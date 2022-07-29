package src.ElGamalHomomorphic;

import java.io.Serializable;
import java.math.BigInteger;

public class ElGamalCipherText implements Serializable{

    private final BigInteger u; // u = g^r, con r randomness
    private final BigInteger v; // v = g^m * y^r, con y PK

    public ElGamalCipherText(BigInteger u, BigInteger v) {
        this.u = u;
        this.v = v;

    }

    public BigInteger getU() {
        return u;
    }

    public BigInteger getV() {
        return v;
    }

    @Override
    public String toString() {
        return "(" + "u=" + u + ", v=" + v + ')';
    }

}
