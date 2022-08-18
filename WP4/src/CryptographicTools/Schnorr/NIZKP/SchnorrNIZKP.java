package CryptographicTools.Schnorr.NIZKP;

import CryptographicTools.CryptographicHash;
import CryptographicTools.ElGamalHomomorphic.CyclicGroupParameters;
import Utils.Utils;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This Class contains some static methods to implements the Schnorr NIZKP.
 *
 * @author gparrella
 */
public class SchnorrNIZKP {

    /**
     * This method allows to make the Schnorr Non-interactive Zero-Knowledge
     * Proof to prove that the owner of the public key y, also knows the secrete
     * key x associated with it.
     *
     *
     *
     * @param x the secrete key associated to y.
     * @param y the public key, g<sup>x</sup> mod p.
     * @param param the parameters of a cyclic group of order q.
     * @return a <code>SchnorrNIProof</code> object
     */
    public static SchnorrNIProof makeProof(BigInteger x, BigInteger y, CyclicGroupParameters param) {
        int securityParameter = param.getSecurityParameter().intValue();
        BigInteger q = param.getQ();
        BigInteger p = param.getP();
        BigInteger g = param.getG();

        // r random
        // h <- Z_p*
        BigInteger h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        while (h.equals(BigInteger.ONE) || h.equals(BigInteger.ZERO)) {
            h = new BigInteger(securityParameter, new SecureRandom()).mod(p);
        }
        // r = h^2 mod p (because p=2q+1), this is a group element of cyclic group of order q [pag. 322]
        BigInteger r = h.modPow(new BigInteger("2"), p);
        if (isInQSubgroup(r, p) == 0) {
            throw new RuntimeException("Malformed C value");
        }

        // a = g^r mod p
        BigInteger a = g.modPow(r, p);

        BigInteger toHash = new BigInteger(Utils.append(y.toByteArray(), a.toByteArray()));
        BigInteger c = new BigInteger(CryptographicHash.hash(toHash.toByteArray())); // c = H(y || a), with y=g^x mod p  
        // z = r+c*x
        BigInteger z = r.add(c.multiply(x)).mod(q);

        return new SchnorrNIProof(a, c, z);
    }

    /**
     * This method checks if a Schnorr NIZKP is valid with respect to a public
     * key y.
     *
     * @param proof the proof, represented by a <code>SchnorrNIProof</code>
     * object
     * @param y the public key, y=g<sup>x</sup> mod p.
     * @param param the parameters of a cyclic group of order q
     * @return true if the NIZKP is valid, false otherwise
     */
    public static boolean checkProof(SchnorrNIProof proof, BigInteger y, CyclicGroupParameters param) {
        // Get the parameters used
        BigInteger g = param.getG();
        BigInteger p = param.getP();
        BigInteger q = param.getQ();
        // Get the value of the NIZKP

        BigInteger a = proof.getA();
        BigInteger c = proof.getC();
        BigInteger z = proof.getZ();

        // Compute the digest of y || a
        BigInteger toHash = new BigInteger(Utils.append(y.toByteArray(), a.toByteArray()));

        // c1 = H(y || a), con y=g^x mod p
        BigInteger c1 = new BigInteger(CryptographicHash.hash(toHash.toByteArray()));

        // Verify if c value is equal to H(y || a) previously computed
        if (c.equals(c1)) {
            // compute k = g^z mod p
            BigInteger k = g.modPow(z.mod(q), p);

            // compute a * y^c mod p
            BigInteger res = a.multiply(y.modPow(c.mod(q), p)).mod(p);

            // verify if a*y^c = g^z
            return k.equals(res);
        }
        return false;
    }

    private static int isInQSubgroup(BigInteger x, BigInteger p) {
        // x ^ {(p-1)/2} mod p == 1 <-> x^q = 1 mod p [pag. 323]
        if (x.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p).compareTo(BigInteger.ONE) == 0) {
            return 1;
        }
        return 0;
    }
}
