package CryptographicTools.Schnorr.ZKP;

import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class represents a key pair for Schnorr.
 *
 * @author gparrella
 */
public class SchnorrKeyPair {

    BigInteger x; // x <- Z_q
    BigInteger y; // y <- g^x mod p
    CyclicGroupParameters param;

    /**
     * This class creates a Schnorr key pair.
     *
     * @param param the parameters that defines a cyclic group of order q.
     */
    public SchnorrKeyPair(CyclicGroupParameters param) {
        this.param = param;
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // Take a random value of securityParameter bit.
        // h <- Z_p*
        BigInteger h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        while (h.equals(BigInteger.ONE) || h.equals(BigInteger.ZERO)) {
            h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        }
        // SK = h^2 mod p (because p=2q+1), this is a group element of cyclic group of order q [pag. 322]
        this.x = h.modPow(new BigInteger("2"), p);
        if (isInQSubgroup(this.x, p) == 0) {
            throw new RuntimeException("Malformed Schnorr key");
        }
        // PK = G^SK mod P
        this.y = g.modPow(x, p);
    }

    private static int isInQSubgroup(BigInteger x, BigInteger p) {
        // x ^ {(p-1)/2} mod p == 1 <-> x^q = 1 mod p [pag. 323]
        if (x.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p).compareTo(BigInteger.ONE) == 0) {
            return 1;
        }
        return 0;
    }

    /**
     * This method returns the <code>x</code> value, that is equal to
     * g<sup>r</sup> mod p.
     *
     * @return a <code>BigInteger</code> representing the <code>x</code> value.
     */
    public BigInteger getX() {
        return x;
    }

    /**
     * This method returns the <code>y</code> value, that is equal to
     * g<sup>x</sup> mod p.
     *
     * @return a <code>BigInteger</code> representing the <code>y</code> value.
     */
    public BigInteger getY() {
        return y;
    }

    /**
     * This method returns the parameters that defines a cyclic group of order
     * q.
     *
     * @return a <code>CyclicGroupParameters</code> object representing the
     * parameters that defines a cyclic group of order q.
     */
    public CyclicGroupParameters getParam() {
        return param;
    }

    /**
     * This method prints the pair (x, y).
     *
     * @return a <code>String</code> containing the representation of (x, y)
     */
    @Override
    public String toString() {
        return "(" + "x=" + x + ", y=" + y + ')';
    }

}
