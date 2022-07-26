package src;


import java.math.*;
import java.security.*;

public class ElGamalPair {
    private BigInteger g, p, q;
    private int securityparameter;
    private BigInteger skValue;
    private BigInteger pkValue;

    public ElGamalPair(int securityparameter) {
        BigInteger p, q, g, pk, sk;

        SecureRandom sr = new SecureRandom();

        while (true) {
            q = BigInteger.probablePrime(securityparameter, sr);
            p = q.multiply(BigInteger.TWO);
            p = p.add(BigInteger.ONE);

            if (p.isProbablePrime(50) == true) {
                break;
            }

        }

        g = new BigInteger("4");
        sk = new BigInteger(securityparameter, sr);
        pk = g.modPow(sk, p);

        this.g = g;
        this.p = p;
        this.q = q;
        this.skValue = sk;
        this.pkValue = pk;

    }

    public BigInteger getPkValue() {
        return pkValue;
    }

    public BigInteger getSkValue() {
        return skValue;
    }

}
