package CryptographicTools.Schnorr.ZKP;

import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author gparrella
 */
public class SchnorrKeyPair {
    BigInteger x; // x <- Z_q
    BigInteger y; // y <- g^x mod p
    CyclicGroupParameters param;

    /**
     *
     * @param param
     */
    public SchnorrKeyPair(CyclicGroupParameters param) {
        this.param = param;
        x  = new BigInteger(param.getSecurityParameter().intValue(), new SecureRandom()).mod(param.getQ()); // choose random r
        y = param.getG().modPow(x, param.getP());
    }

    /**
     *
     * @return
     */
    public BigInteger getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public BigInteger getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public CyclicGroupParameters getParam() {
        return param;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "(" + "x=" + x + ", y=" + y + ')';
    }

 
    
}
