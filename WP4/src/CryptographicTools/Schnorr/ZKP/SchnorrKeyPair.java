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
        x = new BigInteger(param.getSecurityParameter().intValue(), new SecureRandom()).mod(param.getQ()); // choose random r
        y = param.getG().modPow(x, param.getP());
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
